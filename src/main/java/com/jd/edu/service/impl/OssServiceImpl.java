package com.jd.edu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.jd.edu.service.OssService;
import com.jd.edu.utils.oss.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        //工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = null;
        StringBuffer url = new StringBuffer();
        try {
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //随机id 否则相同名称文件在上传时 会被覆盖掉
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            //2.把文件按照日期进行分类
            //2019/11/12/01.jpg
            //获取当前日期 利用joda-time
            String datepath = new DateTime().toString("yyyy/MM/dd/");
            fileName = datepath + uuid + fileName;
            //第一个参数 bucket名称
            //第二个参数 上传到oss文件路径和文件名称 /aa/bb/1.jpg
            putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream());

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接
            //https://ysc666.oss-cn-beijing.aliyuncs.com/微信图片_20190130223138.jpg
            url.append("https://").append(bucketName).append(".").append(endpoint).append("/").append(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return url.toString();
    }
}
