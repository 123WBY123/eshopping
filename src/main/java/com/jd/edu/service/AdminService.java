package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.Admin;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
public interface AdminService extends IService<Admin> {

    int login(String username, String password);

    int saveAdmin(Admin admin);

    int updateAdminById(Admin admin);

    List<Admin> selectAdminDelete(Page<Admin> pageParam);

    Integer updateAdminToUse(String id);

    boolean isDelete(String username);
}
