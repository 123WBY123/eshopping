package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Comment;
import com.jd.edu.entity.vo.CommentQuery;
import com.jd.edu.mapper.CommentMapper;
import com.jd.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-31
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> selectAllComment(Page<Comment> pageComment, CommentQuery commentQuery) {
        List<Comment> result = commentMapper.selectAllComment(pageComment,commentQuery);
        return result;
    }

    @Override
    public int countAdd(String content) {
        int result = commentMapper.countAdd(content);
        return result;
    }

    @Override
    public Comment selectOne(String commentId) {
        Comment comment = commentMapper.selectOneComment(commentId);
        return comment;
    }

    @Override
    public List<Comment> selectByCom(String commodityId) {
        List<Comment>comment = commentMapper.selectGiComment(commodityId);
        return comment;
    }

    @Override
    public boolean saveCom(String commodityId,String content,String userId){
        boolean result=commentMapper.saveCom(commodityId,content,userId);
        return result;
    }

}
