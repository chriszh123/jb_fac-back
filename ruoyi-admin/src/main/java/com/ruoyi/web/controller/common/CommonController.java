package com.ruoyi.web.controller.common;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.vo.FileVo;
import com.ruoyi.fac.vo.ProductImgVo;
import com.ruoyi.framework.util.COSClientUtils;
import com.ruoyi.framework.util.CkImageUploadUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format(" 文件名称({})非法，不允许下载。 ", fileName));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String prefixFileName = fileName.substring(0, fileName.lastIndexOf("."));
            String fileStufix = fileName.substring(fileName.lastIndexOf("."));
            String realFileName = prefixFileName + "_" + sdf.format(new Date()) + fileStufix;

            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    @RequestMapping("/image/uploadImg")
    public void uplodaImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        FileVo fileVo = new FileVo();
        try {
            PrintWriter out = response.getWriter();
            log.info("[uplodaImg] fileSize: " + file.getSize());
            // 图片大小不超过500K
            if (file.getSize() > FacConstant.FILE_SIZE_FAC) {
                String error = fileVo.error(0, "图片大小超过500K");
                out.println(error);
                return;
            }

            String success = CkImageUploadUtil.getInstance().uploadFile(file);
            out.println(success);
        } catch (Exception e) {
            log.error("[uplodaImg] error", e);
            String error = fileVo.error(0, "系统异常");
            try {
                response.getWriter().println(error);
            } catch (Exception e1) {
                log.error("[uplodaImg] error1", e1);
            }
        }
    }

    @RequestMapping("/product/picture/batchUpload")
    @ResponseBody
    public ProductImgVo batchUploadProductImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception {
        ProductImgVo vo = new ProductImgVo();
        vo.setCode(FacConstant.AJAX_CODE_FAIL);
        System.out.println("batchUploadProductImg........");
        if (file != null && file.length > 0) {
            List<String> fileNames = new ArrayList<>();
            List<String> imgPaths = new ArrayList<>();
            try {
                System.out.println("batchUploadProductImg,file.length = " + file.length);
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        // 腾讯云上传图片
                        String fileName = COSClientUtils.getInstance().uploadFile2Cos(file[i]);
                        String imgUrl = COSClientUtils.getInstance().getImgUrl(fileName);

                        fileNames.add(fileName);
                        imgPaths.add(imgUrl);
                    }
                }
                //上传成功
                if (!CollectionUtils.isEmpty(fileNames)) {
                    System.out.println("上传成功！");
                    vo.setCode(FacConstant.AJAX_CODE_SUCCESS);
                    vo.setFileName(StringUtils.join(fileNames, ","));
                    vo.setImgPath(StringUtils.join(imgPaths, ","));
                } else {
                    log.error("[batchUploadProductImg] 上传失败！文件格式错误！");
                    vo.setMsg("上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                log.error("[batchUploadProductImg] 上传出现异常！异常出现在：class.CommonController.batchUploadProductImg()！", e);
                vo.setMsg("上传出现异常！");
            }
        } else {
            vo.setMsg("没有检测到文件！");
        }

        return vo;
    }

    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
