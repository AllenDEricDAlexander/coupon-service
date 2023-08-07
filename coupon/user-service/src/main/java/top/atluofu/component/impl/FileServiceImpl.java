package top.atluofu.component.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.atluofu.component.FileService;
import top.atluofu.config.OSSConfig;
import top.atluofu.utils.CommonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: FileServiceImpl
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-19:18
 * @Version: 1.0
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {


    private final OSSConfig ossConfig;

    public FileServiceImpl(OSSConfig ossConfig) {
        this.ossConfig = ossConfig;
    }


    @Override
    public String uploadUserHeadImg( MultipartFile file) {

        String originalFilename = file.getOriginalFilename();

        //获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        //创建oss对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //JDK8新特性写法，构建路径
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folder = dtf.format(ldt);
        String fileName = CommonUtil.generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //在oss上创建文件夹test路径
        String newFileName = "test/" + folder + "/" + fileName + extension;
        try {
            PutObjectResult result = ossClient.putObject(bucketName, newFileName, file.getInputStream());
            //返回访问路径
            if (null != result) {
                //https://xd-test1.oss-cn-beijing.aliyuncs.com/test/1.jpg
                String imgUrl = "https://"+bucketName+"."+endpoint+"/"+newFileName;
                return imgUrl;
            }
        } catch (Exception e) {
            log.error("上传头像失败:{}",e);
        } finally {
            // 关闭OSS服务
            ossClient.shutdown();
        }

        return null;
    }
}