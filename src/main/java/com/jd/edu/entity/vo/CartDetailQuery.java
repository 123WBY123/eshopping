package com.jd.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartDetailQuery {
    @ApiModelProperty(value = "购物车Id")
    private String cartId;
    @ApiModelProperty(value = "商品Id")
    private String goodsId;
}
