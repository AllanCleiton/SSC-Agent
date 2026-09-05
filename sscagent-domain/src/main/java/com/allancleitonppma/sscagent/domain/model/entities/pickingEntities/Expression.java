package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

import com.allancleitonppma.sscagent.domain.model.enums.LogicalOperator;

import java.util.List;

public class Expression {
    private final LogicalOperator operator;
    private final List<Condition> conditions;

    public Expression(
            LogicalOperator operator,
            List<Condition> conditions
    ) {
        this.operator = operator;
        this.conditions = List.copyOf(conditions);
    }

    public LogicalOperator getOperator() {
        return operator;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
