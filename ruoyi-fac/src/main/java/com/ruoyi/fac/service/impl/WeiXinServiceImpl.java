package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.Global;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.model.FacProduct;
import com.ruoyi.fac.service.WeiXinService;
import com.ruoyi.fac.vo.client.req.WxaQrcodeReq;
import com.ruoyi.fac.vo.client.res.WxaQrcodeVo;
//import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("weiXinService")
public class WeiXinServiceImpl implements WeiXinService {
    private static final Logger log = LoggerFactory.getLogger(WeiXinServiceImpl.class);

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
        // 前端当前商品页面路由页面路由地址,需要拼接加上商品id、邀请人uid信息
        String page = req.getPage();
        String resourcePath = WeiXinServiceImpl.class.getResource("/image/").getPath();
        String qrcodeName = "qrcode_" + UUID.randomUUID().toString().replace("-", "");
        // 微信小程序access_token
        String accessToken = this.productCache.getWeixinAccessToken();
        //二维码图片
//        URL getCodeUrl = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) getCodeUrl.openConnection();
//        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);
//        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
        // 页面跳转路径携带的参数,page参数中不能携带任何参数，只能是页面路径，跳转需要的参数放在scene中
        String scene = req.getProductId() + "," + req.getInviterUid();
        JSONObject paramJson = new JSONObject();
//        paramJson.accumulate("scene", scene).accumulate("page", page);
        paramJson.put("scene", scene);
        paramJson.put("page", page);
        paramJson.put("auto_color", false);
        JSONObject lineColor = new JSONObject();
        lineColor.put("r", 0);
        lineColor.put("g", 0);
        lineColor.put("b", 0);
        paramJson.put("line_color", lineColor);
//        printWriter.write(paramJson.toString());
//        printWriter.flush();
//        printWriter.close();  // 1
//        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
//        OutputStream ost = new FileOutputStream(new File(resourcePath + qrcodeName + ".png"));
//        int len;
//        byte[] qrcodeArrs = new byte[1024];
//        while ((len = bis.read(qrcodeArrs)) != -1) {
//            ost.write(qrcodeArrs, 0, len);
//            ost.flush();
//        }
//        ost.close();
//        bis.close(); // 1
//        httpURLConnection.disconnect();  // 1

        // start *************
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 接口
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = paramJson.toJSONString();//必须是json模式的 post
        StringEntity entity = null;
        try {
            entity = new StringEntity(body);
            entity.setContentType("image/png");//也可以是image/jpg
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            // 保存图片
            File qrcodeFile = new File(resourcePath + qrcodeName + ".png");
            this.saveBit(inputStream, qrcodeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // end *******************


//        //二维码图片文件
//        File qrCodeImg = FileUtils.toFile(WeiXinServiceImpl.class.getResource("/image/" + qrcodeName + ".png"));
//        //海报背景图片地址地址
//        URL url = WeiXinServiceImpl.class.getResource("/image/qrcodebg.jpg");
//        File fileBg = FileUtils.toFile(url);
//        //1、背景图片输入流
//        FileInputStream fis = new FileInputStream(fileBg);
//        //2、背景图片对象
//        Image srcImg = ImageIO.read(fis);
//        //3、创建画布，根据背景图片的宽高
//        BufferedImage bufferedImage = new BufferedImage(
//                //宽度
//                srcImg.getWidth(null) + 200,
//                //高度
//                srcImg.getHeight(null) + 200,
//                //图片类型
//                BufferedImage.TYPE_INT_RGB);
//        int width = bufferedImage.getWidth();
//        int height = bufferedImage.getHeight();
//        //4、得到2d画笔对象
//        Graphics2D g = bufferedImage.createGraphics();
//        // 设置对线段的锯齿状边缘处理
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        //5、设置画布背景
//        g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
//        //6、```````````开始作画```````````
//        //把商品图片和二维码图片划入背景
//        g.drawImage(ImageIO.read(qrCodeImg), 100, 80, srcImg.getWidth(null)
//                , srcImg.getHeight(null), null);
//        //   ```````````结束作画```````````
//        //7、处理画作
//        g.dispose();
//        //8、得到输出流
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", os);
//        //保存为图片文件
//        String qrcodeFileName = UUID.randomUUID().toString().replace("-", "");
//        String qrcodeImgUrl = Global.getImagesUploadPath() + "template/qrcode/" + qrcodeFileName + ".jpg";
//        File qrcodeImgeFile = new File(qrcodeImgUrl);
//        FileUtils.writeByteArrayToFile(qrcodeImgeFile, os.toByteArray());
//        //10、关闭输入输出流
//        os.close();
//        fis.close();
//        qrCodeImg.delete();
//
//        long end = System.currentTimeMillis();
//        String imageUrl = Global.getDomain() + "/images/template/qrcode/" + qrcodeFileName + ".jpg";
//        log.info(String.format("--------------[createPoster] success, cost time : %s, imageUrl:%s"
//                , (end - start), imageUrl));


        String imageUrl = "https://www.jbfac.xyz/images/template/qrcode/583f8aa556ef409e96cda602bd84162e.jpg";
        WxaQrcodeVo result = new WxaQrcodeVo();
        result.setImageUrl(imageUrl);
        result.setProdPrice(facProduct.getPrice().toString());

        return result;
    }

    public void saveBit(InputStream inStream, File dstFile) throws IOException {

//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        //创建一个Buffer字符串
//        byte[] buffer = new byte[1024];
//        //每次读取的字符串长度，如果为-1，代表全部读取完毕
//        int len = 0;
//        //使用一个输入流从buffer里把数据读取出来
//        while ((len = inStream.read(buffer)) != -1) {
//            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//            outStream.write(buffer, 0, len);
//        }
//        //关闭输入流
//        inStream.close();
        //把outStream里的数据写入内存

        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
//        byte[] data = outStream.toByteArray();
//        //创建输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dstFile));
//        //写入数据
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = inStream.read(b)) != -1) {
            out.write(b, 0, len);
        }
        inStream.close();
        out.close();
    }
}
