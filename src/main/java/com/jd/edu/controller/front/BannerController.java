package com.jd.edu.controller.front;

import com.jd.edu.entity.Commodity;
import com.jd.edu.service.CommodityService;
import com.jd.edu.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/front/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private CommodityService commodityService;

    //首页页面显示
    @ApiOperation("首页页面显示")
    @GetMapping("show")
    public R show(){
        //8个热销商品 4个普通商品
        List<Commodity> hotComodity = commodityService.getHotCommodity();
        List<Commodity> commonCommodity = commodityService.getCommonCommodity();
        return R.ok().data("hotCommodity",hotComodity).data("commonCommodity",commonCommodity);
    }
}
