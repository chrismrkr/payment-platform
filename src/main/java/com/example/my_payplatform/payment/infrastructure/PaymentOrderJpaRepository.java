package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderEntity, String> {
}
