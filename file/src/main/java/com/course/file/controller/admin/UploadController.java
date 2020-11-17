package com.course.file.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.course.server.domain.Test;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import com.course.server.util.VodUtil;
import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

@RestController
@RequestMapping("/admin")
//返回json数据用RestController
//返回页面用Controller
public class UploadController {

    private static  final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    public  static final String BUSINESS_NAME ="文件上传";

    //@Value 注入属性
    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Value("${file.path}")
    private String FILE_PATH;
    @Value("${oss.domain}")
    private String OSS_DOMAIN;
    @Resource
    private FileService fileService;
    @Value("${vod.accessKeyId}")
    private String accessKeyId;
    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;


    @RequestMapping("/upload_old")
        public ResponseDto upload_old(@RequestParam MultipartFile file,String use) throws IOException {

            LOG.info("文件上传开始:{}",file);
            LOG.info(file.getOriginalFilename());
            LOG.info(String.valueOf(file.getSize()));

            //保存文件到本地
            FileUseEnum useEnum = FileUseEnum.getByCode(use);
            String key = UuidUtil.getShortUuid();//生成ID
            String fileName = file.getOriginalFilename();//保存文件名字
            //去最后一个点后面的文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();

            //如果文件加不存在则创建
            //COURSE("C", "课程"),
            //TEACHER("T", "讲师");
            String dir = useEnum.name().toLowerCase();//获取枚举的TEACHER，转成小写用于目录
            File fullDir = new File(FILE_PATH + dir);//绝对路径+目录名字
            if(!fullDir.exists()){//如果路径不存在
                fullDir.mkdir();//就创建
            }

            //File.separator会根据操作系统的不同生成不同的斜杠'\,/'
            String path = dir + File.separator + key +  "." +  suffix;

            String fullPath = FILE_PATH+path;//定义本地的路径
            File  dest = new File(fullPath);//生成一个file目标位置
            file.transferTo(dest);//将传递的参数存放到目标位置
            LOG.info(dest.getAbsolutePath());//打印目标位置全路径

            LOG.info("文件保存记录开始");
            FileDto fileDto = new FileDto();
            //记录文件路径
            fileDto.setPath(path);
            //记录文件名字
            fileDto.setName(fileName);
            //记录文件大小，转化成Int类型
            fileDto.setSize(Math.toIntExact(file.getSize()));
            //记录文件后缀
            fileDto.setSuffix(suffix);
            //记录use
            fileDto.setUse(use);
            fileService.save(fileDto);

            ResponseDto responseDto = new ResponseDto();
            //最后在设置一次路径，内容为返回给前端的文件全路径
            fileDto.setPath(FILE_DOMAIN  + path);
            responseDto.setContent(fileDto);

            return responseDto;

    }


    @RequestMapping( "/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {

        LOG.info("文件上传开始:{}");
        String use = fileDto.   getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        //将字符串转化为MultipartFile类型
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);


        //保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件加不存在则创建
        //COURSE("C", "课程"),
        //TEACHER("T", "讲师");
        String dir = useEnum.name().toLowerCase();//获取枚举的TEACHER，转成小写用于目录
        File fullDir = new File(FILE_PATH + dir);//绝对路径+目录名字
        if(!fullDir.exists()){//如果路径不存在
            fullDir.mkdir();//就创建
        }

        //File.separator会根据操作系统的不同生成不同的斜杠'\,/'
        //String path = dir + File.separator + key +  "." +  suffix +"." + fileDto.getShardIndex();
        //返回给前端的不加索引号
        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix)
                .toString();// course\6sfSqfOwzmik4A4icMYuUe.mp4

        //本地文件带索引号
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1

        String fullPath = FILE_PATH+localPath;//定义本地的路径
        File  dest = new File(fullPath);//生成一个file目标位置
        shard.transferTo(dest);//将传递的参数存放到目标位置
        LOG.info(dest.getAbsolutePath());//打印目标位置全路径

        LOG.info("文件保存记录开始");
//        FileDto fileDto = new FileDto();
        //记录文件路径到数据库是不加索引号的path
        fileDto.setPath(path);
        fileService.save(fileDto);
        ResponseDto responseDto = new ResponseDto();
        //最后在设置一次路径，内容为返回给前端的文件全路径
        //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        System.out.print(fileDto.getPath());

        if(fileDto.getShardIndex().equals(fileDto.getShardTotal()) ){
            this.merge(fileDto);
        }

        return responseDto;

    }

    /**
     * 大文件合并方法
     * @return
     * @throws Exception
     */
    public void  merge(FileDto fileDto) throws Exception {
        LOG.info("开始合并分片");
        //获取数据库中不加索引号的全路径：访问地址+路径+文件名+后缀名
        String path = fileDto.getPath();
        //将访问地址FILE_DOMAIN截取掉
        //FILE_DOMAIN:配置文件中的全局访问路径（http://localhost:9000/file/f/）
        path  = path.replace(FILE_DOMAIN,"");
        //获取视频的分片总数
        Integer shardTotal = fileDto.getShardTotal();
        //new一个文件
        File newFile = new File(FILE_PATH+path);
        //文件追加写入
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        //分片文件
        FileInputStream fileInputStream = null;
        //定一个byt 数组长度
        byte[] byt = new byte[10*1024*1024];
        //长度len
        int len;
        try {

            for (int i = 0; i < shardTotal; i++) {
                //读取第i个分片,应为i的分片是从1开始的，所以+1
                fileInputStream = new FileInputStream(new File(FILE_PATH+path + '.' +(i+1)));
                //每次读取一个字节byt读取的长度等于len，但是不等于-1，表示读取成功
                while ((len = fileInputStream.read(byt))!=-1){
                    //开始写入流文件(单位byt，从0开始，到len的长度)
                    outputStream.write(byt,0,len);
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error("分片合并异常",e);
        } finally{
            try {
                //如果读取结果不等于null，就关闭IO流
                if(fileInputStream !=null){
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (IOException e) {
                LOG.error("IO流关闭",e);
            }
        }
        LOG.info("合并分片结束");
        System.gc();//调用垃圾回收机制，删除掉堆里的占用
        //线程停止100毫秒，防止gc本身有需要清理的垃圾，给gc清理时间，以防止清空不干净
        Thread.sleep(100);
        //删除分片(避免磁盘空间占用)
        LOG.info("删除分片开始");
        for (int i = 0; i <shardTotal ; i++) {
            String filePath = FILE_PATH + path +"." +(1+i);
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("删除{}，{}",filePath,result ? "成功":"失败");
        }
        LOG.info("删除分片结束");
    }

    /**
     *检查上传分片
     * @return
     */
    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) throws Exception {
        LOG.info("检查上传分片开始:{}",key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        //文件检查的时候根据是否是视频点播文件来获取视频信息
        if(fileDto!=null){
            //fileDto.setPath(FILE_DOMAIN+fileDto.getPath());
            if (StringUtils.isEmpty(fileDto.getVod())) {
                fileDto.setPath(OSS_DOMAIN + fileDto.getPath());
            } else {
                DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
                GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, fileDto.getVod());
                System.out.println("获取视频信息, response : " + JSON.toJSONString(response));
                String fileUrl = response.getMezzanine().getFileURL();
                fileDto.setPath(fileUrl);
            }
        }
        responseDto.setContent(fileDto);
        return responseDto;

    }
}
