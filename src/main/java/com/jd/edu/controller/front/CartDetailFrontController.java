package com.jd.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Cart;
import com.jd.edu.entity.CartDetail;
import com.jd.edu.service.CartDetailService;
import com.jd.edu.service.CartService;
import com.jd.edu.service.IndentDetailService;
import com.jd.edu.service.IndentService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.jwt.JwtUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/edu/front/cartDetail")
@CrossOrigin
public class CartDetailFrontController {
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private CartService cartService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private IndentDetailService indentDetailService;

    @GetMapping("pageCartCondition/{current}/{limit}/{cartId}")
    public R pageCartCondition(@PathVariable long current,@PathVariable long limit,
                               @PathVariable String cartId) {
        Page<CartDetail> pageParam = new Page<>(current, limit);
        QueryWrapper<CartDetail> wrapper = new QueryWrapper<>();
        Map<String,Object> map=cartDetailService.pageCartCondition(pageParam,cartId);
        return R.ok().data(map);
    }


    @ApiOperation(value = "移除商品")
    @GetMapping("removeGoods/{goodsId}/{cartId}")
    public R removeGoods(@ApiParam(name = "goodsId", value = "商品ID", required = true) @PathVariable String goodsId,
                         @ApiParam(name="cartId",value = "购物车Id",required = true) @PathVariable String cartId) {
        QueryWrapper<CartDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        wrapper.eq("cart_id",cartId);
        boolean remove = cartDetailService.remove(wrapper);
        return remove ? R.ok() : R.error();
    }


    @ApiOperation(value = "添加至购物车")
    @GetMapping("addCart/{goodsId}/{goodsPrice}")
    public R addCart(@ApiParam("添加到购物车的商品id") @PathVariable String goodsId,@PathVariable Float goodsPrice, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        //获得购物车大表id
        Cart cart=cartService.getOne(wrapper);
        String cartId= cart.getCartId();
        CartDetail cartDetail=new CartDetail(cartId,goodsId,1,goodsPrice);
        boolean save = cartDetailService.saveGoods(cartDetail);
        return save ? R.ok() : R.error();
    }
    @ApiOperation(value = "操作购物车,并添加到订单子表中 并删除购物车内此数据")
    @PostMapping("updateCartDetail/{indentId}")
    public R updateCartDetail(@PathVariable String indentId,@RequestBody CartDetail cartDetail){
        cartDetailService.updateCartDetail(cartDetail);
        boolean result = indentDetailService.insertOneIndent(cartDetail,indentId);
        return result ? R.ok() : R.error();
    }

}
