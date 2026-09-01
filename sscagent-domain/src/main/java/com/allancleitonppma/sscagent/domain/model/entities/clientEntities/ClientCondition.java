package com.allancleitonppma.sscagent.domain.model.entities.clientEntities;

import com.allancleitonppma.sscagent.domain.model.entities.productEntities.ProductCategory;

import java.util.HashMap;
import java.util.Map;

public class ClientCondition {
    private String socialReason;
    private Integer shippingConditionAll;
    private Boolean conditionAll;

    private final Map<ProductCategory, Integer> shippingConditions = new HashMap<>();

    public ClientCondition(String socialReason, Boolean conditionAll, Integer shippingConditionAll) {
        this.socialReason = socialReason;
        this.conditionAll = conditionAll;
        this.shippingConditionAll = shippingConditionAll;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public Integer getShippingConditionAll() {
        return shippingConditionAll;
    }

    public void setShippingConditionAll(Integer shippingConditionAll) {
        this.shippingConditionAll = shippingConditionAll;
    }

    public Boolean getConditionAll() {
        return conditionAll;
    }

    public void setConditionAll(Boolean conditionAll) {
        this.conditionAll = conditionAll;
    }

    public Map<ProductCategory, Integer> getShippingConditions() {
        return shippingConditions;
    }

}
