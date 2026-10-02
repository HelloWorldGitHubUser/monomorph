package io.gulimall.controller.order;

import com.alipay.api.AlipayApiException;
import io.gulimall.config.order.AlipayTemplate;
import io.gulimall.service.order.OrderService;
import io.gulimall.vo.order.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayWebController {

    @Autowired
    private AlipayTemplate alipayTemplate;

    @Autowired
    private OrderService orderService;

    /**
     * 支付宝支付（需要配置沙箱环境）
     */
    @ResponseBody
    @GetMapping(value = "/aliPayOrder", produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        System.out.println("接收到订单信息orderSn：" + orderSn);
        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);
        return pay;
    }

    /**
     * 模拟支付 - 直接完成支付（用于测试，无需配置支付宝沙箱）
     * 访问：/mockPay?orderSn=订单号
     */
    @GetMapping("/mockPay")
    public String mockPay(@RequestParam("orderSn") String orderSn) {
        boolean success = orderService.mockPay(orderSn);
        if (success) {
            // 支付成功，跳转到订单列表页
            return "redirect:/memberOrder.html";
        } else {
            // 支付失败，跳转到订单确认页
            return "redirect:/toTrade";
        }
    }

    /**
     * 模拟支付页面 - 显示订单信息和模拟支付按钮
     * 访问：/mockPayPage?orderSn=订单号
     */
    @GetMapping("/mockPayPage")
    public String mockPayPage(@RequestParam("orderSn") String orderSn, 
                              org.springframework.ui.Model model) {
        PayVo payVo = orderService.getOrderPay(orderSn);
        model.addAttribute("payVo", payVo);
        model.addAttribute("orderSn", orderSn);
        return "order/templates/mockPay";
    }
}
