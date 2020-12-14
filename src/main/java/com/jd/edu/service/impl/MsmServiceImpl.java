package com.jd.edu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jd.edu.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(Map<String,Object> param, String phone) {
        //发送短信的方法
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                //这段是账号信息，不要白嫖！
        //创建对象发送短信
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST); //提交方法
        request.setDomain("dysmsapi.aliyuncs.com"); //提交到阿里云的哪个服务
        request.setVersion("2017-05-25"); //版本号
        request.setAction("SendSms"); //请求的哪个方法

        //key不允许修改
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "我的校园二手商品交易系统"); //签名名称
        request.putQueryParameter("TemplateCode", "SMS_204277468"); //模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //需要用json格式

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

