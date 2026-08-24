# SSC AGENT — Sistema de Separação de Carga Inteligente

O SSC AGENT é uma aplicação desktop em Java 25 para Windows 10 e Windows 11. Ela importa uma carga de venda em JSON, importa pallets e caixas em Excel, interpreta instruções de carregamento configuráveis, escolhe as melhores posições de coleta, consolida necessidades compatíveis e apresenta um mapa pronto para revisão, impressão e confirmação.

## Estrutura do projeto

| Projeto                            | Finalidade                                                                                   |
|------------------------------------|----------------------------------------------------------------------------------------------|
| `SSCAGENTE/sscagent-domain`        | Entidades, interpretação de instruções, consolidação, geração de mapa e operações de estoque. |
| `SSCAGENTE/sscagent-infrastucture` | Leitura de JSON/XLSX e persistência local de perfis e pesos.                                 |
| `SSCAGENTE/sscagent-desktop`       | Interface JavaFX do aplicativo `SSCAGENT.exe`.                                               |
| `SSCAGENTE/sscagent-application`   | Casos de uso / DTOs                                                                          |

## Arquitetura do projeto
Para este projeto eu vou usar:

Arquitetura: Clean Architecture / Hexagonal

Organização: Multi-module Maven

Domínio: DDD leve

Interface: JavaFX + MVVM

Estratégias de picking: Strategy Pattern

Interpretação de instruções: Strategy/Chain of Responsibility

Integração de dados: Ports & Adapters

Persistência futura: Repository Pattern

DTOs: somente nas fronteiras entre camadas

Java: 25


## Requisitos para do Projeto
SDK 25 
java 25

## Fluxo de uso

Na aba **Importação**, selecione `CargaDeVenda.json`, `pallets.xlsx` e `products.xlsx` e clique em **Importar dados**. Depois escolha o perfil na aba **Mapa de coleta** e clique em **Gerar mapa**. A tela administrativa apresenta pedido(s) de origem, produto, quantidade, unidade, pallet, lista de posições, pacotes por caixa, validade, dias restantes, critério de identificação física, instrução e status. A impressão operacional transforma a necessidade em caixas e exibe somente a quantidade de caixas a retirar.

A visão de **gestão** e o mapa operacional são deliberadamente diferentes. As abas **Estoque**, **Endereços** e **Perfis de coleta** são detalhadas e editáveis, permitindo bloquear ou liberar caixas, ajustar pesos, alterar regex e administrar configurações. Já o mapa de coleta é uma visão operacional enxuta, feita para impressão e leitura rápida pelo operador, sem expor controles administrativos desnecessários.

Quando uma necessidade ultrapassar o saldo de um pallet, o gerador continua nos pallets elegíveis e cria uma linha por pallet. Se o mesmo pallet possuir caixas equivalentes em várias posições, essas posições são agrupadas na mesma linha, por exemplo `CAM01 RUA13 POS A01A, A02A, A03C`. Assim, o mapa não esconde a distribuição física nem obriga o operador a interpretar várias linhas administrativas. Na impressão operacional, a quantidade total da necessidade fica no cabeçalho do bloco; em cada etapa aparece somente a quantidade de caixas a retirar daquele pallet, evitando o campo `Quantidade neste pallet`.

A carga pode informar a necessidade em quilogramas, mas o mapa operacional sempre converte essa necessidade para caixas. Para cada bloco consolidado, o SIMP calcula `caixas necessárias = teto(peso consolidado / peso médio líquido da caixa do produto)`. O arredondamento para cima garante que uma fração de caixa não seja planejada. Depois dessa conversão, o operador trabalha somente com a quantidade inteira de caixas; o peso não é exigido durante a separação. Um perfil ainda pode receber diretamente uma quantidade em caixas quando a origem da carga já usar essa unidade.

As caixas com status de origem `VENCIDA`, `BLOQUEADA` ou `INDISPONIVEL` não são selecionadas. Pallets sem endereço ficam visíveis no estoque, mas não entram na geração automática quando o perfil está configurado para excluir endereços ausentes. Pesos menores no cadastro de endereços recebem prioridade. Um pedido que tenha `instrucaoCarregamento` nunca é consolidado com pedido sem instrução ou com texto de instrução diferente; a separação ocorre mesmo quando o produto é o mesmo.

## Campo instrucaoCarregamento

Cada pedido pode receber um texto livre preenchido pelo vendedor externo:

```json
"instrucaoCarregamento" : "Prciso de frango com 08 cx"
```

O texto original é preservado. O interpretador tenta extrair os critérios com as regex cadastradas no perfil. As regras iniciais reconhecem:

| Sintaxe            | Interpretação                                                                  |
|--------------------|--------------------------------------------------------------------------------|
| `PAC = 9`          | Seleciona caixas com nove pacotes.                                             |
| `DIAS = 40`        | Seleciona caixas com exatamente quarenta dias restantes até o vencimento.      |
| `DIAS >= 121`      | Seleciona caixas com 121 dias ou mais restantes.                               |
| `PRO = 28/03/2026` | Filtra pela data de produção.                                                  |
| `VAL = 28/03/2026` | Filtra pela data de validade.                                                  |
| `DIAS ↑`           | Prioriza o produto mais novo, isto é, maior número de dias restantes primeiro. |
| `PAC = 9           | DIAS >= 121` | Usa alternativa lógica entre os critérios capturados.           | 

Se o texto não corresponder aos padrões ativos, o processamento pausa e abre uma janela para intervenção. O usuário pode informar uma expressão já suportada e continuar. Novos padrões podem ser cadastrados e editados na aba **Perfis de coleta**, sem alterar o código-fonte nem recompilar o sistema.

Uma regra editável precisa conter uma expressão com grupos nomeados compatíveis com o cadastro. Por exemplo:

```regex
\bPAC\s*(?<operator>=|>=|<=|>|<)\s*(?<value>\d+(?:[,.]\d+)?)
```

Por padrão, o grupo `operator` contém `=`, `>=`, `<=`, `>` ou `<`, e o grupo `value` contém o valor capturado. Regras de ordenação podem usar um grupo `direction`, como na regra de `DIAS ↑`.

## Dados fornecidos

A planilha de caixas possui uma linha por caixa física e utiliza `Etiq Prod` como identificação da caixa, `Etiq Mãe` como pallet, `Cód Sankhya` como produto, `Pacotes`, `Data Val`, `Data Fab`, `Lote Produção`, `Status` e os pesos da caixa. A planilha de pallets utiliza `Etiqueta Palete`, `Situação`, `Local` e `Total Caixas`. Divergências entre resumo e detalhe são reportadas como alertas, sem apagar dados.

## Persistência local

Os perfis e os pesos de endereço são salvos no diretório `%LOCALAPPDATA%\SIMP`. Os arquivos são `profiles.json` e `address-weights.json`. A arquitetura deixa a persistência isolada para futura migração para banco de dados ou API.

