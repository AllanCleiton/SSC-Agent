package com.allancleitonppma.sscagent.application.usecase;

    /*GenerateCollectionMap
        │
        ▼
   escolhe estratégia
        │
 ┌──────┼──────────────┐
 ▼      ▼              ▼
Pedido Cliente      Perfil
 │       │              │
 ▼       ▼              ▼
Strategy Strategy    Strategy
                    │
                    ▼
              algoritmo perfil*/

import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;

import java.util.ArrayList;
import java.util.List;

public class GenerateCollectionMap {

    private List<PickingMap> generatedMaps = new ArrayList<>();

    public GenerateCollectionMap(){}

    //*GERA A LISTA DE PALETE/CAIXAS ELEGIVEIs
    //                     GenerateCollectionMap
    //                             │
    //                             ▼
    //                Lista de pedidos consolidados
    //                             │
    //                             ▼
    //                 ┌───────────────────────┐
    //                 │ Existe condição de    │
    //                 │ envio no pedido?      │
    //                 └───────────┬───────────┘
    //                             │
    //                       SIM   │    NÃO
    //                        │    │
    //                        ▼    └──────────────┐
    //                Estratégia da               │
    //                condição do pedido          │
    //                                            ▼
    //                              ┌────────────────────────┐
    //                              │ Cliente possui condição│
    //                              │ por categoria?         │
    //                              └───────────┬────────────┘
    //                                          │
    //                                    SIM   │    NÃO
    //                                     │    │
    //                                     ▼    └──────────────┐
    //                              Estratégia do              │
    //                              cliente/categoria          │
    //                                                         ▼
    //                                         ┌──────────────────────┐
    //                                         │ Produto possui       │
    //                                         │ PickingProfile?      │
    //                                         └──────────┬───────────┘
    //                                                    │
    //                                              SIM   │   NÃO
    //                                               │    │
    //                                               ▼    └──────────────┐
    //                                      PickingProfile               │
    //                                               │                   │
    //                                               ▼                   ▼
    //                                      Seleção pelo perfil    Seleção padrão
    //                                                            menor peso endereço*/

    /**
     * @param {List<InterpretedOrder> order}
     * @return
     */
    public void toGenerate(List<InterpretedOrder> orders){
        // Todo implements the logical here ...!
    }

}
