package com.jd.edu.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQuery {
    @ApiModelProperty(value = "用户昵称,模糊查询")
    private String userNickname;
    @ApiModelProperty(value = "性别查询")
    private Integer userSex;
}
