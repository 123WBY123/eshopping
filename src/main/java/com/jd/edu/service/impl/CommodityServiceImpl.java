package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.KindBase;
import com.jd.edu.mapper.CommodityMapper;
import com.jd.edu.mapper.KindBaseMapper;
import com.jd.edu.service.CommodityService;
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
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private KindBaseMapper kindBaseMapper;

    //第一次增添 需要返回commodity的id
    @Override
    public String saveCommodity(Commodity commodity) {
        //用于记录返回的结果
        int result = 0;
        //禁止相同商品名
        Commodity selectCommodity = commodityMapper.selectCommodityByName(commodity.getCommodityName().trim());
        if(selectCommodity != null){//说明重复
            throw new EsException(20001,"注册商品名重复");
        }
        //增添到数据库
        baseMapper.insert(commodity);
        //查询 返回commodityId
        String commodityId = baseMapper.selectCommodityByName(commodity.getCommodityName()).getCommodityId();
        return commodityId;
    }

    /*所有商品带商品类别明和来源的方法*/
    @Override
    public List<Commodity> selectAllCommodityWithKindAndSource(Page<Commodity> pageCommodity) {
        List<Commodity> result = commodityMapper.selectAllCommodityWithKindAndSource(pageCommodity);
        return result;
    }

    @Override
    public int setCommodityOnShelves(String id) {
        //根据商品类别是否存在 决定是否能上架
        String kindId = commodityMapper.selectCommodityById(id).getCommodityKind();
        KindBase isDeleted = kindBaseMapper.selectById(kindId);
        if(isDeleted == null){ //改商品类别已经删除了
            throw new EsException(20001,"该商品类别已禁用,请先重启类别或暂时不上架");
        }
        int result = commodityMapper.setCommodityOnShelves(id);
        return result;
    }

    @Override
    public int setCommodityOffShelves(String id) {
        int result = commodityMapper.setCommodityOffShelves(id);
        return result;
    }

    //按类别查询
    @Override
    public List<Commodity> selectCommodityByKind(String kindName) {
        List<Commodity> result = commodityMapper.selectCommodityByKind(kindName);
        return result;
    }

    @Override
    public int updateCommodity(Commodity commodity) {
        int result = commodityMapper.updateCommodity(commodity);
        return result;
    }

    @Override
    public Commodity selectCommodityById(String commodityId) {
        Commodity result = commodityMapper.selectCommodityById(commodityId);
        return result;
    }

    /*************************首页背景********************/
    @Override
    public List<Commodity> getHotCommodity() {
        List<Commodity> hotCommodity = commodityMapper.getHotCommodity();
        return hotCommodity;
    }

    @Override
    public List<Commodity> getCommonCommodity() {
        List<Commodity> commonCommodity = commodityMapper.getCommonCommodity();
        return commonCommodity;
    }

    //查询所有未上架商品
    @Override
    public Map<String, Object> selectAllCommodityOffShelvesFrontList(Page<Commodity> pageParam) {
        List<Commodity> list = commodityMapper.selectAllCommodityOffShelvesFrontList(pageParam);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }
    @Override
    public Map<String, Object> selectAllCommodityReadyToCheckList(Page<Commodity> pageParam) {
        List<Commodity> list = commodityMapper.selectAllCommodityReadyToCheckList(pageParam);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    /*************************首页背景********************/
    //分页查询产品的方法


    @Override
    public Map<String, Object> getCommodityFrontList(Page<Commodity> pageParam) {
       /* QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");*/
        //把分页数据封装到pageTeacher对象
        List<Commodity> list = commodityMapper.selectAllCommodityWithKindAndSource(pageParam);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public Map<String, Object> getCommodityOnShelvesFrontList(Page<Commodity> pageParam) {
        List<Commodity> list = commodityMapper.selectAllCommodityOnShelves(pageParam);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public Map<String, Object> getCommodityByNameFrontList(Page<Commodity> pageParam, Commodity commodity) {
        List<Commodity> list = commodityMapper.getCommodityByNameFrontList(pageParam, commodity);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    //分页按类别查询
    @Override
    public Map<String, Object> getCommodityByKindFrontList(Page<Commodity> pageParam, KindBase kindBase) {
        List<Commodity> list = commodityMapper.getCommodityByKindFrontList(pageParam, kindBase);
        //baseMapper.selectPage(pageParam,wrapper);

        //List<Commodity> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }
}
