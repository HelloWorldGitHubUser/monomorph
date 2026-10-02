package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.OrderItemDto;
import com.hoangtien2k3.ecommerce.dto.ProductDto;
import com.hoangtien2k3.ecommerce.dto.order.OrderDto;
import com.hoangtien2k3.ecommerce.dto.response.OrderResponse;
import com.hoangtien2k3.ecommerce.dto.response.ProductResponse;
import com.hoangtien2k3.ecommerce.exception.wrapper.OrderItemNotFoundException;
import com.hoangtien2k3.ecommerce.helper.OrderItemMappingHelper;
import com.hoangtien2k3.ecommerce.model.shipping.OrderItemId;
import com.hoangtien2k3.ecommerce.repository.shipping.OrderItemRepository;
import com.hoangtien2k3.ecommerce.service.OrderItemService;
import com.hoangtien2k3.ecommerce.service.OrderService;
import com.hoangtien2k3.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderService orderService;

    @Override
    public List<OrderItemDto> findAll() {
        log.info("OrderItemDto List, service; fetch all orderItems");
        return this.orderItemRepository.findAll().stream()
                .map(OrderItemMappingHelper::map)
                .peek(this::enrich)
                .distinct()
                .toList();
    }

    @Override
    public OrderItemDto findById(final OrderItemId orderItemId) {
        log.info("OrderItemDto, service; fetch orderItem by id");
        return this.orderItemRepository.findById(orderItemId)
                .map(OrderItemMappingHelper::map)
                .map(o -> {
                    enrich(o);
                    return o;
                })
                .orElseThrow(() -> new OrderItemNotFoundException(String.format("OrderItem with id: %s not found", orderItemId)));
    }

    @Override
    public OrderItemDto save(final OrderItemDto orderItemDto) {
        log.info("OrderItemDto, service; save orderItem");
        return OrderItemMappingHelper.map(this.orderItemRepository
                .save(OrderItemMappingHelper.map(orderItemDto)));
    }

    @Override
    public OrderItemDto update(final OrderItemDto orderItemDto) {
        log.info("OrderItemDto, service; update orderItem");
        return OrderItemMappingHelper.map(this.orderItemRepository
                .save(OrderItemMappingHelper.map(orderItemDto)));
    }

    @Override
    public void deleteById(final OrderItemId orderItemId) {
        log.info("Void, service; delete orderItem by id");
        this.orderItemRepository.deleteById(orderItemId);
    }

    private void enrich(OrderItemDto o) {
        try {
            ProductDto productDto = productService.findById(o.getProductId());
            o.setProductDto(ProductResponse.builder()
                    .productId(productDto.getProductId())
                    .productTitle(productDto.getProductTitle())
                    .imageUrl(productDto.getImageUrl())
                    .sku(productDto.getSku())
                    .priceUnit(productDto.getPriceUnit())
                    .quantity(productDto.getQuantity())
                    .build());
        } catch (Exception e) {
            log.error("Error fetching product info: {}", e.getMessage());
        }
        try {
            OrderDto orderDto = orderService.findById(o.getOrderId());
            o.setOrderDto(OrderResponse.builder()
                    .orderId(orderDto.getOrderId())
                    .orderDate(orderDto.getOrderDate())
                    .orderDesc(orderDto.getOrderDesc())
                    .orderFee(orderDto.getOrderFee())
                    .productId(orderDto.getProductId())
                    .build());
        } catch (Exception e) {
            log.error("Error fetching order info: {}", e.getMessage());
        }
    }
}
