/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/17
 * Description: 腾讯云文件操作工具类
 */
package com.ruoyi.framework.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.fac.constant.FacConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * 腾讯云文件操作工具类
 *
 * @author zhangguifeng
 * @create 2019-01-17 14:33
 **/
public class COSClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(COSClientUtils.class);

    private static COSClientUtils instance = null;
    private static final Object obj = new Object();

    /**
     * 存储桶名称，替换成自己的
     */
    private static final String bucketName = "xxxxxxxxxxxxxxxxxxxxxxxxx";
    /**
     * secretId ，替换成自己的
     */
    private static final String secretId = "xxxxxxxxxxxxxxxxxxxxxxxxx";
    /**
     * secretKey，替换成自己的
     */
    private static final String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxx";
    /**
     * 1 初始化用户身份信息(secretId, secretKey)
     */
    private static final COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    /**
     * 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
     */
    private static final ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
    /**
     * 3 生成cos客户端
     */
    private static final COSClient cosClient = new COSClient(cred, clientConfig);
    /**
     * 文件存储目录
     */
    private COSClient cOSClient;

    /**
     * 图片存放在cos上的文件夹目录
     */
    private static final String DIR_PICTURE = "fac/test/";

    public COSClientUtils() {
        cOSClient = new COSClient(cred, clientConfig);
    }

    public static COSClientUtils getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new COSClientUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 销毁
     */
    public void destory() {
        cOSClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Cos(String url) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2Cos(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }

    public String uploadFile2Cos(MultipartFile file) throws Exception {
        if (file.getSize() > FacConstant.FILE_SIZE_FAC) {
            throw new Exception("上传图片大小不能超过500K！");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            String fileName = FileUploadUtils.encodingFilename(originalFilename) + extension;
            InputStream inputStream = file.getInputStream();
            this.uploadFile2Cos(inputStream, fileName);
            logger.info("[uploadFile2Cos] success, fileName = " + fileName);
            return fileName;
        } catch (Exception e) {
            logger.error("[uploadFile2Cos] error", e);
            throw new Exception("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileName cos上唯一的名称
     * @return 文件对应的url
     */
    public String getImgUrl(String fileName) {
        return getUrl(fileName);
    }

    /**
     * 获得url链接
     *
     * @param fileName cos上唯一的名称
     * @return 文件对应的访问地址url
     */
    public String getUrl(String fileName) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        String key = DIR_PICTURE + fileName;
        URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            String imgUrl = url.toString();
            String[] imgUrlSplit = imgUrl.split("\\?");
            return imgUrlSplit[0];
        }
        return null;
    }

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2Cos(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            String key = DIR_PICTURE + fileName;
            PutObjectResult putResult = cOSClient.putObject(bucketName, key, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error("[uploadFile2Cos] IOException", e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                logger.error("[uploadFile2Cos] instream close:IOException", e);
            }
        }
        return ret;
    }

    /**
     * 删除
     */
    public static void delete(String fileName) {
        new Thread(new Runnable() {
            public void run() {
                // 指定要删除的 bucket 和路径
                try {
                    String key = DIR_PICTURE + fileName;
                    cosClient.deleteObject(bucketName, key);
                    logger.info("[delete] success, fileName = " + fileName);
                } catch (Exception ex) {
                    logger.error("[delete] failure, fileName = " + fileName, ex);
                }
            }
        }).start();
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
