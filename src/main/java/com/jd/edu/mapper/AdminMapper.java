package com.jd.edu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
 * @since 2020-10-27
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select * from admin where is_deleted = 1")
    List<Admin> selectAdminDelete(Page<Admin> pageParam);

    @Update("update admin set is_deleted = 0 where admin_id = #{id}")
    int updateAdminToUse(@Param("id") String id);

    @Select("select * from admin where is_deleted = 1 and admin_username = #{username}")
    Admin isDelete(@Param("username") String username);
}
