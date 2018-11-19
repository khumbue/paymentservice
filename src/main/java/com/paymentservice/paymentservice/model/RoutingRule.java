package com.paymentservice.paymentservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RoutingRule implements Serializable {

    @Id
    private Long id;

    @Column(name="settlement_engine")
    private String settlementEngine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettlementEngine() {
        return settlementEngine;
    }

    public void setSettlementEngine(String settlementEngine) {
        this.settlementEngine = settlementEngine;
    }
}
