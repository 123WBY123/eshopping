package com.jd.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartQuery {
    @ApiModelProperty(value = "用户id")
    private String cartId;
}
