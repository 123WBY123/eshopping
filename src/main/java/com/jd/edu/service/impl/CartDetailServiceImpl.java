package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.CartDetail;
import com.jd.edu.mapper.CartDetailMapper;
import com.jd.edu.service.CartDetailService;
import com.jd.edu.utils.exceptionhandler.EsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Service
public class CartDetailServiceImpl extends ServiceImpl<CartDetailMapper, CartDetail> implements CartDetailService {
    @Autowired
    private CartDetailMapper cartDetailMapper;
    @Override
    public Map<String, Object> pageCartCondition(Page<CartDetail> pageParam,String cartId) {
        List<CartDetail>list= cartDetailMapper.pageCartCondition(pageParam, cartId);

        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String,Object>map=new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public boolean saveGoods(CartDetail cartDetail) {
        int result = 0;
        String cartId=cartDetail.getCartId();
        String goodsId=cartDetail.getGoodsId();
        QueryWrapper<CartDetail>wrapper=new QueryWrapper<>();
        wrapper.eq("cart_id",cartId);
        wrapper.eq("goods_id",goodsId);
        //先判断是否存在
        result = baseMapper.selectCount(wrapper);
        System.out.println("result      " + result);
        if(result == 1){
            throw new EsException(20001,"该商品已经添加到购物车了，不许重复添加");
        }
        result = baseMapper.insert(cartDetail);
        return true;
    }

    @Override
    public void updateCartDetail(CartDetail cartDetail) {
        cartDetailMapper.updateCartDetail(cartDetail);
    }
}
