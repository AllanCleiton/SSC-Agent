package com.allancleitonppma.sscagent.application;

import com.allancleitonppma.sscagent.application.services.InterpretationEngine;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Condition;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;

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
    }
}
