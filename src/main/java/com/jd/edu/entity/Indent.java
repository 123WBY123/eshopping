package com.jd.edu.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2020-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Indent对象", description="")
public class Indent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "indent_id", type = IdType.ID_WORKER_STR)
    private String indentId;

    @ApiModelProperty(value = "买家")
    private String indentUserId;

    @ApiModelProperty(value = "卖家")
    private String indentReceviceId;

    @ApiModelProperty(value = "0未支付/1支付/2到货")
    private Integer indentStatus;

    @ApiModelProperty(value = "0微信 0微信/1支付宝")
    private Integer indentWay;

    @ApiModelProperty(value = "订单物流")
    private String indentAddress;

    @ApiModelProperty(value = "逻辑删除订单")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "订单总金额")
    private Float indentTotal;

    @ApiModelProperty(value = "订单详情描述")
    private String indentDescript;

    @ApiModelProperty(value = "订单的创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "订单的修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    //外键 买家
    private User indentUser;

}
