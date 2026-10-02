package io.gulimall.schedule;

import io.gulimall.entity.order.OrderEntity;
import io.gulimall.service.order.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class OrderTimeoutScheduler {

    private final TaskScheduler taskScheduler;
    private final ObjectProvider<OrderService> orderServiceProvider;
    private final Duration closeDelay;

    public OrderTimeoutScheduler(TaskScheduler taskScheduler,
                                 ObjectProvider<OrderService> orderServiceProvider,
                                 @Value("${order.close-delay-minutes:30}") long closeDelayMinutes) {
        this.taskScheduler = taskScheduler;
        this.orderServiceProvider = orderServiceProvider;
        this.closeDelay = Duration.ofMinutes(closeDelayMinutes);
    }

    public void scheduleClose(OrderEntity orderEntity) {
        if (orderEntity == null || orderEntity.getId() == null) {
            return;
        }
        OrderEntity snapshot = new OrderEntity();
        BeanUtils.copyProperties(orderEntity, snapshot);
        Date triggerTime = Date.from(Instant.now().plus(closeDelay));
        OrderService orderService = orderServiceProvider.getIfAvailable();
        if (orderService == null) {
            return;
        }
        taskScheduler.schedule(() -> orderService.closeOrder(snapshot), triggerTime);
    }
}

