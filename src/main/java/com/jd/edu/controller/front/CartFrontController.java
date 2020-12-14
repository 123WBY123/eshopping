package com.jd.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jd.edu.entity.Cart;
import com.jd.edu.service.CartService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/edu/front/cart")
@CrossOrigin
public class CartFrontController {
    @Autowired
    private CartService cartService;


    @GetMapping("getCart")
    public R getCart(HttpServletRequest request) {
        //获得用户id信息
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        //获得购物车大表id
        Cart cart=cartService.getOne(wrapper);
        return R.ok().data("cartId",cart.getCartId());
    }

    @GetMapping("updateCart/{cartTotal}")
    public R updateCart(@PathVariable Float cartTotal,HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        cartService.updateCart(userId,cartTotal);
        return R.ok();
    }
}



