package com.github.cotrod.hotel.web.rq;

import com.github.cotrod.hotel.model.Decision;

public class ChangeDecisionRq {
    private long orderId;
    private Decision decision;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
