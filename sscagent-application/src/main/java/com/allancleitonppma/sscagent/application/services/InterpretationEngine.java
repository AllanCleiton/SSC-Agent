package com.allancleitonppma.sscagent.application.services;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Expression;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;

public class InterpretationEngine {
        private final ConditionParser parser;

        public InterpretationEngine() {
            this.parser = new ConditionParser();
        }

        public InterpretedOrder interpret(OrderPreview order) {

            Expression expression =
                    parser.parse(order.getCondition());

            return new InterpretedOrder(
                    order.getProduct(),
                    order.getNeed(),
                    order.getCondition(),
                    order.getOrder(),
                    order.getInstruction(),
                    expression
            );
        }
}
