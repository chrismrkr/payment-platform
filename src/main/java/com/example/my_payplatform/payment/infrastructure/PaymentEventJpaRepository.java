package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentEventJpaRepository extends JpaRepository<PaymentEventEntity, String> {
    @Query(value = "SELECT pe " +
                    "FROM PaymentEventEntity pe " +
                    "JOIN FETCH pe.paymentOrderEntities " +
                    "WHERE pe.paymentToken = :paymentToken")
    Optional<PaymentEventEntity> findByPaymentTokenWithPaymentOrder(@Param("paymentToken") String paymentToken);
    Optional<PaymentEventEntity> findByPaymentToken(String paymentToken);
}
