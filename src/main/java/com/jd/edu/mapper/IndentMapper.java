package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Indent;
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
public interface IndentMapper extends BaseMapper<Indent> {

    List<Indent> selectAllIndent(Page page, @Param("status")Integer status);

    Indent getByIndentId(@Param("indentId") String indentId);

    Indent getByUserId(@Param("userId") String userId);

    Indent getIndentTotal(@Param("indentId") String indentId);

    int updateIndentById(@Param("indent") Indent indent);

    List<Indent> getAllByUserId(Page<Indent> pageIndent, @Param("userId") String userId);
}
