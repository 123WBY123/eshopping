package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.IndentDetail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Repository
public interface IndentDetailMapper extends BaseMapper<IndentDetail> {
    List<IndentDetail> selectAllIndentDetail(Page page,@Param("indentId") String indentId);

    int updateIndentToCommodity(@Param("indentDetail") IndentDetail indentDetail);
}
