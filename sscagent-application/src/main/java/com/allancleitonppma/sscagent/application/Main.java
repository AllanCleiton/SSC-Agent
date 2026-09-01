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
        OrderPreview order = new OrderPreview(
                UUID.randomUUID(),
                "FRANGO CONGELADO CX. 20KG",
                20,
                "PAC = 9 || PAC = 10",
                "123456",
                null
        );

        InterpretationEngine engine =
                new InterpretationEngine();

        InterpretedOrder result =
                engine.interpret(order);

        System.out.println(
                result.getExpression().getOperator()
        );

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


        System.out.println("Rigor de separaçao: " + profile.getRigor().toString());

    }
}
