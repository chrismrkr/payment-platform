package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentEventRepositoryImpl implements PaymentEventRepository {
    private final PaymentEventJpaRepository jpaRepository;
    private final EntityManager entityManager;

    @Override
    public PaymentEvent findByCheckoutId(String checkoutId) {
        PaymentEventEntity entity = jpaRepository.findById(checkoutId)
                .orElseThrow(() -> new IllegalArgumentException("PaymentEvent Not Fount"));
        return PaymentEvent.from(entity);
    }

    @Override
    public PaymentEvent save(PaymentEvent paymentEvent) {
        PaymentEventEntity entity = paymentEvent.toEntity();
        PaymentEventEntity save = jpaRepository.save(entity);
        return PaymentEvent.from(save);
    }

    @Override
    @Transactional
    public PaymentEvent persistWithPaymentOrder(PaymentEvent paymentEvent) {
        PaymentEventEntity paymentEventEntity = paymentEvent.toEntity();
        for(PaymentOrder paymentOrder : paymentEvent.getPaymentOrders()) {
            PaymentOrderEntity paymentOrderEntity = paymentOrder.toEntity();
            paymentEventEntity.getPaymentOrderEntities().add(paymentOrderEntity);
            paymentOrderEntity.setPaymentEventEntity(paymentEventEntity);
        }
        entityManager.persist(paymentEventEntity);
        return PaymentEvent.fromWithPaymentOrder(paymentEventEntity);
    }
}
