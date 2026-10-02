package com.youlai.mall.service.oms.app;

import com.youlai.mall.model.oms.dto.CartItemDto;

import java.util.List;

/**
 * 购物车业务接口
 *
 * @author haoxr
 * @date 2022/11/13
 */
public interface CartService {

    List<CartItemDto> listCartItems(Long memberId);

    boolean deleteCart();

    boolean addCartItem(Long skuId);

    boolean updateCartItem(CartItemDto cartItem);

    boolean removeCartItem(Long skuId);

    boolean removeCheckedItem();

    boolean checkAll(boolean checked);

}
