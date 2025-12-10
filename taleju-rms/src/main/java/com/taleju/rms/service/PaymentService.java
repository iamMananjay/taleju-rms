package com.taleju.rms.service;

import com.taleju.rms.dto.PaymentRequest;
import com.taleju.rms.dto.PaymentResponse;
import com.taleju.rms.entity.Order;
import com.taleju.rms.entity.Payment;
import com.taleju.rms.enums.PaymentStatus;
import com.taleju.rms.repository.OrderRepository;
import com.taleju.rms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.taleju.rms.enums.PaymentStatus.PAID;
import static com.taleju.rms.enums.OrderStatus.PREPARING;


@Service
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    @Autowired
    private KitchenOrderService kitchenOrderService;

    public PaymentService(PaymentRepository paymentRepo, OrderRepository orderRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    public PaymentResponse makePayment(PaymentRequest request) {

        // 1. Fetch order
        Order order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Get amount from backend
        Double amountToPay = order.getTotal();

        // 3. Process payment (here you can integrate Stripe later)
        boolean paymentSuccessful = true; // mock for now

        if (!paymentSuccessful) {
            throw new RuntimeException("Payment failed");
        }

        // 4. Create payment record
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amountToPay);
        payment.setPaymentType(request.getPaymentType());

        Payment savedPayment = paymentRepo.save(payment);

        // 6. Create Kitchen Order automatically after payment
        kitchenOrderService.createKitchenOrder(order.getId());
        // 5. Update order payment + order status
        order.setPaymentStatus(PAID);
        order.setOrderStatus(PREPARING);
        orderRepo.save(order);

        // 6. Return response
        return new PaymentResponse(
                savedPayment.getId(),
                order.getId(),
                amountToPay,
                PaymentStatus.PAID
        );
    }


//    private PaymentResponse toResponse(Payment p) {
//        PaymentResponse res = new PaymentResponse();
//        res.setId(p.getId());
//        res.setOrderId(p.getOrder().getId());
//        res.setAmount(p.getAmount());
//        return res;
//    }
}
