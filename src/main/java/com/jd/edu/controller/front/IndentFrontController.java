package com.jd.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Indent;
import com.jd.edu.entity.IndentDetail;
import com.jd.edu.entity.User;
import com.jd.edu.service.CommodityService;
import com.jd.edu.service.IndentDetailService;
import com.jd.edu.service.IndentService;
import com.jd.edu.service.UserService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.exceptionhandler.EsException;
import com.jd.edu.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/edu/front/indent")
@CrossOrigin
public class IndentFrontController{

    @Autowired
    private IndentService indentService;

    @Autowired
    private IndentDetailService indentDetailService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserService userService;

    //查询该用户这次要支付的订单详情
    @GetMapping("getOneIndentForPaid/{id}")
    public R getOneIndentForPaid(@PathVariable String id){
        //获得订单基本信息
        Indent indent = indentService.getByIndentId(id);
        //获得订单的详情信息
        Page<IndentDetail> page = new Page<>();
        List<IndentDetail> list = indentDetailService.selectAllIndentDetail(page, id);
        long total = page.getTotal();
        return R.ok().data("indent",indent).data("list",list).data("total",total);
    }

    //未支付订单 -> 支付
    @PostMapping("updateIndentStatus")
    public R updateIndentStatus(@RequestBody Indent indent){
        indent.setIndentStatus(1);
        boolean result = indentService.updateById(indent);
        return result ? R.ok() : R.error();
    }

    //取消订单 将订单详情表中商品退回到商品库存中
    @GetMapping("cancelIndent/{id}")
    public R cancelIndent(@PathVariable String id){
        //对订单详情操作
        List<IndentDetail> list = indentDetailService.selectAllIndentDetail(null, id);
        for(IndentDetail indentDetail : list){
            indentDetailService.updateIndentToCommodity(indentDetail);
        }
        //清除取消的订单消息
        boolean result = indentService.removeById(id);
        System.out.println(result);
        return result ? R.ok() : R.error();
    }
    
    //显示登陆用户的所有订单信息
    @GetMapping("getAllIndentOfOne/{current}/{limit}")
    public R getAllIndentOfOne(HttpServletRequest request,@PathVariable long current,
                               @PathVariable long limit){
        Page<Indent> pageIndent = new Page<>(current,limit);
        //获取用户信息
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //通过用户id拿到订单大表信息
        List<Indent> list = indentService.getAllByUserId(pageIndent,userId);
        int size = list.size();
        long total = pageIndent.getTotal();//总记录数
        return R.ok().data("list",list).data("size",size).data("total",total);
    }

    //新增Indent
    @GetMapping("insertIndent")
    public R insertIndent(HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        User byId = userService.getById(userId);
        if(byId.getStatus() != 1){
            throw new EsException(20001,"您存在违规行为，无法结算购物车，请联系管理员");
        }
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
