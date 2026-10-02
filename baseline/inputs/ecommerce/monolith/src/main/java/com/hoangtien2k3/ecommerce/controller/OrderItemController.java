package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.DtoCollectionResponse;
import com.hoangtien2k3.ecommerce.dto.OrderItemDto;
import com.hoangtien2k3.ecommerce.model.shipping.OrderItemId;
import com.hoangtien2k3.ecommerce.service.OrderItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shippings")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DtoCollectionResponse<OrderItemDto>> findAll() {
        log.info("OrderItemDto List, controller; fetch all orderItems");
        return ResponseEntity.ok(new DtoCollectionResponse<>(this.orderItemService.findAll()));
    }

    @GetMapping("/{orderId}/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderItemDto> findById(@PathVariable("orderId") final String orderId,
                                                 @PathVariable("productId") final String productId) {
        log.info("OrderItemDto, resource; fetch orderItem by id");
        return ResponseEntity.ok(this.orderItemService.findById(
                new OrderItemId(Integer.parseInt(productId), Integer.parseInt(orderId))));
    }

    @GetMapping("/find")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderItemDto> findById(@RequestBody
                                                 @NotNull(message = "Input must not be NULL")
                                                 @Valid final OrderItemId orderItemId) {
        log.info("OrderItemDto, resource; fetch orderItem by id");
        return ResponseEntity.ok(this.orderItemService.findById(orderItemId));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderItemDto> save(@RequestBody @NotNull(message = "Input must not be NULL")
                                             @Valid final OrderItemDto orderItemDto) {
        log.info("OrderItemDto, resource; save orderItem");
        return ResponseEntity.ok(this.orderItemService.save(orderItemDto));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderItemDto> update(@RequestBody
                                               @NotNull(message = "Input must not be NULL")
                                               @Valid final OrderItemDto orderItemDto) {
        log.info("OrderItemDto, resource; update orderItem");
        return ResponseEntity.ok(this.orderItemService.update(orderItemDto));
    }

    @DeleteMapping("/{orderId}/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> deleteById(@PathVariable("orderId") final String orderId,
                                              @PathVariable("productId") final String productId) {
        log.info("Boolean, resource; delete orderItem by id");
        this.orderItemService.deleteById(new OrderItemId(Integer.parseInt(productId), Integer.parseInt(orderId)));
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> deleteById(@RequestBody
                                              @NotNull(message = "Input must not be NULL")
                                              @Valid final OrderItemId orderItemId) {
        log.info("Boolean, resource; delete orderItem by id");
        this.orderItemService.deleteById(orderItemId);
        return ResponseEntity.ok(true);
    }
}
