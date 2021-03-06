/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/17
 * Description: ckEditor组件图片上传
 */
package com.ruoyi.framework.util;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ckEditor组件图片上传
 *
 * @author zhangguifeng
 * @create 2019-01-17 15:49
 **/
public class CkImageUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(CkImageUploadUtil.class);

    private static CkImageUploadUtil instance = null;
    private static final Object obj = new Object();
    /**
     * 图片类型
     */
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    public static CkImageUploadUtil getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new CkImageUploadUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 图片上传
     */
    public String upload(HttpServletRequest request) throws Exception {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 图片名称
        String fileName = null;
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为"",说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 获得图片的原始名称
                        String originalFilename = file.getOriginalFilename();
                        // 获得图片后缀名称,如果后缀不为图片格式，则不上传
                        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                        if (!fileTypes.contains(suffix)) {
                            continue;
                        }
                        // 获得上传路径的绝对路径地址(本地测试上传)
                        String realPath = Global.getProductPath();
                        // 如果路径不存在，则创建该路径
                        File realPathDirectory = new File(realPath);
                        if (realPathDirectory == null || !realPathDirectory.exists()) {
                            realPathDirectory.mkdirs();
                        }
                        fileName = FileUploadUtils.encodingFilename(originalFilename) + suffix;
                        File uploadFile = new File(realPathDirectory + fileName);
                        System.out.println(uploadFile);
                        file.transferTo(uploadFile);
                        // 腾讯云上传图片
//                      fileName = COSClientUtils.getInstance().uploadFile2Cos(file);
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                System.out.println("[CkImageUploadUtil] upload cost : " + (finaltime - pre));
            }
        }
        return fileName;
    }

    public String uploadFile(MultipartFile file, String imagesUploadPath, String imageReturnPrePath, String domain) throws Exception {
        String result = "";
        FileBean fileBean = new FileBean();
//        // 腾讯云上传图片
//        String fileName = COSClientUtils.getInstance().uploadFile2Cos(file);
//        String imagePath = COSClientUtils.getInstance().getImgUrl(fileName);
//        result = fileBean.success(1, file.getOriginalFilename(), imagePath, null);

        String fileName = FileUploadUtils.upload(file, imagesUploadPath);
        // 存储在数据库中的图片完整路径
        String imagePath = domain + imageReturnPrePath + fileName;
        result = fileBean.success(1, file.getOriginalFilename(), imagePath, null);

        return result;
    }
}
