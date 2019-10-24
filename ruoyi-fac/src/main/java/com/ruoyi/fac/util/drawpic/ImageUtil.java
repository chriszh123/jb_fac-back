/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/10/18
 * Description: ImageUtil
 */
package com.ruoyi.fac.util.drawpic;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * ImageUtil
 *
 * @author zhangguifeng
 * @create 2019-10-18 16:04
 **/
public class ImageUtil {
    static final String IMAGE_SUFFIX = ".png";
    static final int HEAD_URL_WIDTH = 100;
    static final int HEAD_URL_HEIGHT = 100;

    /**
     * @param posterImgUrl     海报
     * @param tempQrCodeImgUrl 临时二维码
     * @param headImgUrl       头像
     * @return 合成图片地址
     */
    public static String drawImage(String posterImgUrl, String tempQrCodeImgUrl, String headImgUrl) throws IOException {

        BufferedImage posterBufImage = ImageIO.read(new URL(posterImgUrl));
        Graphics2D posterBufImageGraphics = posterBufImage.createGraphics();

        BufferedImage qrCodeImage = ImageIO.read(new URL(tempQrCodeImgUrl));
        BufferedImage headImage = ImageIO.read(new URL(headImgUrl));

        //设置圆形头像
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg =
            roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(), headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        posterBufImageGraphics
            .drawImage(qrCodeImage, (posterBufImage.getWidth() - qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(), qrCodeImage.getHeight(), null);
        posterBufImageGraphics.drawImage(roundHeadImg, 50, 100, HEAD_URL_WIDTH, HEAD_URL_HEIGHT, null);
        posterBufImageGraphics.dispose();

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imgOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(posterBufImage, "png", imgOut);
        InputStream inSteam = new ByteArrayInputStream(bs.toByteArray());

        //        String url = OSSFactory.build().uploadSuffix(inSteam, IMAGE_SUFFIX);
        String fileName = "D:/zgf.png";
        transferFile(inSteam, fileName);
        return fileName;//返回合成的图片地址url
    }

    public static void transferFile(InputStream is, String fileName) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        in = new BufferedInputStream(is);
        out = new BufferedOutputStream(new FileOutputStream(fileName));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public static void drawImage() throws IOException {
        //海报图
        String backgroundImageUrl = "http://img1.juimg.com/171010/330841-1G01000050879.jpg";
        //二维码
        String qrCodeImageUrl =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553504957130&di=02fae20a5c0f885d52299b2b1d682c86&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F2%2F1%2F3%2F061%2F3213061%2F1.gif";
        //头像
        String headUrl = "http://pic.51yuansu.com/pic3/cover/00/63/25/589bdedf5475d_610.jpg";

        BufferedImage bgBufImage = ImageIO.read(new URL(backgroundImageUrl));
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();

        BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeImageUrl));
        BufferedImage headImage = ImageIO.read(new URL(headUrl));
        //设置圆形图片
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg =
            roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(), headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        bgBufImageGraphics
            .drawImage(qrCodeImage, (bgBufImage.getWidth() - qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(), qrCodeImage.getHeight(), null);
        bgBufImageGraphics.drawImage(roundHeadImg, 50, 100, HEAD_URL_WIDTH, HEAD_URL_HEIGHT, null);
        bgBufImageGraphics.dispose();
        ImageIO.write(bgBufImage, "png", new File("E:\\demo1.png"));

    }

    public static void main(String[] args) throws IOException {
        //海报图
        String backgroundImageUrl = "http://img1.juimg.com/171010/330841-1G01000050879.jpg";
        //二维码
        String qrCodeImageUrl =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553504957130&di=02fae20a5c0f885d52299b2b1d682c86&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F2%2F1%2F3%2F061%2F3213061%2F1.gif";
        //头像
        String headUrl = "http://pic.51yuansu.com/pic3/cover/00/63/25/589bdedf5475d_610.jpg";
        String url = ImageUtil.drawImage(backgroundImageUrl, qrCodeImageUrl, headUrl);

        System.out.println("url=" + url);
    }

}