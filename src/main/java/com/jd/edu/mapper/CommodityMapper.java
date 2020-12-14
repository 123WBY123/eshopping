package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.KindBase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Repository
public interface CommodityMapper extends BaseMapper<Commodity> {
    //减少对应商品的数量
    @Update("update commodity set commodity_stock = commodity_stock - #{commodity.commodityStock} where commodity_id = #{commodity.commodityId}")
    int updateCommodityNum(@Param("commodity") Commodity commodity);

    Commodity getInfoIndex();

    //查询所有商品带类别名和买家名
    List<Commodity> selectAllCommodityWithKindAndSource(Page page);

    //查询所有上架商品
    List<Commodity> selectAllCommodityOnShelves(Page page);

    //商品上架
    int setCommodityOnShelves(String id);

    //商品下架
    int setCommodityOffShelves(String id);

    //按类别查询商品
    List<Commodity> selectCommodityByKind(String kindName);

    //分页按名字查询产品的方法
    List<Commodity> getCommodityByNameFrontList(Page<Commodity> pageCommodity,@Param("commodity")  Commodity commodity);

    //分页按类别查询产品的方法
    List<Commodity> getCommodityByKindFrontList(Page<Commodity> pageCommodity, @Param("kindBase") KindBase kindBase);

    //按名称查询商品
    Commodity selectCommodityByName(String commodityName);


    //更新商品信息
    int updateCommodity(Commodity commodity);

    //根据id查询商品信息
    Commodity selectCommodityById(String commodityId);

    //显示所有下架商品
    List<Commodity> selectAllCommodityOffShelvesFrontList(Page page);

    //显示所有待审核商品
    List<Commodity> selectAllCommodityReadyToCheckList(Page page);

    /**************首页显示*********************/
    List<Commodity> getHotCommodity();

    List<Commodity> getCommonCommodity();
}
