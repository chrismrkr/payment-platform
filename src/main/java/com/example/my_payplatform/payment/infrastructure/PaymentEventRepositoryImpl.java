package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentEventRepositoryImpl implements PaymentEventRepository {
    private final PaymentEventJpaRepository jpaRepository;

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
}
