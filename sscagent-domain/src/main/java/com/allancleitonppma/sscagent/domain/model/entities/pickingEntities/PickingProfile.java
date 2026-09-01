package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

import com.allancleitonppma.sscagent.domain.model.enums.QuantityUnit;

import java.util.List;

public class PickingProfile {
    //*@ RECEBE O NOME O PERFIL DE COLETA*/
    private final String name;

    //*@ RECEBE UMA LISTA COM OS CÓDIGOS DOS PRODUTOS
    // QUE FAZEM PARTE DESTE PERFIL DE COLETA*/
    private final List<String> productsContents;

    //*@ RECEBE O NÚMERO QUE REPRESENTA O VALOR MÍNIMO
    //  DE DIAS A VENCER QUE O PRODUTO ELEGÍVEL DEVE
    //  TER PARA QUE ELE POSSA FAZER PARTE DOS ELEGÍVEIS*/
    private final Integer minimumDayToExpire;

    //*@ RECEBE VALOR DE EURISTICA PARA O RIGOR DE BUSCA, OU SEJA,
    //   SE DEVE O SISTEMA DEVE PEGAR O PALETE/CAIXA COM DATA MAIS
    //   VELHA NO ESTOQUE*/
    private Rigor rigor;

    private QuantityUnit unit;

    //*==========================================================================
    //                   BLOCO DE VARIAVEIS DE RIGOR DE COLETA
    // =========================================================================*/
    private final String rangeOfLowRigor;
    private final String rangeOfMediumRigor;
    private final String rangeOfRigorHigh;
    private final String rangeOfCriticalRigor;

    public PickingProfile(String name,
                          List<String> productsContents,
                          Integer minimumDayToExpire,
                          Double quantity,
                          String rangeOfLowRigor,
                          String rangeOfMediumRigor,
                          String rangeOfRigorHigh,
                          String rangeOfCriticalRigor,
                          QuantityUnit unit) {
        this.name = name;
        this.productsContents = productsContents;
        this.minimumDayToExpire = minimumDayToExpire;
        this.rangeOfLowRigor = rangeOfLowRigor;
        this.rangeOfMediumRigor = rangeOfMediumRigor;
        this.rangeOfRigorHigh = rangeOfRigorHigh;
        this.rangeOfCriticalRigor = rangeOfCriticalRigor;
        this.unit = unit;
        rigor = setPriority(quantity);

    }

    public Rigor setPriority(double quantity){
        Rigor result;

        result = setRangeOfLowRigor(quantity);
        if (result != Rigor.NENHUM) return result;

        result = setRangeOfMediumRigor(quantity);
        if (result != Rigor.NENHUM) return result;

        result = setRangeOfRigorHigh(quantity);
        if (result != Rigor.NENHUM) return result;

        result = setRangeOfCriticalRigor(quantity);
        if (result != Rigor.NENHUM) return result;

        return Rigor.NENHUM;
    }


    //*@ ESTE METODO RECEBE O VALOR REFERENTE A QUANTIDADE DO PEDIDO,
    // E SETA NA VARIAVEL {rigor},O RIGOR BAIXO SE O VALOR PASSADO POR
    // PARAMETRO EM {quantity} ATENDER A REGRA DEFINIDA PELO USUARIO
    // PARA ESTE PERFIL DE COLETA.*/
    private Rigor setRangeOfLowRigor(double quantity){
        var range = testRegex(rangeOfLowRigor);

        if(range.minimumRange == 0 && range.maximumRange == 0)
            return Rigor.NENHUM;

        if((quantity >= range.minimumRange) && (quantity <= range.maximumRange) ){
            return Rigor.BAIXA;
        }

        return Rigor.NENHUM;
    }

