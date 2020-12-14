package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Admin;
import com.jd.edu.mapper.AdminMapper;
import com.jd.edu.service.AdminService;
import com.jd.edu.utils.exceptionhandler.EsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    //判断是否信息输入正确
    @Override
    public int login(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_username",username);
        wrapper.eq("admin_password",password);
        int result = baseMapper.selectCount(wrapper);
        return result;
    }

    //判断是否重复 不重复添加 重复不添加
    @Override
    public int saveAdmin(Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_username",admin.getAdminUsername().trim());
        //用户名重复了
        if(baseMapper.selectCount(wrapper) != 0){
            throw new EsException(20001,"用户名重复");
        }
        QueryWrapper<Admin> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("admin_phone",admin.getAdminPassword());
        if(baseMapper.selectCount(wrapper1) != 0){
            throw new EsException(20001,"注册电话重复");
        }
        int result = baseMapper.insert(admin);
        return result;
    }

    //更新管理员信息
    @Override
    public int updateAdminById(Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.ne("admin_username",admin.getAdminUsername());
        wrapper.eq("admin_phone",admin.getAdminPhone());
        if(baseMapper.selectCount(wrapper) != 0){
            throw new EsException(20001,"修改电话重复");
        }
        int result = baseMapper.updateById(admin);
        return result;
    }

    //查看禁用管理员
    @Override
    public List<Admin> selectAdminDelete(Page<Admin> pageParam) {
        List<Admin> list = adminMapper.selectAdminDelete(pageParam);
        return list;
    }

    @Override
    public Integer updateAdminToUse(String id) {
        int result = adminMapper.updateAdminToUse(id);
        return result;
    }

    @Override
    public boolean isDelete(String username) {
        Admin admin = adminMapper.isDelete(username);
        if(admin != null) { //已被禁用
            throw new EsException(20001,"管理员" + username + " ,您被禁用了,请与老大联系");
        }
        return true;
    }
}
