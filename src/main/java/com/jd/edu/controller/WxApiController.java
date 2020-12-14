package com.jd.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.jd.edu.entity.Cart;
import com.jd.edu.entity.User;
import com.jd.edu.service.CartService;
import com.jd.edu.service.UserService;
import com.jd.edu.utils.jwt.JwtUtils;
import com.jd.edu.utils.wx.ConstantWxUtils;
import com.jd.edu.utils.wx.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

//要返回页面 而不是数据
@Controller
@CrossOrigin
@RequestMapping("api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    //1.生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode(){
        // 微信开放平台授权baseUrl %s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URL编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_REDIRECT_URL,
                "Ysc666"
        );
        //重定向请求微信地址
        return "redirect:" + url;
    }

    /**
     * 2.获取扫描人信息,添加数据
     * @param code 类似于手机验证码,随机唯一的值
     * @param state  原样传递
     * @return
     */
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            //1.获取code值 临时票据 类似于验证码

            //2.拿着code请求微信固定的地址,得到两个值access_token和openid
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 id 秘钥 code
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，得到返回两个值access_token和openid
            //使用httpclient发送请求，得到返回结果
            //httpclient可以不使用浏览器 而模拟浏览器发送的效果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo字符串获取出来两个值 access_token和openid
            //把accessTokenInfo字符串转换map集合，根据map中的key获取对应值
            //使用json转换工具gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");


            //把扫码人的信息直接添加到数据库中
            //判断数据表里面是否存在相同微信信息，根据openid判断
            User user = userService.getOpenIdUser(openid);
            if(user == null){
                //表里面没有数据 进行添加
                //3.拿着得到access_token 和 open_id,再去请求微信提供固定的地址,获取到扫描人信息
                //访问微信的资源服务器,获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                //昵称 头像
                String nickname = (String)userInfoMap.get("nickname");
                String headimgurl = (String)userInfoMap.get("headimgurl");
                user = new User();
                user.setUserWechat(openid); //存微信
                user.setUserNickname(nickname);    //存昵称
                user.setUserAvatar(headimgurl);  //存头像
                userService.save(user); //存user对象到数据库

                //新建一个专属用户的cart
                Cart cart = new Cart();
                //从用户拿id
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("user_wechat",user.getUserWechat());
                cart.setUserId(userService.getOne(wrapper).getUserId());
                //创建新购物车
                cartService.save(cart);
            }
            //使用jwt根据member对象生成token字符串
            //没有使用cookie是因为cookie无法实现跨域
            String jwtToken = JwtUtils.getJwtToken(user.getUserId(), user.getUserNickname());
            //返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:http://localhost:3000";
    }
}

