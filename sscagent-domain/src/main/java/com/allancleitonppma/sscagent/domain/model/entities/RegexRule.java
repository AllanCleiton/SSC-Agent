package com.allancleitonppma.sscagent.domain.model.entities;

import com.allancleitonppma.sscagent.domain.model.enums.CriterionOperator;
import com.allancleitonppma.sscagent.domain.model.enums.CriterionType;

public class RegexRule
{
    public String name;
    public String pattern;
    public CriterionType criterionType;
    public CriterionOperator operator;
    public String valueGroup = "value";
    public String operatorGroup = "operator";
    public String directionGroup;
    public boolean enabled = true;
    public boolean isRequired;
    public String Description = "";
}