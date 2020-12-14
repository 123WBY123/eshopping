package com.jd.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.IndentDetail;
import com.jd.edu.service.IndentDetailService;
import com.jd.edu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/edu/back/indentDetail")
@CrossOrigin
public class IndentDetailController {

    @Autowired
    private IndentDetailService indentDetailService;

    //根据id查询订单
    @GetMapping("findAllIndentDetailById/{current}/{limit}/{indentId}")
    public R findAllIndentDetailById(@PathVariable long current,
                                     @PathVariable long limit,@PathVariable String indentId)
    {
        Page<IndentDetail> pageIndentDetail = new Page<>(current,limit);
        List<IndentDetail> list = indentDetailService.selectAllIndentDetail(pageIndentDetail,indentId);
        long total = pageIndentDetail.getTotal();//总记录数
        return R.ok().data("items",list).data("total",total);
    }

}

