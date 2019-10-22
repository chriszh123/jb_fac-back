package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.model.FacProduct;
import com.ruoyi.fac.service.WeiXinService;
import com.ruoyi.fac.vo.client.req.WxaQrcodeReq;
import com.ruoyi.fac.vo.client.res.WxaQrcodeVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service("weiXinService")
public class WeiXinServiceImpl implements WeiXinService {
    private static final Logger log = LoggerFactory.getLogger(WeiXinServiceImpl.class);
    /**
     * 获取微信小程序码url
     */
    private static final String WEIXIN_ARCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    @Autowired
    private ProductCache productCache;

    @Override
    public WxaQrcodeVo createPoster(WxaQrcodeReq req) throws Exception {
        long start = System.currentTimeMillis();
        log.info(String.format("--------------[createPoster] start, req:%s", req));
        FacProduct facProduct = this.productCache.getFacProductCache(req.getProductId());
        if (facProduct == null) {
            log.info(String.format("---------------[createPoster] product is not exist, req:%s", req));
            throw new Exception("商品已经不存在");
        }
        // 微信小程序access_token
        String accessToken = this.productCache.getWeixinAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            log.info(String.format("-----------------------[createPoster] accessToken is blank."));
            throw new Exception("AccessToken不能为空");
        }

        // 小程序应用小程序码图片文件名称 start
        String qrcodeName = UUID.randomUUID().toString().replace("-", "");
        // 前端当前商品页面路由页面路由地址
        JSONObject paramJson = new JSONObject();
        // 页面跳转路径携带的参数,page参数中不能携带任何参数，只能是页面路径，跳转需要的参数放在scene中(微信接口入参就是这么约定好的)
        String scene = req.getProductId() + "," + req.getInviterUid();
        paramJson.put("scene", scene);
        paramJson.put("page", req.getPage());
        paramJson.put("auto_color", false);
        JSONObject lineColor = new JSONObject();
        lineColor.put("r", 0);
        lineColor.put("g", 0);
        lineColor.put("b", 0);
        paramJson.put("line_color", lineColor);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(String.format(WEIXIN_ARCODE_URL, accessToken));
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = paramJson.toJSONString();
        StringEntity entity;
        // 小程序对应的小程序码图片文件
        File qrcodeFile;
        try {
            entity = new StringEntity(body);
            // 也可以是image/jpg
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            // 临时存储小程序码图片，后面操作结束后会删除掉这个临时文件
            String qrcodeImgUrl = Global.getImagesUploadPath() + "template/temp/" + qrcodeName + ".jpg";
            qrcodeFile = new File(qrcodeImgUrl);
            // 保存小程序码图片文件
            this.saveBitPic(inputStream, qrcodeFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("获取小程序码图片文件失败");
        }
        // 小程序应用二维码图片文件 end

        // 海报图片文件 start
        String posterFileName = UUID.randomUUID().toString().replace("-", "");
        FileInputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            //海报背景图片地址地址
            URL url = WeiXinServiceImpl.class.getResource("/image/qrcodebg.jpg");
            File fileBg = FileUtils.toFile(url);
            // 背景图片输入流
            fis = new FileInputStream(fileBg);
            // 背景图片对象
            Image srcImg = ImageIO.read(fis);
            // 创建画布，根据背景图片的宽高
            BufferedImage bufferedImage = new BufferedImage(
                    //宽度
                    srcImg.getWidth(null) + 200,
                    //高度
                    srcImg.getHeight(null) + 200,
                    //图片类型
                    BufferedImage.TYPE_INT_RGB);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            // 得到2d画笔对象
            Graphics2D g = bufferedImage.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 设置画布背景
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            // 开始作画
            //把商品图片和二维码图片划入背景
            g.drawImage(ImageIO.read(qrcodeFile), 100, 80, srcImg.getWidth(null), srcImg.getHeight(null), null);
            // 结束作画
            // 处理画作
            g.dispose();
            // 得到输出流
            os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", os);

            // 最终合成的商品海报:本地文件
            String posterImgUrl = Global.getImagesUploadPath() + "template/qrcode/" + posterFileName + ".jpg";
            File posterImgeFile = new File(posterImgUrl);
            FileUtils.writeByteArrayToFile(posterImgeFile, os.toByteArray());
            // 关闭输入输出流
            os.close();
            fis.close();
            qrcodeFile.delete();
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(os);
        }
        // 海报图片文件 end

        long end = System.currentTimeMillis();
        // 最终的海报图片文件:对外开放，域名访问的图片文件
        String imageUrl = Global.getDomain() + "/images/template/qrcode/" + posterFileName + ".jpg";
        log.info(String.format("--------------[createPoster] success, cost time : %s, imageUrl:%s", (end - start), imageUrl));

//        String imageUrl = "https://www.jbfac.xyz/images/template/qrcode/583f8aa556ef409e96cda602bd84162e.jpg";

        WxaQrcodeVo result = new WxaQrcodeVo();
        result.setImageUrl(imageUrl);
        result.setProdPrice(facProduct.getPrice().toString());

        return result;
    }

    private void saveBitPic(InputStream is, File dstFile) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            //创建一个Buffer字符串:1M
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                bos.write(buffer, 0, len);
            }
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            FileUtils.writeByteArrayToFile(dstFile, bos.toByteArray());
            //关闭输入流
            is.close();
            bos.close();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(bos);
        }
    }
}
