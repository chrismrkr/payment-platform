package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEventJpaRepository extends JpaRepository<PaymentEventEntity, String> {
}
