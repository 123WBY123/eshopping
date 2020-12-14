package com.jd.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Admin;
import com.jd.edu.service.AdminService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.exceptionhandler.EsException;
import com.jd.edu.utils.msm.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@Api(value = "管理员类")
@RestController
@RequestMapping("/edu/back/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    //登录操作
    @PostMapping("/login")
    public R login(@RequestParam String username,@RequestParam String password) {
        Admin admin = new Admin();
        //判断名字是否正确或禁用
        boolean result = adminService.isDelete(username);
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_username",username);
        admin = adminService.getOne(wrapper);
        if(admin == null){
            throw new EsException(20001,"该用户名不存在");
        }
        //判断密码是否正确
        wrapper.eq("admin_password",MD5.encrypt(password));
        admin = adminService.getOne(wrapper);
        if(admin == null){
            throw new EsException(20001,"密码不正确");
        }
        return R.ok().data("token", admin);
    }

    //info
    @GetMapping("info")
    public R info() {
        //roles需要返回一个非空数组
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

    //查询管理员信息
    @ApiOperation("查询管理员信息")
    @GetMapping("pageAdmin/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        /**
         * page -> current
         * limit -> size
         */
        Page<Admin> pageParam = new Page<>(page, limit);
        adminService.page(pageParam,null);
        List<Admin> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    //查询单个管理员信息
    @ApiOperation("单个管理员信息")
    @GetMapping("getAdmin/{id}")
    public R getAdmin(@PathVariable String id){
        Admin admin = adminService.getById(id);
        return R.ok().data("admin",admin);
    }

    //增添管理员
    @ApiOperation("增添管理员")
    @PostMapping("addAdmin")
    public R addAdmin(@RequestBody Admin admin){
        String password = MD5.encrypt(admin.getAdminPassword());
        admin.setAdminPassword(password);
        int result = adminService.saveAdmin(admin);
        return result == 1 ? R.ok() : R.error();
    }

    //修改管理员
    @ApiOperation("修改管理员信息")
    @PostMapping("updateAdmin")
    public R updateAdmin(@RequestBody Admin admin){
        if(admin.getIsDeleted() != null && admin.getIsDeleted()){
            boolean remove = adminService.removeById(admin.getAdminId());
            return remove ? R.ok() : R.error();
        }
        if(admin.getAdminPassword() != null){ //修改密码
            String password = MD5.encrypt(admin.getAdminPassword());
            admin.setAdminPassword(password);
        }
        int result = adminService.updateAdminById(admin);
        return result == 1 ? R.ok() : R.error();
    }

    //删除管理员信息
    @ApiOperation("删除管理员信息")
    @DeleteMapping("deleteAdmin/{id}")
    public R deleteAdmin(@PathVariable String id, HttpServletRequest request){
        String headId = request.getHeader("adminId");
        System.out.println(headId);
        if(headId.equals(id)){
            throw new EsException(20001,"你难道要禁用你自己嘛");
        }
        boolean result = adminService.removeById(id);
        return result ? R.ok() : R.error();
    }

    //查看禁用管理员
    @ApiOperation("查看禁用管理员")
    @GetMapping("pageAdminDelete/{page}/{limit}")
    public R pageAdminDelete(@ApiParam(name = "page",value = "当前页码",required = true)
                             @PathVariable Long page,

                             @ApiParam(name = "limit", value = "每页记录数", required = true)
                             @PathVariable Long limit){
        Page<Admin> pageParam = new Page<>();
        List<Admin> list = adminService.selectAdminDelete(pageParam);
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",list);
    }

    //恢复管理员使用
    @ApiOperation("恢复管理员使用")
    @GetMapping("updateAdminToUse/{id}")
    public R updateAdminToUse(@PathVariable String id){
        Integer result = adminService.updateAdminToUse(id);
        return result == 1 ? R.ok() : R.error();
    }
}

