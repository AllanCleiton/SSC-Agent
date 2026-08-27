package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

public class Condition {
    private final ConditionType type;
    private final ComparisonOperator operator;
    private final Object value;

    public Condition(
            ConditionType type,
            ComparisonOperator operator,
            Object value
    ) {
        this.type = type;
        this.operator = operator;
        this.value = value;
    }

    public ConditionType getType() {
        return type;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
