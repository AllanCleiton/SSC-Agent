package com.allancleitonppma.sscagent.application.pickingStrategies;


import com.allancleitonppma.sscagent.application.usecase.ImportPallet;
import com.allancleitonppma.sscagent.application.usecase.ImportStockBox;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Condition;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.Expression;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;
import com.allancleitonppma.sscagent.domain.model.enums.ComparisonOperator;
import com.allancleitonppma.sscagent.domain.model.enums.ConditionType;
import com.allancleitonppma.sscagent.domain.model.enums.LogicalOperator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *@author allan
 * @version 1.0
 * @implNote "Encontrar conjuntos de StockBoxes, agrupados por pallet, que atendem a uma condição, priorizando a concentração física da coleta."
 */
public class ConditionStrategy implements PickingStrategy {


    /**
     * @param order
     * @param importStockBox
     * @return
     */
    @Override
    public PickingMap generated(InterpretedOrder order,ImportPallet importPallet, ImportStockBox importStockBox) throws IOException {

        return  processedOrder(order, importPallet, importStockBox);

    }


    public PickingMap processedOrder(
            InterpretedOrder order,
            ImportPallet importPallet,
            ImportStockBox importStockBox
    ) throws IOException {
        PickingMap pickingMap = new PickingMap();
        double quantityRequired = 0.0;

        Set<String> processedPallets = new HashSet<>();

        for (StockBox box : importStockBox.StockBoxLoadAll(order.getProduct())) {

            // Esse pallet já foi analisado anteriormente
            if (processedPallets.contains(box.palletId)) {
                continue;
            }

            // Essa caixa não atende à condição.
            // Portanto, seu pallet não é candidato.
            if (!evaluate(order.getExpression(), box)) {
                continue;
            }

            processedPallets.add(box.palletId);

            // Encontramos um pallet candidato.
            // Agora exploramos o pallet inteiro.
            for (StockBox palletBox : importStockBox.loadAllForPallet(box.palletId)) {

                if (!evaluate(order.getExpression(), palletBox)) {
                    continue;
                }

                quantityRequired += palletBox.getNetWeight();

                pickingMap.getBoxes().add(palletBox);

                if (quantityRequired >= order.getNeed()) {
                    return pickingMap;
                }
            }
        }

        return pickingMap;
    }

    private boolean evaluate(
            Expression expression,
            StockBox stockBox
    ) {

        if (expression.getOperator() == LogicalOperator.AND) {

            for (Condition condition : expression.getConditions()) {

                boolean result = atomEvaluate(
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

                boolean result = atomEvaluate(
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


    private boolean atomEvaluate(ConditionType variable, StockBox stockBox, ComparisonOperator operator, Object expectedValue) {

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
                if(actual instanceof LocalDate){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isAfter(expectedAux);
                }

                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux > expecterAux;
                }
            }

            case LESS_THAN -> {
                if(actual instanceof LocalDate){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isBefore(expectedAux);
                }

                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux < expecterAux;
                }
            }

            case GREATER_OR_EQUAL -> {
                if (actual instanceof LocalDate) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;

                    if (actualAux.isAfter(expectedAux) || actualAux.isEqual(expectedAux)){
                        return true;
                    }
                }

                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux >= expecterAux;

                }

            }
            case LESS_OR_EQUAL -> {
                if (actual instanceof LocalDate) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;

                    if (actualAux.isBefore(expectedAux) || actualAux.isEqual(expectedAux)){
                        return true;
                    }
                }


                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux <= expecterAux;

                }

            }
            case EQUAL -> {
                if (actual instanceof LocalDate) {
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return actualAux.isEqual(expectedAux);
                }


                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return actualAux.equals(expecterAux);

                }
            }

            case NOT_EQUAL ->{
                if(actual instanceof LocalDate){
                    LocalDate actualAux = (LocalDate) actual;
                    LocalDate expectedAux = (LocalDate) expected;
                    return !actualAux.isEqual(expectedAux);
                }


                if(actual instanceof Integer){
                    Integer actualAux = (Integer) actual;
                    Integer expecterAux = (Integer) expected;
                    return !actualAux.equals(expecterAux);

                }
            }
        };

        return false;
    }

}
