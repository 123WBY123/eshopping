package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.CartDetail;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.IndentDetail;
import com.jd.edu.mapper.CartDetailMapper;
import com.jd.edu.mapper.CommodityMapper;
import com.jd.edu.mapper.IndentDetailMapper;
import com.jd.edu.mapper.IndentMapper;
import com.jd.edu.service.IndentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Service
public class IndentDetailServiceImpl extends ServiceImpl<IndentDetailMapper, IndentDetail> implements IndentDetailService {

    @Autowired
    private IndentMapper indentMapper;

    @Autowired
    private IndentDetailMapper indentDetailMapper;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<IndentDetail> selectAllIndentDetail(Page<IndentDetail> pageIndentDetail,String id) {
        List<IndentDetail> list = indentDetailMapper.selectAllIndentDetail(pageIndentDetail,id);
        return list;
    }

    //根据获得的订单id值增添信息到订单表
    @Override
    public boolean insertOneIndent(CartDetail cartDetail, String indentId) {
        //更新订单子表数据
        IndentDetail indentDetail = new IndentDetail();
        indentDetail.setIndentId(indentId);
        indentDetail.setCommodityId(cartDetail.getGoodsId());
        indentDetail.setCommodityNum(cartDetail.getCartGoodsNumber());
        int result = baseMapper.insert(indentDetail);
        //减少对应商品的数量
        Commodity commdity = new Commodity();
        commdity.setCommodityStock(cartDetail.getCartGoodsNumber());
        commdity.setCommodityId(cartDetail.getGoodsId());
        commodityMapper.updateCommodityNum(commdity);
        return result == 1 ? true : false;
    }

    @Override
    public int updateIndentToCommodity(IndentDetail indentDetail) {
        int result = indentDetailMapper.updateIndentToCommodity(indentDetail);
        return result;
    }
}
