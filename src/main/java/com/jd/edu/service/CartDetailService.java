package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.CartDetail;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
public interface CartDetailService extends IService<CartDetail> {

    Map<String, Object> pageCartCondition(Page<CartDetail> pageParam,String cartId);

    boolean saveGoods(CartDetail cartDetail);

    void updateCartDetail(CartDetail cartDetail);
}
