package com.paymentservice.paymentservice.repository;

import com.paymentservice.paymentservice.model.BusinessRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Long> {
}
