package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.Cart;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
public interface CartService extends IService<Cart> {
    //    Cart turnCart(String userId);
    void updateCart(String userId,Float cartTotal);
}
