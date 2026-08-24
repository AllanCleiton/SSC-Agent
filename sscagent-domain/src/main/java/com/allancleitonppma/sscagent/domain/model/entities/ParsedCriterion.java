package com.allancleitonppma.sscagent.domain.model.entities;

import com.allancleitonppma.sscagent.domain.model.enums.CriterionOperator;
import com.allancleitonppma.sscagent.domain.model.enums.CriterionType;

public record ParsedCriterion(
        CriterionType Type,
        CriterionOperator Operator,
        String Value,
        String RawText,
        boolean IsOrdering,
        boolean IsAlternative) {

    public ParsedCriterion(String rawText, String value, CriterionOperator operator, CriterionType type) {
        this(type, operator, value, rawText, false, false);
    }
}