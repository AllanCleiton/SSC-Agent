package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;


import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.CandidateStock;

import java.util.ArrayList;
import java.util.List;

public class InterpretedOrder {
    private final String product;
    private final double need;
    private final String condition;
    private final String order;
    private final String instruction;

    private final Expression expression;

    private List<CandidateStock> candidateStocks = new ArrayList<>();

    public InterpretedOrder(
            String product,
            double need,
            String condition,
            String order,
            String instruction,
            Expression expression
    ) {
        this.product = product;
        this.need = need;
        this.condition = condition;
        this.order = order;
        this.instruction = instruction;
        this.expression = expression;
    }

    public String getProduct() {
        return product;
    }

    public double getNeed() {
        return need;
    }

    public String getCondition() {
        return condition;
    }

    public String getOrder() {
        return order;
    }

    public String getInstruction() {
        return instruction;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<CandidateStock> getCandidateStocks() {
        return candidateStocks;
    }

    public void setCandidateStocks(List<CandidateStock> candidateStocks) {
        this.candidateStocks = candidateStocks;
    }
}
