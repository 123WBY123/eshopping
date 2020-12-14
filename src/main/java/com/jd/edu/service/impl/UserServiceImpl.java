package com.jd.edu.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.User;
import com.jd.edu.entity.vo.RegisterVo;
import com.jd.edu.mapper.UserMapper;
import com.jd.edu.service.UserService;
import com.jd.edu.utils.exceptionhandler.EsException;
import com.jd.edu.utils.jwt.JwtUtils;
import com.jd.edu.utils.msm.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //密码登录方法
    @Override
    public String login(User user) {
        //获取登录手机号和密码
        String phone = user.getUserPhone();
        String password = user.getUserPassword();
        //判断手机号和密码是否为空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new EsException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",phone);
        User user1 = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        //把输入的密码进行加密,再和数据库密码进行比较
        //加密方式 MD5
        if(user1 == null) { //没有这个手机号
            throw new EsException(20001,"没有这个手机号");
        }

        //判断密码
        if(!MD5.encrypt(password).equals(user1.getUserPassword())){
            throw new EsException(20001,"密码不正确");
        }

        //判断用户是否被移除
        if(user1.getStatus() == 0){
            throw new EsException(20001,"登录失败");
        }

        //判断用户是否违规
        if(user1.getStatus() == 2){
            throw new EsException(20001,"用户违规 禁止登录");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String token = JwtUtils.getJwtToken(user1.getUserId(),user1.getUserNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获得注册数据
        String code = registerVo.getCode(); //验证码
        String phone = registerVo.getUserPhone(); //手机号
        String nickname = registerVo.getUserNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)){
            throw new EsException(20001,"注册失败");
        }

        //验证码是否正确
        String valiCode = (String)redisTemplate.opsForValue().get(phone);
        if(!code.equals(valiCode)){
            throw new EsException(20001,"验证码错误");
        }

        //用户电话是否被注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone",phone);
        if(baseMapper.selectOne(queryWrapper) != null){ //该电话已经被使用
            throw new EsException(20001,"该电话已经被注册");
        }

        //数据添加到数据库
        User user = new User();
        user.setUserPhone(phone);
        user.setUserNickname(nickname);
        user.setUserPassword(MD5.encrypt(password));
        user.setUserAvatar("https://ysc666.oss-cn-beijing.aliyuncs.com/sweet/45e8.jpg");//默认头像
        user.setStatus(1); //正常使用用户
        baseMapper.insert(user);
    }

    //微信号
    @Override
    public User getOpenIdUser(String userWechat) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_wechat",userWechat);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public boolean updateUserInfoById(User user) {
        int count = 0;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        count = baseMapper.selectCount(wrapper);
        if(count == 1){ //名字重复
            wrapper.eq("user_id",user.getUserId());
            count = baseMapper.selectCount(wrapper);
            if(count != 1){ //重名
                throw new EsException(20001,"名字重复,请重新选择提交");
            }
        }
        baseMapper.updateById(user);
        return true;
    }

    @Override
    public boolean removeByUserId(String userId) {
        User user = new User();
        user.setStatus(0);
        user.setUserId(userId);
        int result = baseMapper.updateById(user);
        return result == 1 ? true : false;
    }
    //查询全部 用户状态不为0
    @Override
    public List<User>getUser(String userId){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("status",0);
        List<User> users = baseMapper.selectList(wrapper);
        return users;
    }
}
