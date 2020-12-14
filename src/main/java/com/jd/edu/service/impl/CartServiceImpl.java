package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Cart;
import com.jd.edu.mapper.CartMapper;
import com.jd.edu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartService cartService;
    @Override
    public void updateCart(String userId, Float cartTotal) {
        cartService.updateCart(userId,cartTotal);
    }
}
