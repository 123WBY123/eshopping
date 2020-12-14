package com.jd.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Indent;
import com.jd.edu.service.CommodityService;
import com.jd.edu.service.IndentService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.jwt.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-24
 */
@Api(description="订单管理")
@RestController
@RequestMapping("/edu/back/indent")
@CrossOrigin
public class IndentController {

    //访问地址： http://localhost:8001/eduservice/indent/findAll
    @Autowired
    private IndentService indentService;

    @Autowired
    private CommodityService commodityService;

    //1 查询订单所有数据
    //rest风格
    @ApiOperation(value = "所有订单")
    @GetMapping("findAll/{current}/{limit}/{status}")
    public R findAllIndent(@PathVariable long current,
                           @PathVariable long limit,@PathVariable Integer status)
    {
        Page<Indent> pageIndent = new Page<>(current,limit);
        List<Indent> list = indentService.selectAllIndent(pageIndent,status);
        long total = pageIndent.getTotal();//总记录数
        return R.ok().data("items",list).data("total",total);
    }
    //2 逻辑删除订单的方法
    @ApiOperation(value = "逻辑删除订单")
    @DeleteMapping("{indentId}")
    public R removeIndent(@ApiParam(name = "indentId", value = "订单id", required = true)
                          @PathVariable String indentId) {
        boolean flag = indentService.removeById(indentId);
        return flag ? R.ok() : R.error();
    }

    //根据订单id进行查询
    @GetMapping("getIndent/{indentId}")
    public R getTeacher(@PathVariable String indentId) {
        Indent indent = indentService.getByIndentId(indentId);
        return R.ok().data("indent",indent);
    }

    //订单物流情况修改
    @PostMapping("updateIndent")
    public R updateIndent(@RequestBody Indent indent){
        boolean result = indentService.updateById(indent);
        return result ? R.ok() : R.error();
    }

    //订单到货处理
    @GetMapping("updateIndentStatus/{indentId}")
    public R updateIndentStatus(@PathVariable String indentId){
        Indent indent = new Indent();
        indent.setIndentId(indentId);
        indent.setIndentStatus(2);
        boolean result = indentService.updateById(indent);
        return result ? R.ok() : R.error();
    }

    //新增Indent
    @GetMapping("insertIndent")
    public R insertIndent(HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Indent indent = new Indent();
        //新增的订单id
        indent.setIndentUserId(userId);
        indent.setIndentTotal((float) 0);
        indentService.save(indent);
        //通过用户id拿到订单id
        String indentId = indentService.getByUserId(userId).getIndentId();
        return R.ok().data("indentId",indentId);
    }

    //更新indent
    @PostMapping("updateCart")
    public R updateCart(@RequestParam(value = "indentId",required = false) String indentId,@RequestParam(value = "indentTotal",required = false) Float indentTotal){
        Indent indent = new Indent();
        indent.setIndentId(indentId);
        indent.setIndentTotal(indentTotal);
        Integer result = indentService.updateIndentById(indent);
        return result == 1 ? R.ok() : R.error();
    }
}



