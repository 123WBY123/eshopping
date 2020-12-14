package com.jd.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.User;
import com.jd.edu.entity.vo.UserQuery;
import com.jd.edu.service.CartService;
import com.jd.edu.service.UserService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.msm.MD5;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edu/back/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "逻辑删除用户")
    @GetMapping("removeDataById/{userId}")
    public R removeDataById(@PathVariable String userId) {
        boolean flag = userService.removeByUserId(userId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    @GetMapping("pageUser/{current}/{limit}")
    public R pageListUser(@PathVariable long current,
                          @PathVariable long limit) {
        Page<User> pageUser = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("status",0);
        userService.page(pageUser, wrapper);
        long total = pageUser.getTotal();
        List<User> records = pageUser.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }



    @PostMapping("pageUserCondition/{current}/{limit}")
    public R pageUserCondition(@PathVariable long current,@PathVariable long limit,
                               @RequestBody (required = false) UserQuery userQuery) {
        Page<User> pageUser = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("status",0);
        String userNickname = userQuery.getUserNickname();
        Integer userSex = userQuery.getUserSex();
        if (!StringUtils.isEmpty(userNickname)) {
            wrapper.like("user_nickname", userNickname);
        }
        if (!StringUtils.isEmpty(userSex)) {
            wrapper.eq("user_sex", userSex);
        }
//        wrapper.orderByDesc("gmt_create");
        userService.page(pageUser, wrapper);
        long total = pageUser.getTotal();
        List<User> records = pageUser.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }


    @GetMapping("getUser/{userId}")
    public R getUser(@PathVariable String userId) {
        User user = userService.getById(userId);
        return R.ok().data("user",user);
    }


    @PostMapping("updateUser")
    public R updateUser(@RequestBody User user) {
        //需要重置用户密码
        if(user.getUserPassword() != null){
            //MD5加密用户密码
            String password = MD5.encrypt(user.getUserPassword());
            user.setUserPassword(password);
        }
        boolean flag = userService.updateById(user);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //获得全部用户数据
    @ApiOperation(value = "获得全部用户数据")
    @GetMapping("getAll")
    public R getAll(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("status",0);
        List<User> list = userService.list(wrapper);
        return R.ok().data("userList",list);
    }
}

