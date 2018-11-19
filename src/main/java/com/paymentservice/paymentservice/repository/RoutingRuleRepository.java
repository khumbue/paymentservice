package com.paymentservice.paymentservice.repository;

import com.paymentservice.paymentservice.model.RoutingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutingRuleRepository extends JpaRepository<RoutingRule, Long> {
}
