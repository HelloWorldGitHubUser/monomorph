package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.ProductDto;
import com.hoangtien2k3.ecommerce.dto.order.OrderDto;
import com.hoangtien2k3.ecommerce.dto.response.ProductResponse;
import com.hoangtien2k3.ecommerce.exception.wrapper.CartNotFoundException;
import com.hoangtien2k3.ecommerce.exception.wrapper.OrderNotFoundException;
import com.hoangtien2k3.ecommerce.helper.OrderMappingHelper;
import com.hoangtien2k3.ecommerce.model.order.Order;
import com.hoangtien2k3.ecommerce.repository.order.CartRepository;
import com.hoangtien2k3.ecommerce.repository.order.OrderRepository;
import com.hoangtien2k3.ecommerce.service.OrderService;
import com.hoangtien2k3.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Override
    public List<OrderDto> findAll() {
        log.info("OrderDto List, service; fetch all orders");
        return orderRepository.findAll().stream()
                .map(OrderMappingHelper::map)
                .peek(this::enrichWithProduct)
                .toList();
    }

    @Override
    public Page<OrderDto> findAll(int page, int size, String sortBy, String sortOrder) {
        log.info("OrderDto List, service; fetch all orders with paging and sorting");
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<OrderDto> orderDtos = orderRepository.findAll(pageable).stream()
                .map(OrderMappingHelper::map)
                .peek(this::enrichWithProduct)
                .toList();
        return new PageImpl<>(orderDtos, pageable, orderDtos.size());
    }

    @Override
    public OrderDto findById(Integer orderId) {
        log.info("OrderDto, service; fetch order by id");
        OrderDto orderDto = orderRepository.findById(orderId)
                .map(OrderMappingHelper::map)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id: %d not found", orderId)));
        enrichWithProduct(orderDto);
        return orderDto;
    }

    @Override
    public Boolean existsByOrderId(Integer orderId) {
        return orderRepository.findById(orderId).isPresent();
    }

    @Override
    public OrderDto save(OrderDto orderDto) {
        log.info("OrderDto, service; save order");
        return OrderMappingHelper.map(orderRepository.save(OrderMappingHelper.map(orderDto)));
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        log.info("OrderDto, service; update order");
        Order order = OrderMappingHelper.map(orderDto);
        resolveCart(order);
        return OrderMappingHelper.map(orderRepository.save(order));
    }

    @Override
    public OrderDto update(Integer orderId, OrderDto orderDto) {
        log.info("OrderDto, service; update order with orderId");
        OrderDto existingOrderDto = findById(orderId);
        modelMapper.map(orderDto, existingOrderDto);
        Order order = OrderMappingHelper.map(existingOrderDto);
        resolveCart(order);
        return OrderMappingHelper.map(orderRepository.save(order));
    }

    @Override
    public void deleteById(Integer orderId) {
        log.info("Void, service; delete order by id");
        orderRepository.deleteById(orderId);
    }

    private void resolveCart(Order order) {
        if (order.getCart() != null && order.getCart().getCartId() != null) {
            order.setCart(cartRepository.findById(order.getCart().getCartId())
                    .orElseThrow(() -> new CartNotFoundException(
                            "Cart not found with id: " + order.getCart().getCartId())));
        }
    }

    private void enrichWithProduct(OrderDto orderDto) {
        try {
            ProductDto productDto = productService.findById(orderDto.getProductId());
            orderDto.setProductDto(ProductResponse.builder()
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
    }
}
