package com.jd.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.KindBase;
import com.jd.edu.service.CommodityService;
import com.jd.edu.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Api("商品类")
@RestController
@RequestMapping("/edu/back/commodity")
@CrossOrigin
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    //分页查询商品的方法
    @GetMapping("getCommodityFrontList/{page}/{limit}")
    public R getCommodityFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<Commodity> pageCommodity = new Page<>(page, limit);
        Map<String, Object> map = commodityService.getCommodityFrontList(pageCommodity);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //分页查询上架商品的方法
    @GetMapping("getCommodityOnShelvesFrontList/{page}/{limit}")
    public R getCommodityOnShelvesFrontList(@PathVariable long page, @PathVariable long limit){
        Page<Commodity> pageCommodity = new Page<>(page,limit);
        Map<String, Object> map = commodityService.getCommodityOnShelvesFrontList(pageCommodity);
        return R.ok().data(map);
    }

    //分页按名称查询商品
    @PostMapping("getCommodityByNameFrontList/{page}/{limit}")
    public R getCommodityByNameFrontList(@PathVariable long page, @PathVariable long limit, @RequestBody Commodity commodity){
        Page<Commodity> pageCommodity = new Page<>(page, limit);
        Map<String,Object> map = commodityService.getCommodityByNameFrontList(pageCommodity,commodity);
        return R.ok().data(map);
    }

    //分页按类别查询商品
    @PostMapping("getCommodityByKindFrontList/{page}/{limit}")
    public R getCommodityByKindFrontList(@PathVariable long page, @PathVariable long limit, @RequestBody KindBase kindBase){
        Page<Commodity> pageCommodity = new Page<>(page, limit);
        Map<String, Object> map = commodityService.getCommodityByKindFrontList(pageCommodity, kindBase);
        return R.ok().data(map);
    }

    @ApiOperation("分页查询下架商品")
    @GetMapping("selectAllCommodityOffShelvesFrontList/{page}/{limit}")
    public R selectAllCommodityOffShelvesFrontList(@PathVariable long page, @PathVariable long limit){
        Page<Commodity> pageCommodity = new Page<>(page,limit);
        Map<String, Object> map = commodityService.selectAllCommodityOffShelvesFrontList(pageCommodity);
        return R.ok().data(map);
    }

    @ApiOperation("分页查询待审核商品")
    @GetMapping("selectAllCommodityReadyToCheckList/{page}/{limit}")
    public R selectAllCommodityReadyToCheckList(@PathVariable long page, @PathVariable long limit){
        Page<Commodity> pageCommodity = new Page<>(page,limit);
        Map<String, Object> map = commodityService.selectAllCommodityReadyToCheckList(pageCommodity);
        return R.ok().data(map);
    }

    //上线新商品 第一次添加 直接添加 若补全信息 则修改数据
    @ApiOperation("上线新商品")
    @PostMapping("addCommodity")
    public R addCommodity(@RequestBody Commodity commodity){
        //第一次添加信息 即添加基本信息
        if(commodity.getCommodityId() == null){
            String commodityId = commodityService.saveCommodity(commodity);
            return R.ok().data("commodityId",commodityId);
        }
        //存在id 第二次添加 补全信息
        int result = commodityService.updateCommodity(commodity);
        return result == 1 ? R.ok().data("commodityId",commodity.getCommodityId()) : R.error();
    }

    //获得单个数据
    @ApiOperation("通过id获得商品信息")
    @GetMapping("getOneCommodity/{id}")
    public R getOneCommodity(@PathVariable String id){
        Commodity commodity = commodityService.selectCommodityById(id);
        return R.ok().data("commodity",commodity);
    }

    /*****************************************后台商品调用的方法****************************************************************/
    @ApiOperation("查询商品信息")
    @GetMapping("pageCommodity/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        /**
         * page -> current
         * limit -> size
         */
        Page<Commodity> pageParam = new Page<>(page, limit);
        commodityService.page(pageParam, null);
        List<Commodity> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "带条件查询商品")
    @PostMapping("pageCommodityByKind/{current}/{limit}")
    public R pageCommodityByKind(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) Commodity commodity) {
        //创建page对象
        Page<Commodity> pageCommodity = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String kind = commodity.getCommodityKind();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(kind)) {
            //构建条件
            wrapper.eq("commodity_kind", kind);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        commodityService.page(pageCommodity, wrapper);

        long total = pageCommodity.getTotal();//总记录数
        List<Commodity> records = pageCommodity.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "通过名称查询商品")
    @PostMapping("pageCommodityByName/{current}/{limit}")
    public R pageCommodityByName(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) Commodity commodity) {
        //创建page对象
        Page<Commodity> pageCommodity = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = commodity.getCommodityName();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("commodity_kind", name);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        commodityService.page(pageCommodity, wrapper);

        long total = pageCommodity.getTotal();//总记录数
        List<Commodity> records = pageCommodity.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("删除商品信息")
    @DeleteMapping("deleteCommodity/{id}")
    public R deleteCommodity(@PathVariable String id) {
        boolean result = commodityService.removeById(id);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("修改商品信息")
    @PostMapping("updateCommodity")
    public R updateCommodity(@RequestBody Commodity commodity) {
        System.out.println(commodity);
        int result = commodityService.updateCommodity(commodity);
        return result == 1 ? R.ok() : R.error();
    }

    @ApiOperation("商品上架")
    @PostMapping("setCommodityOnShelves/{id}")
    public R setCommodityOnShelves(@PathVariable String id) {
        int result = commodityService.setCommodityOnShelves(id);
        return result == 1 ? R.ok() : R.error();
    }

    @ApiOperation("商品下架")
    @PostMapping("setCommodityOffShelves/{id}")
    public R setCommodityOffShelves(@PathVariable String id) {
        int result = commodityService.setCommodityOffShelves(id);
        return result == 1 ? R.ok() : R.error();
    }

    @ApiOperation("按类别查询商品")
    @PostMapping("selectCommodityByKind/{kindName}")
    public R selectCommodityByKind(@PathVariable String kindName){
        List<Commodity> result = commodityService.selectCommodityByKind(kindName);
        return R.ok().data("list",result);
    }

    /*所有商品带商品类别明和来源的方法*/
    @ApiOperation("所有商品带商品类别明和来源的方法")
    @GetMapping("selectAllCommodityWithKindAndSource/{current}/{limit}")
    public R selectAllCommodityWithKindAndSource(@PathVariable long current, @PathVariable long limit) {
        Page<Commodity> pageCommodity = new Page<>(current,limit);
        List<Commodity> result = commodityService.selectAllCommodityWithKindAndSource(pageCommodity);
        return R.ok().data("list", result);
    }

    @ApiOperation("按id查询商品")
    @GetMapping("selectCommodityById/{id}")
    public R selectCommodityById(@PathVariable String id){
        Commodity commodity = commodityService.selectCommodityById(id);
        return R.ok().data("items",commodity);
    }
}

