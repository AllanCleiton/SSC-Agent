package com.allancleitonppma.sscagent.application;

import com.allancleitonppma.sscagent.application.services.InterpretationEngine;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Condition;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingProfile;
import com.allancleitonppma.sscagent.domain.model.enums.QuantityUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    static void main() {
        // =========================================================
        // todo TESTE 1 - INTERPRETATION ENGINE
        // =========================================================

        //Criando uma ordem
        OrderPreview order = new OrderPreview(
                UUID.randomUUID(),
                "FRANGO CONGELADO CX. 20KG",
                20,
                "PAC = 9 || PAC = 10",
                "123456",
                null
        );


        //Instanciando um InterpretationEngine, para a posteriore interpretar a order
        InterpretationEngine engine =  new InterpretationEngine();

        // A partir da order, o interpretationEngine cria uma InterpretedOrder, que pe uma ordem com sua condition interpretada
        InterpretedOrder result =  engine.interpret(order);

        // aqui ele imprime na tela o operador  da InterpretedOrder. OR ou And
        System.out.println( result.getExpression().getOperator());


        //Aqui ele imprime as condições da  InterpretedOrder.
        for (Condition condition :
                result.getExpression().getConditions()) {

            System.out.println(
                    condition.getType()
                            + " "
                            + condition.getOperator().getSymbol()
                            + " "
                            + condition.getValue()
            );
        }


        // =========================================================
        // todo TESTE 2 - PICKING PROFILE
        // =========================================================

        //*aqui ele cria um objeto pickingProfile, (que vai ser um tipo que implementa a interface EvaluateProduct, isso será usado dentro
        // das classes PickingStrategy conforme o tipo de PickingStrategy*/
        PickingProfile profile = new PickingProfile(
        "PRODUTOS CRÍTICOS",
                new ArrayList<>(List.of("11046")),
                60,
                20.0,
                "0-10.7",
                "11.0-50.76",
                "51-150",
                "151-10000",
                QuantityUnit.Kilograms
        );

        //aqui é um teste para printar na tela o rigor do PickingProfile que criei acima.
        System.out.println("Rigor de separaçao: " + profile.getRigor().toString());




    }
}
