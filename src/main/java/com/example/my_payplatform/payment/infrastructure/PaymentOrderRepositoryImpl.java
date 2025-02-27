package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentOrderRepositoryImpl implements PaymentOrderRepository {
    private final PaymentOrderJpaRepository jpaRepository;
    private final EntityManager entityManager;

    @Override
    public List<PaymentOrder> findByCheckoutId(String checkoutId) {
        return jpaRepository.findByCheckoutId(checkoutId).stream()
                .map(PaymentOrder::from)
                .toList();
    }

    public List<PaymentOrder> findByCheckoutIdWithoutPaymentEvent(String checkoutId) {
        return jpaRepository.findByCheckoutId(checkoutId).stream()
                .map(PaymentOrder::fromWithoutPaymentEvent)
                .toList();
    }

    @Override
    public PaymentOrder findByPaymentOrderId(String paymentOrderId) {
        PaymentOrderEntity entity = jpaRepository.findById(paymentOrderId)
                .orElseThrow(() -> new IllegalArgumentException("PaymentOrder Not Found"));
        return PaymentOrder.from(entity);
    }

    @Override
    public PaymentOrder save(PaymentOrder paymentOrder) {
        PaymentOrderEntity entity = paymentOrder.toEntity();
        PaymentOrderEntity save = jpaRepository.save(entity);
        return PaymentOrder.from(save);
    }

    @Override
    @Transactional
    public PaymentOrder persist(PaymentOrder paymentOrder) {
        PaymentOrderEntity entity = paymentOrder.toEntity();
        entityManager.persist(entity);
        return PaymentOrder.from(entity);
    }
}
