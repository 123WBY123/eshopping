package com.jd.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.KindBase;
import com.jd.edu.service.KindBaseService;
import com.jd.edu.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "商品类别类")
@RestController
@RequestMapping("/edu/front/kindBase")
@CrossOrigin
public class KindBaseFrontController {
    @Autowired
    private KindBaseService kindBaseService;

    @ApiOperation("全选")
    @GetMapping("selectAll")
    public R selectAll(){
        List<KindBase> baseList = kindBaseService.list(null);
        return R.ok().data("list",baseList);
    }

    @ApiOperation("全选分页")
    @GetMapping("pageListKindBase/{current}/{limit}")
    public R pageListKindBase(@PathVariable long current,
                              @PathVariable long limit) {
        Page<KindBase> pageUser = new Page<>(current, limit);
        QueryWrapper<KindBase> wrapper = new QueryWrapper<>();
        //wrapper.ne("status",0);
        kindBaseService.page(pageUser, wrapper);
        long total = pageUser.getTotal();
        List<KindBase> records = pageUser.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("查询单个商品类别")
    @GetMapping("getOneKindBase/{id}")
    public R getOneBaseKind(@PathVariable String id){
        KindBase kindBase = kindBaseService.getById(id);
        return R.ok().data("kindBase",kindBase);
    }

    @ApiOperation("修改商品类别")
    @PostMapping("updateKindBase")
    public R updateKindBase(@RequestBody KindBase kindBase){
        boolean result = kindBaseService.updateKindBaseById(kindBase);
        return result == true ? R.ok() : R.error();
    }

    @ApiOperation("添加商品类别")
    @PostMapping("save")
    public R save(@RequestBody KindBase kindBase){
        boolean result = kindBaseService.saveKindBase(kindBase);
        return result == true ? R.ok() : R.error();
    }

    @ApiOperation("逻辑删除商品类别")
    @GetMapping("removeById/{id}")
    public R removeById(@PathVariable String id){
        boolean result = kindBaseService.removeByKindBaseId(id);
        return result ? R.ok() : R.error();
    }

    @ApiOperation("显示所有被删除的类别")
    @GetMapping("selectAllDelete/{current}/{limit}")
    public R selectAllDelete(@PathVariable long current,
                             @PathVariable long limit){
        Page<KindBase> pageUser = new Page<>(current, limit);
        List<KindBase> baseList = kindBaseService.showKindDelete(pageUser);
        long total = pageUser.getTotal();
        return R.ok().data("rows",baseList).data("total",total);
    }

    @ApiOperation("将被删除类别重启")
    @GetMapping("updateKindToUse/{id}")
    public R updateKindToUse(@PathVariable String id){
        Integer result = kindBaseService.updateKindToUse(id);
        return result == 1 ? R.ok() : R.error();
    }
}
