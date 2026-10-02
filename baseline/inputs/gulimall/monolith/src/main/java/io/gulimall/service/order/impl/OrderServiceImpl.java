package io.gulimall.service.order.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.gulimall.entity.member.MemberReceiveAddressEntity;
import io.gulimall.service.member.MemberReceiveAddressService;
import io.gulimall.constant.CartConstant;
import io.gulimall.exception.NoStockException;
import io.gulimall.vo.SkuHasStockVo;
import io.gulimall.to.mq.OrderTo;
import io.gulimall.to.mq.SeckillOrderTo;
import io.gulimall.utils.PageUtils;
import io.gulimall.utils.Query;
import io.gulimall.utils.R;
import io.gulimall.vo.MemberResponseVo;
import io.gulimall.constant.OrderConstant;
import io.gulimall.constant.PayConstant;
import io.gulimall.entity.order.OrderItemEntity;
import io.gulimall.entity.order.PaymentInfoEntity;
import io.gulimall.enume.OrderStatusEnum;
import io.gulimall.service.cart.CartService;
import io.gulimall.vo.cart.CartItemVo;
import io.gulimall.entity.product.SkuInfoEntity;
import io.gulimall.entity.product.SpuInfoEntity;
import io.gulimall.service.product.SkuInfoService;
import io.gulimall.service.product.SpuInfoService;
import io.gulimall.service.ware.WareSkuService;
import io.gulimall.service.ware.WareInfoService;
import io.gulimall.vo.FareVo;
import io.gulimall.vo.OrderItemVo;
import io.gulimall.vo.WareSkuLockVo;
import io.gulimall.exception.BizCodeEnum;
import io.gulimall.schedule.OrderTimeoutScheduler;
import io.gulimall.interceptor.LoginInterceptor;
import io.gulimall.service.order.OrderItemService;
import io.gulimall.service.order.PaymentInfoService;
import io.gulimall.to.order.OrderCreateTo;
import io.gulimall.vo.order.OrderConfirmVo;
import io.gulimall.vo.order.OrderSubmitVo;
import io.gulimall.vo.order.PayAsyncVo;
import io.gulimall.vo.order.PayVo;
import io.gulimall.vo.order.SeckillSkuInfoVo;
import io.gulimall.vo.order.SubmitOrderResponseVo;
import io.gulimall.vo.MemberAddressVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.gulimall.dao.order.OrderDao;
import io.gulimall.entity.order.OrderEntity;
import io.gulimall.service.order.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @Autowired
    private WareSkuService wareSkuService;

    @Autowired
    private WareInfoService wareInfoService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SpuInfoService spuInfoService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderTimeoutScheduler orderTimeoutScheduler;

    @Autowired
    private PaymentInfoService paymentInfoService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() {
        MemberResponseVo memberResponseVo = LoginInterceptor.loginUser.get();
        if (memberResponseVo != null) {
            log.debug("准备生成确认页，用户ID={}", memberResponseVo.getId());
        }
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 获取当前线程的 UserInfoTo，以便在线程池中传递
        io.gulimall.to.cart.UserInfoTo currentUserInfo = io.gulimall.interceptor.CartInterceptor.threadLocal.get();
        CompletableFuture<Void> itemAndStockFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 在线程池中恢复 ThreadLocal
            if (currentUserInfo != null) {
                io.gulimall.interceptor.CartInterceptor.threadLocal.set(currentUserInfo);
            }
            //1. 查出所有选中购物项（简化后直接调用 CartService）
            List<OrderItemVo> checkedItems = getCheckedOrderItems();
            confirmVo.setItems(checkedItems);
            return checkedItems;
        }, executor).thenAcceptAsync((items) -> {
            //4. 库存
            List<Long> skuIds = items.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            Map<Long, Boolean> hasStockMap = wareSkuService.getSkuHasStocks(skuIds).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
            confirmVo.setStocks(hasStockMap);
        }, executor);

        //2. 查出所有收货地址（简化后直接调用 MemberReceiveAddressService）
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            List<MemberReceiveAddressEntity> entities = memberReceiveAddressService.getAddressByUserId(memberResponseVo.getId());
            List<MemberAddressVo> addressByUserId = entities.stream().map(entity -> {
                MemberAddressVo vo = new MemberAddressVo();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
            confirmVo.setMemberAddressVos(addressByUserId);
        }, executor);

        //3. 积分
        confirmVo.setIntegration(memberResponseVo.getIntegration());

        //5. 总价自动计算
        //6. 防重令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId(), token, 30, TimeUnit.MINUTES);
        confirmVo.setOrderToken(token);
        try {
            CompletableFuture.allOf(itemAndStockFuture, addressFuture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return confirmVo;
    }

/**
     * 提交订单 - 单库事务版本
     * 参照微服务实现，使用标准 @Transactional 注解
     */
    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {
        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        responseVo.setCode(0);
        //1. 验证防重令牌
        MemberResponseVo memberResponseVo = LoginInterceptor.loginUser.get();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long execute = redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId()),
                submitVo.getOrderToken());
        if (execute == 0L) {
            //1.1 防重令牌验证失败
            responseVo.setCode(1);
            log.warn("用户{}提交订单失败，防重令牌校验不通过", memberResponseVo.getId());
            return responseVo;
        }

        //2. 创建订单、订单项
        FareVo fareVo = wareInfoService.getFare(submitVo.getAddrId());
        OrderCreateTo order = createOrderTo(memberResponseVo, submitVo, fareVo);

        //3. 验价
        BigDecimal payAmount = order.getOrder().getPayAmount();
        BigDecimal payPrice = submitVo.getPayPrice();
        log.info("验价：计算金额={}，请求金额={}", payAmount, payPrice);
        if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
            //4. 保存订单
            log.info("开始保存订单...");
            saveOrder(order);
            log.info("订单保存成功，订单号: {}", order.getOrder().getOrderSn());
            //5. 锁定库存
            List<OrderItemVo> orderItemVos = order.getOrderItems().stream().map((item) -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                orderItemVo.setSkuId(item.getSkuId());
                orderItemVo.setCount(item.getSkuQuantity());
                return orderItemVo;
            }).collect(Collectors.toList());
            WareSkuLockVo lockVo = new WareSkuLockVo();
            lockVo.setOrderSn(order.getOrder().getOrderSn());
            lockVo.setLocks(orderItemVos);
            log.info("开始锁定库存，订单号: {}, SKU数量: {}", lockVo.getOrderSn(), lockVo.getLocks().size());
            try {
                wareSkuService.orderLockStock(lockVo);
                log.info("锁定库存成功");
                //5.1 锁定库存成功
                responseVo.setOrder(order.getOrder());
                responseVo.setCode(0);
                orderTimeoutScheduler.scheduleClose(order.getOrder());
                //清除购物车记录
                BoundHashOperations<String, Object, Object> ops =
                        redisTemplate.boundHashOps(CartConstant.CART_PREFIX + memberResponseVo.getId());
                for (OrderItemEntity orderItem : order.getOrderItems()) {
                    ops.delete(orderItem.getSkuId().toString());
                }
                log.info("订单{}创建成功并锁定库存，等待支付", order.getOrder().getOrderSn());
                return responseVo;
            } catch (NoStockException e) {
                //5.1 锁定库存失败
                log.error("订单{}锁定库存失败: {}", order.getOrder().getOrderSn(), e.getMessage());
                throw e;
            }
        } else {
            //验价失败
            responseVo.setCode(2);
            log.warn("订单{}验价失败，计算金额={}，请求金额={}", order.getOrder().getOrderSn(), payAmount, payPrice);
            return responseVo;
        }
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        OrderEntity order_sn = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));

        return order_sn;
    }

    /**
     * 关闭过期的的订单
     * @param orderEntity
     */
    @Override
    public void closeOrder(OrderEntity orderEntity) {
        //因为消息发送过来的订单已经是很久前的了，中间可能被改动，因此要查询最新的订单
        OrderEntity newOrderEntity = this.getById(orderEntity.getId());
        //如果订单还处于新创建的状态，说明超时未支付，进行关单
        if (newOrderEntity != null && newOrderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()) {
            OrderEntity updateOrder = new OrderEntity();
            updateOrder.setId(newOrderEntity.getId());
            updateOrder.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(updateOrder);

            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(newOrderEntity,orderTo);
            wareSkuService.unlock(orderTo);
            log.info("订单{}超时未支付，已自动关单并释放库存", newOrderEntity.getOrderSn());
        }
    }

    @Override
    public PageUtils getMemberOrderPage(Map<String, Object> params) {
        MemberResponseVo memberResponseVo = LoginInterceptor.loginUser.get();
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<OrderEntity>().eq("member_id", memberResponseVo.getId()).orderByDesc("create_time");
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),queryWrapper
        );
        List<OrderEntity> entities = page.getRecords().stream().map(order -> {
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", order.getOrderSn()));
            order.setItems(orderItemEntities);
            return order;
        })
        // 过滤掉没有订单项的空白订单
        .filter(order -> order.getItems() != null && !order.getItems().isEmpty())
        .collect(Collectors.toList());
        page.setRecords(entities);
        return new PageUtils(page);
    }

    @Override
    public PayVo getOrderPay(String orderSn) {
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        PayVo payVo = new PayVo();
        payVo.setOut_trade_no(orderSn);
        BigDecimal payAmount = orderEntity.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVo.setTotal_amount(payAmount.toString());

        List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        OrderItemEntity orderItemEntity = orderItemEntities.get(0);
        payVo.setSubject(orderItemEntity.getSkuName());
        payVo.setBody(orderItemEntity.getSkuAttrsVals());
        return payVo;
    }

    @Override
    public void handlerPayResult(PayAsyncVo payAsyncVo) {
        //保存交易流水
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        String orderSn = payAsyncVo.getOut_trade_no();
        infoEntity.setOrderSn(orderSn);
        infoEntity.setAlipayTradeNo(payAsyncVo.getTrade_no());
        infoEntity.setSubject(payAsyncVo.getSubject());
        String trade_status = payAsyncVo.getTrade_status();
        infoEntity.setPaymentStatus(trade_status);
        infoEntity.setCreateTime(new Date());
        infoEntity.setCallbackTime(payAsyncVo.getNotify_time());
        paymentInfoService.save(infoEntity);

        //判断交易状态是否成功
        if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")) {
            baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
            log.info("订单{}支付完成，状态={}", orderSn, trade_status);
        } else {
            log.warn("收到订单{}支付回调，状态={}", orderSn, trade_status);
        }
    }

    @Transactional
    @Override
    public void createSeckillOrder(SeckillOrderTo orderTo) {
        MemberResponseVo memberResponseVo = LoginInterceptor.loginUser.get();
        //1. 创建订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderTo.getOrderSn());
        orderEntity.setMemberId(orderTo.getMemberId());
        if (memberResponseVo!=null){
            orderEntity.setMemberUsername(memberResponseVo.getUsername());
        }
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setCreateTime(new Date());
        orderEntity.setPayAmount(orderTo.getSeckillPrice().multiply(new BigDecimal(orderTo.getNum())));
        this.save(orderEntity);
        orderTimeoutScheduler.scheduleClose(orderEntity);
        log.info("创建秒杀订单{}，用户={}, 数量={}", orderTo.getOrderSn(), orderTo.getMemberId(), orderTo.getNum());
        //2. 创建订单项 - 直接调用 skuInfoService
        SkuInfoEntity skuInfo = skuInfoService.getById(orderTo.getSkuId());
        if (skuInfo != null) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderSn(orderTo.getOrderSn());
            orderItemEntity.setSpuId(skuInfo.getSpuId());
            orderItemEntity.setCategoryId(skuInfo.getCatalogId());
            orderItemEntity.setSkuId(skuInfo.getSkuId());
            orderItemEntity.setSkuName(skuInfo.getSkuName());
            orderItemEntity.setSkuPic(skuInfo.getSkuDefaultImg());
            orderItemEntity.setSkuPrice(skuInfo.getPrice());
            orderItemEntity.setSkuQuantity(orderTo.getNum());
            orderItemService.save(orderItemEntity);
        }
    }

    private void saveOrder(OrderCreateTo orderCreateTo) {
        OrderEntity order = orderCreateTo.getOrder();
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        this.save(order);
        orderItemService.saveBatch(orderCreateTo.getOrderItems());
    }

    private OrderCreateTo createOrderTo(MemberResponseVo memberResponseVo, OrderSubmitVo submitVo, FareVo fareVo) {
        //用IdWorker生成订单号
        String orderSn = IdWorker.getTimeId();
        //构建订单
        OrderEntity entity = buildOrder(memberResponseVo, submitVo, orderSn, fareVo);
        //构建订单项
        List<OrderItemEntity> orderItemEntities = buildOrderItems(orderSn);
        //计算价格
        compute(entity, orderItemEntities);
        OrderCreateTo createTo = new OrderCreateTo();
        createTo.setOrder(entity);
        createTo.setOrderItems(orderItemEntities);
        return createTo;
    }

    private void compute(OrderEntity entity, List<OrderItemEntity> orderItemEntities) {
        //总价
        BigDecimal total = BigDecimal.ZERO;
        //优惠价格
        BigDecimal promotion=new BigDecimal("0.0");
        BigDecimal integration=new BigDecimal("0.0");
        BigDecimal coupon=new BigDecimal("0.0");
        //积分
        Integer integrationTotal = 0;
        Integer growthTotal = 0;

        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            total=total.add(orderItemEntity.getRealAmount());
            promotion=promotion.add(orderItemEntity.getPromotionAmount());
            integration=integration.add(orderItemEntity.getIntegrationAmount());
            coupon=coupon.add(orderItemEntity.getCouponAmount());
            integrationTotal += orderItemEntity.getGiftIntegration();
            growthTotal += orderItemEntity.getGiftGrowth();
        }

        entity.setTotalAmount(total);
        entity.setPromotionAmount(promotion);
        entity.setIntegrationAmount(integration);
        entity.setCouponAmount(coupon);
        entity.setIntegration(integrationTotal);
        entity.setGrowth(growthTotal);

        //付款价格=商品价格+运费
        entity.setPayAmount(entity.getFreightAmount().add(total));

        //设置删除状态(0-未删除，1-已删除)
        entity.setDeleteStatus(0);
    }

    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        List<OrderItemVo> checkedItems = getCheckedOrderItems();
        List<OrderItemEntity> orderItemEntities = checkedItems.stream().map((item) -> {
            OrderItemEntity orderItemEntity = buildOrderItem(item);
            //1) 设置订单号
            orderItemEntity.setOrderSn(orderSn);
            return orderItemEntity;
        }).collect(Collectors.toList());
        return orderItemEntities;
    }

    private OrderItemEntity buildOrderItem(OrderItemVo item) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        Long skuId = item.getSkuId();
        //2) 设置sku相关属性
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(item.getTitle());
        orderItemEntity.setSkuAttrsVals(StringUtils.collectionToDelimitedString(item.getSkuAttrValues(), ";"));
        orderItemEntity.setSkuPic(item.getImage());
        orderItemEntity.setSkuPrice(item.getPrice());
        orderItemEntity.setSkuQuantity(item.getCount());
        //3) 通过skuId查询spu相关属性并设置 - 直接调用 spuInfoService
        SpuInfoEntity spuInfo = spuInfoService.getSpuBySkuId(skuId);
        if (spuInfo != null) {
            orderItemEntity.setSpuId(spuInfo.getId());
            orderItemEntity.setSpuName(spuInfo.getSpuName());
            orderItemEntity.setSpuBrand(spuInfo.getBrandName());
            orderItemEntity.setCategoryId(spuInfo.getCatalogId());
        }
        //4) 商品的优惠信息(不做)

        //5) 商品的积分成长，为价格x数量
        orderItemEntity.setGiftGrowth(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());
        orderItemEntity.setGiftIntegration(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());

        //6) 订单项订单价格信息
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //7) 实际价格
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity()));
        BigDecimal realPrice = origin.subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(realPrice);

        return orderItemEntity;
    }

    private OrderEntity buildOrder(MemberResponseVo memberResponseVo,
                                   OrderSubmitVo submitVo,
                                   String orderSn,
                                   FareVo fareVo) {

        OrderEntity orderEntity =new OrderEntity();

        orderEntity.setOrderSn(orderSn);

        //2) 设置用户信息
        orderEntity.setMemberId(memberResponseVo.getId());
        orderEntity.setMemberUsername(memberResponseVo.getUsername());

        //3) 使用预先计算好的邮费和收件人信息
        BigDecimal fare = fareVo.getFare();
        orderEntity.setFreightAmount(fare);
        MemberAddressVo address = fareVo.getAddress();
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());

        //4) 设置订单相关的状态信息
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setConfirmStatus(0);
        orderEntity.setAutoConfirmDay(7);

        return orderEntity;
    }

    @Override
    public boolean mockPay(String orderSn) {
        // 1. 查询订单
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        if (orderEntity == null) {
            log.warn("模拟支付失败：订单{}不存在", orderSn);
            return false;
        }

        // 2. 检查订单状态（只有待支付的订单才能支付）
        if (orderEntity.getStatus() != OrderStatusEnum.CREATE_NEW.getCode()) {
            log.warn("模拟支付失败：订单{}状态不是待支付，当前状态={}", orderSn, orderEntity.getStatus());
            return false;
        }

        // 3. 创建模拟支付流水
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(orderSn);
        paymentInfo.setAlipayTradeNo("MOCK_" + System.currentTimeMillis()); // 模拟交易号
        paymentInfo.setPaymentStatus("TRADE_SUCCESS");
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCreateTime(new Date());

        // 获取订单项信息作为支付主题
        List<OrderItemEntity> orderItems = orderItemService.list(
                new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        if (orderItems != null && !orderItems.isEmpty()) {
            paymentInfo.setSubject(orderItems.get(0).getSkuName());
        } else {
            paymentInfo.setSubject("模拟支付");
        }
        paymentInfo.setTotalAmount(orderEntity.getPayAmount());
        paymentInfoService.save(paymentInfo);

        // 4. 更新订单状态为已支付
        baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);

        log.info("模拟支付成功：订单{}已完成支付，金额={}", orderSn, orderEntity.getPayAmount());
        return true;
    }

    /**
     * 获取选中的购物项并转换为 OrderItemVo
     */
    private List<OrderItemVo> getCheckedOrderItems() {
        List<CartItemVo> checkedItems = cartService.getCheckedItems();
        return checkedItems.stream().map(item -> {
            OrderItemVo vo = new OrderItemVo();
            vo.setSkuId(item.getSkuId());
            vo.setCheck(item.getCheck());
            vo.setTitle(item.getTitle());
            vo.setImage(item.getImage());
            vo.setSkuAttrValues(item.getSkuAttrValues());
            vo.setPrice(item.getPrice());
            vo.setCount(item.getCount());
            vo.setTotalPrice(item.getTotalPrice());
            return vo;
        }).collect(Collectors.toList());
    }

}