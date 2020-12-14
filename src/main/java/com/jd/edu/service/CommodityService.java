package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.KindBase;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
public interface CommodityService extends IService<Commodity> {

    String saveCommodity(Commodity commodity);

    /*查询所有产品带类别和买家名字*/
    List<Commodity> selectAllCommodityWithKindAndSource(Page<Commodity> pageCommodity);

    /*查询所有上架商品*/
    //List<Commodity> selectAllCommodityOnShelves();

    //商品上架
    int setCommodityOnShelves(String id);

    int setCommodityOffShelves(String id);

    List<Commodity> selectCommodityByKind(String kindName);

    //分页查询产品的方法
    Map<String, Object> getCommodityFrontList(Page<Commodity> pageCommodity);

    //分页查询上架产品的方法
    Map<String, Object> getCommodityOnShelvesFrontList(Page<Commodity> pageCommodity);

    Map<String, Object> getCommodityByNameFrontList(Page<Commodity> pageCommodity,Commodity commodity);

    //分页按类别查询产品
    Map<String,Object>  getCommodityByKindFrontList(Page<Commodity> pageCommodity, KindBase kindBase);

    //更新商品信息
    int updateCommodity(Commodity commodity);

    //根据id查询商品信息
    Commodity selectCommodityById(String commodityId);

    //显示所有下架商品
    Map<String, Object> selectAllCommodityOffShelvesFrontList(Page<Commodity> pageCommodity);

    //显示所有待审核商品
    Map<String, Object> selectAllCommodityReadyToCheckList(Page<Commodity> pageCommodity);

    /*************************首页背景********************/
    //八条热销商品
    List<Commodity> getHotCommodity();
    //四条普通商品
    List<Commodity> getCommonCommodity();
}
