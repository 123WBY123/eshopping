package com.jd.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.KindBase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ysc666
 * @since 2020-10-31
 */
@Repository
public interface KindBaseMapper extends BaseMapper<KindBase> {

    @Select("select * from kind_base where is_deleted = 1")
    List<KindBase> showKindDelete(Page page);

    @Update("update kind_base set is_deleted = 0 where kind_id = #{id}")
    int updateKindToUse(@Param("id") String id);

    @Select("select * from kind_base where kind_name = #{kindName} and is_deleted = 1")
    List<KindBase> showAllDelete(@Param("kindName") String kindName);

    @Select("select * from kind_base where kind_name = #{kindName} and is_deleted = 0")
    List<KindBase> showAllUse(@Param("kindName") String kindName);

    @Select("select * from kind_base where kind_id = #{id}")
    KindBase getOneDelete(@Param("id")String id);

    @Update("update kind_base set kind_name = #{kindBase.kindName} where kind_id = #{kindBase.kindId}")
    int updateDeleteKind(@Param("kindBase") KindBase kindBase);
}
