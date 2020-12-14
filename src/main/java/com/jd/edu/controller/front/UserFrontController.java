package com.jd.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jd.edu.entity.Cart;
import com.jd.edu.entity.User;
import com.jd.edu.entity.vo.RegisterVo;
import com.jd.edu.service.CartService;
import com.jd.edu.service.UserService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.jwt.JwtUtils;
import com.jd.edu.utils.msm.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/edu/front/user")
@CrossOrigin
public class UserFrontController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /*********************************************登录   注册**************************************************************/

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody User user){
        //user对象封装手机号和密码
        //调用service完成登录
        //返回token值,使用jwt完成
        String token = userService.login(user);
        return R.ok().data("token",token);
    }

    //注册
    //需要用到验证码确认注册 所以创建一个新类封装验证码和用户信息用于注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        //创建新购物车
        Cart cart = new Cart();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",registerVo.getUserPhone());
        cart.setUserId(userService.getOne(wrapper).getUserId());
        cartService.save(cart);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        //获取所有请求头名称
        /*Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            //根据名称获取请求头的值
            String value = request.getHeader(name);
            System.out.println(name+"---"+value);
        }
        System.out.println("=========      " + request.getHeader("token"));*/
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //调用数据库根据用户id获取用户信息
        User user = userService.getById(userId);
        return R.ok().data("userInfo",user);
    }

    /*********************************************前台用户信息修改**************************************************************/
    @PostMapping("updateUserInfo")
    public R updateUserInfo(@RequestBody User user, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        user.setUserId(userId);
        if(user.getUserPassword() != null){ //修改了用户密码
            user.setUserPassword(MD5.encrypt(user.getUserPassword()));
        }
        boolean result = userService.updateUserInfoById(user);
        return result ? R.ok() : R.error();
    }
}
