package com.allancleitonppma.sscagent.application.pickingStrategies;


import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Condition;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Expression;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;
import com.allancleitonppma.sscagent.domain.model.enums.ComparisonOperator;
import com.allancleitonppma.sscagent.domain.model.enums.ConditionType;
import com.allancleitonppma.sscagent.domain.model.enums.LogicalOperator;
import com.allancleitonppma.sscagent.domain.model.enums.StockAvailability;

import java.time.LocalDate;
import java.util.List;

public class ConditionStrategy implements PickingStrategy {
    List<Pallet> stock;
    InterpretedOrder order;
    PickingMap pickingMap = new PickingMap();
    Double quantityDeft;



    /**
     * @param order
     * @param pallets
     * @return
     */
    @Override
    public PickingMap generated(InterpretedOrder order, List<Pallet> pallets,  List<StockBox> boxes) {
        stock = pallets;
        this.order = order;

        return null;
    }


    public boolean processedOrder(InterpretedOrder order, List<StockBox> boxes){
        double quantityRequired = 0.0;
        String palletId;

        boolean itFound;


        for(StockBox boxe: boxes){
            if(evaluate(order.getExpression(), boxe)){
                quantityRequired += boxe.getNetWeight();
                boxe.isAvailable = StockAvailability.Consumed.getValue();
                pickingMap.getBoxes().add(boxe);
                palletId = boxe.palletId;
            }
            if (quantityRequired >= order.getNeed()){
                break;
            }
        }
        return true;
    }

    private boolean evaluate(
            Expression expression,
            StockBox stockBox
    ) {

        if (expression.getOperator() == LogicalOperator.AND) {

            for (Condition condition : expression.getConditions()) {

                boolean result = AtomEvaluate(
                        condition.getType(),
                        stockBox,
                        condition.getOperator(),
                        condition.getValue()
                );

                if (!result) {
                    return false;
                }
            }

            return true;
        }

        if (expression.getOperator() == LogicalOperator.OR) {

            for (Condition condition : expression.getConditions()) {

                boolean result = AtomEvaluate(
                        condition.getType(),
                        stockBox,
                        condition.getOperator(),
                        condition.getValue()
                );

                if (result) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }


    private boolean AtomEvaluate(ConditionType variable, StockBox stockBox, ComparisonOperator operator, Object expectedValue) {

        Object actualValue = getValue(variable, stockBox);

        return compare(actualValue, operator, expectedValue);
    }

    private Object getValue(ConditionType variable, StockBox stockBox) {

        return switch (variable) {

            case ConditionType.PAC -> stockBox.packages;
            case ConditionType.DIAS -> stockBox.getDaysToExpiry();
            case ConditionType.PRO -> stockBox.productionDate;
            case ConditionType.VAL -> stockBox.expirationDate;
        };
    }

    private boolean compare(
            Object actual,
            ComparisonOperator operator,
            Object expected
    ) {

        switch (operator) {

            case GREATER_THAN -> {
                if(actual.getClass().isInstance(LocalDate.class)){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isAfter(expectedAux);
                }

                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux > expecterAux;
                }
            }

            case LESS_THAN -> {
                if(actual.getClass().isInstance(LocalDate.class)){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isBefore(expectedAux);
                }

                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux < expecterAux;
                }
            }

            case GREATER_OR_EQUAL -> {
                if (actual.getClass().isInstance(LocalDate.class)) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;

                    if (actualAux.isAfter(expectedAux) || actualAux.isEqual(expectedAux)){
                        return true;
                    }
                }

                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux >= expecterAux;

                }

            }
            case LESS_OR_EQUAL -> {
                if (actual.getClass().isInstance(LocalDate.class)) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;

                    if (actualAux.isBefore(expectedAux) || actualAux.isEqual(expectedAux)){
                        return true;
                    }
                }


                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux <= expecterAux;

                }

            }
            case EQUAL -> {
                if (actual.getClass().isInstance(LocalDate.class)) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isEqual(expectedAux);
                }


                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux.equals(expecterAux);

                }
            }

            case NOT_EQUAL ->{
                if(actual.getClass().isInstance(LocalDate.class)){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return !actualAux.isEqual(expectedAux);
                }


                if(actual.getClass().isInstance(Integer.class)){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return !actualAux.equals(expecterAux);

                }
            }
        };

        return false;
    }

}
