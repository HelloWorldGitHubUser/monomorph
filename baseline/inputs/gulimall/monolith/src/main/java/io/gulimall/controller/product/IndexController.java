package io.gulimall.controller.product;

import io.gulimall.service.cart.CartService;
import io.gulimall.vo.cart.CartVo;
import io.gulimall.entity.product.CategoryEntity;
import io.gulimall.service.product.CategoryService;
import io.gulimall.vo.product.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @GetMapping({"/", "index.html"})
    public String getIndex(Model model) {
        //获取所有的一级分类
        List<CategoryEntity> catagories = categoryService.getLevel1Catagories();
        model.addAttribute("catagories", catagories);
        // 获取购物车数量
        try {
            CartVo cart = cartService.getCart();
            model.addAttribute("cartCount", cart.getCountNum());
        } catch (Exception e) {
            model.addAttribute("cartCount", 0);
        }
        return "product/templates/index";
    }

    @GetMapping("index/json/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCategoryMap() {
        return categoryService.getCatalogJsonDbWithSpringCache();
    }

}
