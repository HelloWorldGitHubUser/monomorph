package com.goodskill.handler;
import com.goodskill.dto.SeckillWebMockRequestDTO;
import com.goodskill.monomorph.id.generated.client.OrderServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class MongoPreRequestHandler extends AbstractPreRequestHandler {
    @Resource
    private OrderServiceImpl orderService;

    @Override
    public void handle(SeckillWebMockRequestDTO request) {
        orderService.deleteRecord(request.getSeckillId());
    }

    @Override
    public int getOrder() {
        return 3;
    }
}