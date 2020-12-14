package com.jd.edu.controller;

import com.alibaba.excel.util.StringUtils;
import com.jd.edu.service.MsmService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.msm.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//短信发送
@RestController
@RequestMapping("edu/msm")
@CrossOrigin(allowedHeaders = "*")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //从redis获取验证码,如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //如果redis获取不到,进行阿里云发送
        //生成随机值,用阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        System.out.println(code);
        //发送短信
        boolean isSend = msmService.send(param,phone);
        if(isSend) {
            //发送成功,把发送成功验证码放到redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,1, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
}