package cn.bo.project.util;

import cn.bo.project.config.AliYunOssConfig;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.*;
/**
 * @Author zhangbo
 * @Date 2020/7/28 22:29
 * @Description
 * @PackageName cn.bo.project.util
 **/
@Component
public class FileUploadUtil {

	@Autowired
	private AliYunOssConfig ossConfig;

	public String uploadFileToOss(File file, String key) {
		OSS client = new OSSClientBuilder().build(ossConfig.getOssEndpoint(), ossConfig.getOssAccessKey(),ossConfig.getOssAccessKeySecret());
		return key;
	}

    public void deleteOssFile(String key) {
		OSS client = new OSSClientBuilder().build(ossConfig.getOssEndpoint(), ossConfig.getOssAccessKey(),ossConfig.getOssAccessKeySecret());
		client.deleteObject(ossConfig.getOssBucketName(),key);
    }
}
