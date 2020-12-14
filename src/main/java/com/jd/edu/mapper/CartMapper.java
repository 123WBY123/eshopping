package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jd.edu.entity.Cart;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    //   Cart turnCart(String userId);
    void updateCart(String userId,Float cartTotal);
}
