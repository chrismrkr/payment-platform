package com.example.my_payplatform.payment.infrastructure;

import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentOrderRepositoryImpl implements PaymentOrderRepository {
    private final PaymentOrderJpaRepository jpaRepository;
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
}
