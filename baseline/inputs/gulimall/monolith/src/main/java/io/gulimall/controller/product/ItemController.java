package io.gulimall.controller.product;

import io.gulimall.service.product.SkuInfoService;
import io.gulimall.vo.product.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    // 使用正则表达式限制只匹配纯数字的 skuId，避免与 /login.html、/reg.html 等冲突
    @GetMapping("/{skuId:\\d+}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        SkuItemVo skuItemVo=skuInfoService.item(skuId);
        model.addAttribute("item", skuItemVo);
        return "product/templates/item";
    }
}
