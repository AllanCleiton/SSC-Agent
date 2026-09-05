package com.allancleitonppma.sscagent.application.services;


import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.*;
import com.allancleitonppma.sscagent.domain.model.enums.ComparisonOperator;
import com.allancleitonppma.sscagent.domain.model.enums.ConditionType;
import com.allancleitonppma.sscagent.domain.model.enums.LogicalOperator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConditionParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Expression parse(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "A condição não pode ser vazia."
            );
        }

        String condition = text.trim();

        boolean hasAnd = condition.contains("&&");
        boolean hasOr = condition.contains("||");

        /*
         * V1:
         * Não permitimos misturar && e ||.
         */
        if (hasAnd && hasOr) {
            throw new IllegalArgumentException(
                    "A V1 não permite misturar && e || na mesma expressão."
            );
        }

        LogicalOperator logicalOperator = null;

        if (hasAnd) {
            logicalOperator = LogicalOperator.AND;
        } else if (hasOr) {
            logicalOperator = LogicalOperator.OR;
        }

        String separator = logicalOperator == LogicalOperator.AND
                ? "&&"
                : logicalOperator == LogicalOperator.OR
                ? "\\|\\|"
                : null;

        String[] parts;

        if (separator != null) {
            parts = condition.split("\\s*" + separator + "\\s*");
        } else {
            parts = new String[]{condition};
        }

        List<Condition> conditions = new ArrayList<>();

        for (String part : parts) {
            conditions.add(parseCondition(part));
        }

        /*
         * Se existe conectivo, precisamos ter pelo menos
         * duas condições.
         */
        if (logicalOperator != null && conditions.size() < 2) {
            throw new IllegalArgumentException(
                    "Uma expressão lógica precisa possuir pelo menos duas condições."
            );
        }

        return new Expression(
                logicalOperator,
                conditions
        );
    }

    private Condition parseCondition(String text) {

        String condition = text.trim();

        String[] operators = {
                ">=",
                "<=",
                "!=",
                "=",
                ">",
                "<"
        };

        for (String operatorSymbol : operators) {

            int index = condition.indexOf(operatorSymbol);

            if (index > 0) {

                String typeText =
                        condition.substring(0, index).trim();

                String valueText =
                        condition.substring(
                                index + operatorSymbol.length()
                        ).trim();

                if (valueText.isBlank()) {
                    throw new IllegalArgumentException(
                            "Valor da condição não informado: " + condition
                    );
                }

                ConditionType type = parseType(typeText);

                ComparisonOperator operator =
                        parseOperator(operatorSymbol);

                Object value =
                        parseValue(type, valueText);

                return new Condition(
                        type,
                        operator,
                        value
                );
            }
        }

        throw new IllegalArgumentException(
                "Condição inválida: " + condition
        );
    }

    private ConditionType parseType(String value) {

        try {
            return ConditionType.valueOf(
                    value.toUpperCase()
            );

        } catch (IllegalArgumentException exception) {

            throw new IllegalArgumentException(
                    "Tipo de condição desconhecido: " + value
            );
        }
    }

    private ComparisonOperator parseOperator(String value) {

        return switch (value) {

            case ">" -> ComparisonOperator.GREATER_THAN;
            case "<" -> ComparisonOperator.LESS_THAN;
            case ">=" -> ComparisonOperator.GREATER_OR_EQUAL;
            case "<=" -> ComparisonOperator.LESS_OR_EQUAL;
            case "=" -> ComparisonOperator.EQUAL;
            case "!=" -> ComparisonOperator.NOT_EQUAL;

            default -> throw new IllegalArgumentException(
                    "Operador desconhecido: " + value
            );
        };
    }

    private Object parseValue(
            ConditionType type,
            String value
    ) {

        return switch (type) {

            case PAC, DIAS ->
                    parseInteger(value, type);

            case PRO, VAL ->
                    parseDate(value, type);
        };
    }

    private Integer parseInteger(
            String value,
            ConditionType type
    ) {

        try {
            return Integer.valueOf(value);

        } catch (NumberFormatException exception) {

            throw new IllegalArgumentException(
                    "O tipo " + type +
                            " espera um valor inteiro: " + value
            );
        }
    }

    private LocalDate parseDate(
            String value,
            ConditionType type
    ) {

        try {
            return LocalDate.parse(
                    value,
                    DATE_FORMAT
            );

        } catch (Exception exception) {

            throw new IllegalArgumentException(
                    "O tipo " + type +
                            " espera uma data no formato dd/MM/yyyy: "
                            + value
            );
        }
    }
}