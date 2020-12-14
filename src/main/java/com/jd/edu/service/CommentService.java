package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.Comment;
import com.jd.edu.entity.vo.CommentQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-31
 */
public interface CommentService extends IService<Comment> {

    List<Comment> selectAllComment(Page<Comment> pageComment, CommentQuery commentQuery);

    int countAdd(String content);


    Comment selectOne(String commentId);

    List<Comment> selectByCom(String commodityId);

    boolean saveCom(String commodityId,String content,String userId);

}