    //*@ ESTE METODO RECEBE O VALOR REFERENTE A QUANTIDADE DO PEDIDO,
    // E SETA NA VARIAVEL {rigor},O RIGOR MEDIO SE O VALOR PASSADO POR
    // PARAMETRO EM {quantity} ATENDER A REGRA DEFINIDA PELO USUARIO
    // PARA ESTE PERFIL DE COLETA.*/
    private Rigor setRangeOfMediumRigor(double quantity){
        var range = testRegex(rangeOfMediumRigor);

        if(range.minimumRange == 0 && range.maximumRange == 0)
            return Rigor.NENHUM;

        if((quantity >= range.minimumRange) && (quantity <= range.maximumRange) ){
            return Rigor.MEDIA;
        }

        return Rigor.NENHUM;
    }

    //*@ ESTE METODO RECEBE O VALOR REFERENTE A QUANTIDADE DO PEDIDO,
    // E SETA NA VARIAVEL {rigor},O RIGOR ALTO SE O VALOR PASSADO POR
    // PARAMETRO EM {quantity} ATENDER A REGRA DEFINIDA PELO USUARIO
    // PARA ESTE PERFIL DE COLETA.*/
    private Rigor setRangeOfRigorHigh(double quantity){
        var range = testRegex(rangeOfRigorHigh);

        if(range.minimumRange == 0 && range.maximumRange == 0)
            return Rigor.NENHUM;

        if((quantity >= range.minimumRange) && (quantity <= range.maximumRange) ){
            return Rigor.ALTA;
        }

        return Rigor.NENHUM;
    }

    //*@ ESTE METODO RECEBE O VALOR REFERENTE A QUANTIDADE DO PEDIDO,
    // E SETA NA VARIAVEL {rigor},O RIGOR ALTO SE O VALOR PASSADO POR
    // PARAMETRO EM {quantity} ATENDER A REGRA DEFINIDA PELO USUARIO
    // PARA ESTE PERFIL DE COLETA.*/
    private Rigor setRangeOfCriticalRigor(double quantity){
        var range = testRegex(rangeOfCriticalRigor);

        if(range.minimumRange == 0 && range.maximumRange == 0)
            return Rigor.NENHUM;

        if((quantity >= range.minimumRange) && (quantity <= range.maximumRange) ){
            return Rigor.CRITICA;
        }

        return Rigor.NENHUM;
    }

    //*@ ESTE METODO TESTA SE O VALOR PASSADO NO PARAMENTO, ATENDE AO
    //   PADRÃO ESTABELECIDO, E RETORNA UM OBJETO RANGER SE SIM*/
    private  Range  testRegex(String parameter){
        double minimumRangeValue;
        double maximumRangeValue;

        if(parameter.matches("^\\d+(?:\\.\\d+)?-\\d+(?:\\.\\d+)?$")){
            String[] parts = parameter.split("-");

            minimumRangeValue = Double.parseDouble(parts[0]);
            maximumRangeValue = Double.parseDouble(parts[1]);

            return new Range(minimumRangeValue, maximumRangeValue);


        }

        return new Range(0,0);
    }
    /*==========================================================================
    //@ ESTE METODO IRÁ DIZER SE O CÓDIGO DO PRODUTO PASSADO POR PARÂMETRO
        PERTENCE A ESSE PERFIL DE COLETA
    // =========================================================================*/
    public boolean acceptsProduct(String productId) {
        return productsContents.contains(productId);
    }

    /*==========================================================================
    //                   CLASSE AUXILIAR PARA DEFINIR O VALOR DE RANGE
    // =========================================================================*/
    private record Range(double minimumRange, double maximumRange){}


    /*==========================================================================
    //                   METODOS GET
    // =========================================================================*/

    public String getName() {
        return name;
    }

    public List<String> getProductsContents() {
        return productsContents;
    }

    public Integer getMinimumDayToExpire() {
        return minimumDayToExpire;
    }

    public Rigor getRigor() {
        return rigor;
    }

    public String getRangeOfLowRigor() {
        return rangeOfLowRigor;
    }

    public String getRangeOfMediumRigor() {
        return rangeOfMediumRigor;
    }

    public String getRangeOfRigorHigh() {
        return rangeOfRigorHigh;
    }

    public String getRangeOfCriticalRigor() {
        return rangeOfCriticalRigor;
    }
}

