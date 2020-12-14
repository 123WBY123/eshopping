package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.CartDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Repository
public interface CartDetailMapper extends BaseMapper<CartDetail> {

    void updateCartDetail(CartDetail cartDetail);

    List<CartDetail> pageCartCondition(Page<CartDetail> pageParam, String cartId);
}
