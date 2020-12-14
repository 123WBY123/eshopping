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
 * @since 2020-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="KindBase对象", description="")
public class KindBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品类别id")
    @TableId(value = "kind_id", type = IdType.ID_WORKER_STR)
    private String kindId;

    @ApiModelProperty(value = "商品类别名")
    private String kindName;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除 1 删除 0 未删除")
    @TableLogic
    private Boolean isDeleted;



}
