package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderEntity, String> {
    @Query(value = "SELECT po " +
                    "FROM PaymentOrderEntity po " +
                    "WHERE po.paymentEventEntity.checkoutId = :checkoutId")
    List<PaymentOrderEntity> findByCheckoutId(@Param("checkoutId") String checkoutId);
}
