package com.ruoyi.fac.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

@Slf4j
public class AudioPlayerUtil {
    // 音乐文件的URl
    private URL url = null;
    // 播放器
    private AudioStream audioStream = null;

    private static AudioPlayerUtil instance = null;

    protected AudioPlayerUtil() {
        try {
            File file = ResourceUtils.getFile("classpath:file/test.wav");
//            File file = ResourceUtils.getFile("F://test.wav");
            url = file.toURI().toURL();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static AudioPlayerUtil getInstance() {
        if (ObjectUtil.isNull(instance)) {
            instance = new AudioPlayerUtil();
        }
        return instance;
    }

    /**
     * 用AudioPlayer静态成员player.start播放音乐
     */
    public void play() {
        try {
            if (url == null) {
                log.error("==========================[play] url is null.");
                return;
            }
            // 获得音乐文件的输入流
            InputStream inputStream = url.openStream();
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
            log.info("======================================[play] success: {}", DateUtil.date());
        } catch (Exception e) {
            log.error("=======[play] error : " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        AudioPlayerUtil.getInstance().play();
    }
}
