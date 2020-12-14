package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Comment;
import com.jd.edu.entity.vo.CommentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectAllComment(Page page,@Param("commentQuery") CommentQuery commentQuery);

    int countAdd(String content);

    Comment selectOneComment(@Param("commentId") String commentId);

    List<Comment> selectGiComment(@Param("commodityId") String commodityId);

    boolean saveCom(String commodityId,String content,String userId);
}
