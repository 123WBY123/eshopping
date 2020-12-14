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
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Commodity对象", description = "")
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    private KindBase kindBase;

    @ApiModelProperty(value = "商品唯一标识")
    @TableId(value = "commodity_id", type = IdType.ID_WORKER_STR)
    private String commodityId;

    @ApiModelProperty(value = "外键(商品类别)")
    private String commodityKind;

    @ApiModelProperty(value = "上传商品的用户id(外键)")
    private String commoditySource;

    @ApiModelProperty(value = "商品名称")
    private String commodityName;

    @ApiModelProperty(value = "商品状态(0已经卖过了/1未上架（待审核）/2上架) ")
    private Integer commodityStatus;

    @ApiModelProperty(value = "商品价格")
    private Float commodityPrice;

    @ApiModelProperty(value = "图片在服务器存储的地址")
    private String commodityPicture;

    @ApiModelProperty(value = "商品详情描述")
    private String commodityDescript;

    @ApiModelProperty(value = "商品适用性别(0 男,1女)")
    private Integer commoditySex;

    @ApiModelProperty(value = "商品的颜色信息")
    private String commodityColor;

    @ApiModelProperty(value = "商品的库存数量")
    private Integer commodityStock;

    @ApiModelProperty(value = "分为1-9下拉菜单")
    private Integer commodityWearlevel;

    @ApiModelProperty(value = "是否为热销商品，1或0")
    private Integer commodityPopuler;

    @ApiModelProperty(value = "买家名称(不要放id，放名字就行)")
    private String commodityTo;

    @ApiModelProperty(value = "商品的创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "商品的修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
