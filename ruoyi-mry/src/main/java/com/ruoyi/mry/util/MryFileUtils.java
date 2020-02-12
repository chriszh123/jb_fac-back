/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/17
 * Description: 文件操作类
 */
package com.ruoyi.mry.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * 文件操作类
 *
 * @author zhangguifeng
 * @create 2019-01-17 15:45
 **/
public class MryFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(MryFileUtils.class);

    private static MryFileUtils instance = null;
    private static final Object obj = new Object();

    public static MryFileUtils getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new MryFileUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取文件名称
     *
     * @param fileUrl 网络资源文件 地址
     * @return 名称
     */
    public String getFileName(String fileUrl) {
        String fileName = "";
        if (org.apache.commons.lang3.StringUtils.isBlank(fileUrl) || fileUrl.indexOf("/") < 0 || fileUrl.length() <= 1) {
            return fileName;
        }
        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     * 获取网络资源文件大小
     *
     * @param fileUrl 网络资源文件 地址
     * @return 文件大小
     */
    public String getFileSize(String fileUrl) {
        String fileSize = "";
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlFile = (HttpURLConnection) url.openConnection();
            //根据响应获取文件大小
            int fileLength = urlFile.getContentLength();
            if (urlFile.getResponseCode() >= 400) {
                logger.info("服务器响应错误,fileUrl:{}", fileUrl);
                return fileSize;
            }
            if (fileLength <= 0) {
                logger.info("无法获知文件大小,fileUrl:{}", fileUrl);
                return fileSize;
            }
            DecimalFormat df = new DecimalFormat("######0");
            Double size = Double.parseDouble(String.valueOf(fileLength));
            fileSize = df.format(size);
        } catch (Exception ex) {
            logger.info("获知文件大小操作异常,fileUrl:{}", fileUrl, ex);
        }

        return fileSize;
    }
}
