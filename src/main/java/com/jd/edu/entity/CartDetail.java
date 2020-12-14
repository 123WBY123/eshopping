package com.jd.edu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CartDetail对象", description="")
public class CartDetail implements Serializable {
    Commodity commodity;
    private static final long serialVersionUID = 1L;

    public CartDetail(String cartId, String goodsId, Integer cartGoodsNumber, Float goodsPrice) {
        this.cartId = cartId;
        this.goodsId = goodsId;
        this.cartGoodsNumber = cartGoodsNumber;
        this.goodsPrice = goodsPrice;
    }

    public CartDetail() {
    }

    @ApiModelProperty(value = "购物车id")
    @TableId(value = "cart_id", type = IdType.ID_WORKER_STR)
    private String cartId;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品数量")
    private Integer cartGoodsNumber;

    @ApiModelProperty(value = "商品价格")
    private Float goodsPrice;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
