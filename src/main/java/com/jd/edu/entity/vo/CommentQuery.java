package com.jd.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentQuery {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论信息的时间")
    private String gmtCreate;

    @ApiModelProperty(value = "用户修改评论信息的最后时间")
    private String gmtModified;
}
