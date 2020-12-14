package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.User;
import com.jd.edu.entity.vo.RegisterVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
public interface UserService extends IService<User> {
    boolean removeByUserId(String userId);
    List<User> getUser(String userId);

    //登录 注册方法
    String login(User user);

    void register(RegisterVo registerVo);

    User getOpenIdUser(String openid);

    boolean updateUserInfoById(User user);
}
