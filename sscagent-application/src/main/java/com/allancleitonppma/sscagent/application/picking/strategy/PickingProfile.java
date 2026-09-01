package com.allancleitonppma.sscagent.application.picking.strategy;

import com.allancleitonppma.sscagent.application.picking.PickingMap;

import java.util.List;

public class PickingProfile {
    //*
    //@ RECEBE O NOME O PERFIL DE COLETA*/
    private String name;

    //*@ RECEBE UMA LISTA COM OS CODIGOS DOS PRODUTOS
    // QUE FAZEM PARTE DESTE PERFIL DE COLETA*/
    private List<String> idProducts;

    //*@ RECEBE O NUMERO QUE REPRESENTA O VALOR MINIMO
    //  DE DIAS A VENCER QUE O PRODUTO ELEGIVEL DEVE
    //  TER PARA QUE ELE POSSA FAZER PARTE DOS ELEGIVEIS*/
    private String minimumDayToExpire;

    //*@ RECEBE VALOR DE EURISTICA PARA O RIGOR DE BUSCA, OU SEJA,
    //   SE DEVE O SITEMA DEVE PEGAR O PALETE/CAIXA COM DATA MAIS
    //   VELHA NO ESTOQUE*/
    private Rigor rigor;

    //*==========================================================================
    //                   BLOCO DE VARIAVEAIS DE RIGOR DE COLETA
    // =========================================================================*/
    private String rangeOfLowRigor;
    private String rangeOfMediumRigor;
    private String rangeOfRigorHigh;
    private String rangeOfCriticalRigor;



    private void setPriority(double quantity){
        rigor = setRangeOfLowRigor(quantity);
        rigor = setRangeOfMediumRigor(quantity);
        rigor = setRangeOfRigorHigh(quantity);
    }


    //*@ ESTE METODO RECEBE O VALOR REFERENTE A QUANTIDADE DO PEDIDO,
    // E SETA NA VARIAVEL {rigor},O RIGOR BAIXO SE O VALOR PASSADO POR
    // PARAMETRO EM {quantity} ATENDER A REGRA DEFINIDA PELO USUARIO
    // PARA ESTE PERFIL DE COLETA.*/
    private Rigor setRangeOfLowRigor(double quantity){
        var range = testRegex(rangeOfLowRigor);

        assert range != null;
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

        assert range != null;
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

        assert range != null;
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

        assert range != null;
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

        if(parameter.matches("^[1-9]\\\\d*-[1-9]\\\\d*$")){
            String[] parts = rangeOfLowRigor.split("-");

            maximumRangeValue = Double.parseDouble(parts[0]);
            minimumRangeValue = Double.parseDouble(parts[1]);

            return new Range(maximumRangeValue, minimumRangeValue);


        }

        return null;
    }

    /*==========================================================================
    //                   CLASSE AUXILIAR PARA DEFINIR O VALOR DE RANGE
    // =========================================================================*/
    private record Range(double minimumRange, double maximumRange){}



    //*==========================================================================
    //                   GERA A LISTA DE PALETE/CAIXAS ELEGIVEIS
    // =========================================================================*/
    public PickingMap generate() {
        // Todo implements here... tester
        setPriority(100.00);
        return null;
    }
}

