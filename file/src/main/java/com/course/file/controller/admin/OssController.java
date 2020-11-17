package com.course.file.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import com.google.gson.JsonObject;
import com.mysql.fabric.ShardIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;


@RequestMapping("/admin")
@RestController
public class OssController {

    private static  final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    public  static final String BUSINESS_NAME ="文件上传";

    //@Value 注入属性
    //AccessKey ID用于标识用户。
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    //AccessKey Secret是用来验证用户的密钥。AccessKey Secret必须保密。
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    //Endpoint:地域节点（控制台的预览里面有）
    @Value("${oss.endpoint}")
    private String endpoint;
    //Bucket 域名访问地址
    @Value("${oss.Domain}")
    private String ossDomain;
    //bucket：oss的工作空间
    @Value("${oss.bucket}")
    private String bucket;


    @Resource
    private FileService fileService;

    /**
     * 阿里云OSS 上传
     * @param fileDto
     * @return
     * @throws Exception
     */
    @PostMapping("/oss-append")
    public ResponseDto fileUpload(@RequestBody FileDto fileDto) throws Exception {

        LOG.info("文件上传开始:{}");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        Integer shardIndex = fileDto.getShardIndex();
        Integer shardSize = fileDto.getShardSize();
        String shardBase64 = fileDto.getShard();
        //将字符串转化为MultipartFile类型
        //MultipartFile是spring类型，代表HTML中form data方式上传的文件，包含二进制数据+文件名称。
        //MultipartFile  这个类一般是用来接受前台传过来的文件
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);


        //保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件加不存在则创建
        //COURSE("C", "课程"),
        //TEACHER("T", "讲师");
        String dir = useEnum.name().toLowerCase();//获取枚举的TEACHER，转成小写用于目录
//        File fullDir = new File(FILE_PATH + dir);//绝对路径+目录名字
//        if(!fullDir.exists()){//如果路径不存在
//            fullDir.mkdir();//就创建
//        }

        //File.separator会根据操作系统的不同生成不同的斜杠'\,/'
        //String path = dir + File.separator + key +  "." +  suffix +"." + fileDto.getShardIndex();
        //返回给前端的不加索引号
        String path = new StringBuffer(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();// course\6sfSqfOwzmik4A4icMYuUe.mp4

        //本地文件带索引号
//        String localPath = new StringBuffer(path)
//                .append(".")
//                .append(fileDto.getShardIndex())
//                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1

//        String fullPath = FILE_PATH+localPath;//定义本地的路径
//        File  dest = new File(fullPath);//生成一个file目标位置
//        shard.transferTo(dest);//将传递的参数存放到目标位置
//        LOG.info(dest.getAbsolutePath());//打印目标位置全路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 在HTTP header中设置标签信息。
        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");

        // 通过AppendObjectRequest设置多个参数。
        AppendObjectRequest appendObjectRequest = new
                AppendObjectRequest(bucket, path, new
                ByteArrayInputStream(shard.getBytes()),meta);

        /**
         * 官网给出的注释
         * 地址:https://help.aliyun.com/document_detail/84784.html?spm=a2c4g.11186623.6.831.74dc28baby2Ez5
         */
        // 通过AppendObjectRequest设置单个参数。
        // 设置存储空间名称。
        //appendObjectRequest.setBucketName("<yourBucketName>");
        // 设置文件名称。
        //appendObjectRequest.setKey("<yourObjectName>");
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为InputStream类型。
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content1.getBytes()));
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为File类型。
        //appendObjectRequest.setFile(new File("<yourLocalFile>"));
        // 指定文件的元信息，第一次追加时有效。
        //appendObjectRequest.setMetadata(meta);


        // 第一次追加。
        // 设置文件的追加位置。
//        appendObjectRequest.setPosition(0L);
        appendObjectRequest.setPosition(((long)(shardIndex -1) * shardSize));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        // 文件的64位CRC值。此值根据ECMA-182标准计算得出。
        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

        // 第二次追加。
// nextPosition指明下一次请求中应当提供的Position，即文件当前的长度。
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(content2.getBytes()));
//        appendObjectResult = ossClient.appendObject(appendObjectRequest);

        // 第三次追加。
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(content3.getBytes()));
//        appendObjectResult = ossClient.appendObject(appendObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();
        LOG.info("文件保存记录开始");
        //记录文件路径到数据库是不加索引号的path
        fileDto.setPath(path);
        fileService.save(fileDto);
        ResponseDto responseDto = new ResponseDto();
        //最后在设置一次路径，内容为返回给前端的文件全路径
        //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
        fileDto.setPath(ossDomain + path);
        responseDto.setContent(fileDto);
//        if(fileDto.getShardIndex().equals(fileDto.getShardTotal()) ){
//            this.merge(fileDto);
//        }

        return responseDto;

    }

    /**
     * 急速秒传检查上传分片
     * @return
     */
    @GetMapping("/check-oss/{key}")
    public ResponseDto check(@PathVariable String key){
        LOG.info("检查上传分片开始:{}",key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        if(fileDto!=null){
            fileDto.setPath(ossDomain+fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;

    }


    /**
     * 简易上传
     * @param file
     * @param use
     * @return
     * @throws Exception
     */
    @PostMapping("/oss-simple")
    public ResponseDto fileUpload(@RequestParam MultipartFile file, String use) throws Exception {
        LOG.info("上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
//        String content = "Hello OSS";
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

//        LOG.info("保存文件记录开始");
//        fileDto.setPath(path);
//        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = new FileDto();
        fileDto.setPath(ossDomain + path);
        responseDto.setContent(fileDto);

        return responseDto;
    }

}
