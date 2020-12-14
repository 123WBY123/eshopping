package com.jd.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Address对象", description="")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址id")
    @TableId(value = "address_id", type = IdType.ID_WORKER_STR)
    private String addressId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "邮编")
    private Integer addressPostcode;

    @ApiModelProperty(value = "用户住址")
    private String userAddress;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreat;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
