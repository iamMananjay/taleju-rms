package com.taleju.rms.service;

import com.taleju.rms.dto.OrderItemRequest;
import com.taleju.rms.dto.OrderItemResponse;
import com.taleju.rms.dto.OrderRequest;
import com.taleju.rms.dto.OrderResponse;
import com.taleju.rms.entity.*;
import com.taleju.rms.enums.OrderStatus;
import com.taleju.rms.enums.PaymentStatus;
import com.taleju.rms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

//    @Autowired
//    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Place new order
    @Transactional
    public OrderResponse placeOrder(OrderRequest request,String loggedInEmail) {
        Order order = new Order();

        // Generate order number
        order.setOrderNumber("ORD-" + System.currentTimeMillis());

//        Table table = tableRepository.findById(request.getTableId())
//                .orElseThrow(() -> new RuntimeException("Table not found"));
//        order.setTable(table);

        User customer;
        if (loggedInEmail != null) {
            customer = userRepository.findByEmail(loggedInEmail)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

            order.setCustomer(customer);
            order.setRestaurant(customer.getRestaurant()); // from logged-in user's restaurant

        } else {
            customer = userRepository.findByEmail("guest@taleju.com")
                    .orElseThrow(() -> new RuntimeException("Guest user not found"));
            order.setCustomer(customer);

            // Get restaurant from request (QR order will include it)
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurant())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            order.setRestaurant(restaurant);
        }



        order.setOrderType(request.getOrderType());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);
//        order.setRestaurant(table.getRestaurant());

        Set<OrderItem> orderItems = new HashSet<>();
        double total = 0.0;

        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice() * itemReq.getQuantity());
            orderItem.setSpecialRequest(itemReq.getSpecialRequest());

            total += orderItem.getPrice();
            orderItems.add(orderItem);

        }

        order.setTotal(total);
        order.setOrderItems(orderItems);

        orderRepository.save(order);  // Cascade will save items
        orderItemRepository.saveAll(orderItems);

        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(Long orderId, OrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderStatus() == OrderStatus.SERVED || order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot modify order that is already served or completed");
        }

        order.getOrderItems().clear(); // Remove old items
        double total = 0.0;
        order.setOrderType(request.getOrderType());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);

        for(OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice() * itemReq.getQuantity());
            orderItem.setSpecialRequest(itemReq.getSpecialRequest());

            total += orderItem.getPrice();
            order.getOrderItems().add(orderItem);

        }

        order.setTotal(total);
        orderRepository.save(order);

        return mapToResponse(order);
    }


    // Get all orders by restaurant
    public List<OrderResponse> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get order by ID
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    // Update order status
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
        return mapToResponse(order);
    }

    // Update payment status
    @Transactional
    public OrderResponse updatePaymentStatus(Long orderId, PaymentStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(status);
        orderRepository.save(order);
        return mapToResponse(order);
    }

    // Cancel order
    public void cancelOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    // Mapping to DTO
    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> items = order.getOrderItems().stream().map(oi -> {
            OrderItemResponse resp = new OrderItemResponse();
            resp.setMenuItemName(oi.getMenuItem().getName());
            resp.setQuantity(oi.getQuantity());
            resp.setPrice(oi.getPrice());
            resp.setSpecialRequest(oi.getSpecialRequest());
            return resp;
        }).toList();

        OrderResponse response = new OrderResponse();
        response.setOrderNumber(order.getOrderNumber());
        response.setTotal(order.getTotal());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setOrderStatus(order.getOrderStatus());
        response.setOrderType(order.getOrderType());
        response.setItems(items);

        return response;
    }
}

