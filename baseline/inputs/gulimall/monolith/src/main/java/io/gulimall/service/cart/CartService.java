package io.gulimall.service.cart;

import io.gulimall.vo.cart.CartItemVo;
import io.gulimall.vo.cart.CartVo;

import java.util.List;

/**
 * 购物车业务接口，来自原 cart 微服务。
 */
public interface CartService {

    CartItemVo addCartItem(Long skuId, Integer num);

    CartItemVo getCartItem(Long skuId);

    CartVo getCart();

    void checkCart(Long skuId, Integer isChecked);

    void changeItemCount(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVo> getCheckedItems();
}

