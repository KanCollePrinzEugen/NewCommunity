package top.momoandnono.community.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;
import top.momoandnono.community.util.ResultUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 根据前端传来 要生成的 数量 生成 随机 验证码
 * return 验证码 BASE64 和 验证码
 * @author l
 */
@RestController
public class CodeController {

    @PostMapping("/code/{code}")
    public void code(@PathVariable("code") Integer code) {
        int width = code * 20;
        int height = 40;
        //1.创建一个对象，在内存中存图片（验证码图片对象）
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //2.美化图片
        //2.1 填充背景色
        Graphics graphics = image.getGraphics();
        //画笔对象
        //        graphics.fill//填充
        //        graphics.draw//画
        graphics.setColor(Color.white);
        //画笔的颜色
        graphics.fillRect(0, 0, width, height);
        //填充 rect 矩形
        Random random = new Random();

        graphics.setColor(Color.black);
        //画笔的颜色
        //2.3 写验证码
        String str = "QWERTYPADFGHLBNM123456789qwertypadfghjkbnm";
        //生成随机角标
        StringBuilder randomNumber = new StringBuilder();
        graphics.setColor(Color.black);
        //画笔的颜色
        //2.3 写验证码
        for (int i = 1; i <= code; i++) {
            //获取字符
            int nextInt = random.nextInt(str.length());

            //随机字符
            char charAt = str.charAt(nextInt);
            randomNumber.append(charAt);
            graphics.drawString(charAt + "", width / (code + 1) * i, 20);
        }

        //2.4干扰线
        graphics.setColor(Color.green);
        //随机生成坐标点

        for (int i = 0; i < 3; i++) {
            int nextWidth = random.nextInt(width);
            int nextHeight = random.nextInt(height);

            int nextWidth2 = random.nextInt(width);
            int nextHeight2 = random.nextInt(height);
            graphics.drawLine(nextWidth, nextHeight, nextWidth2, nextHeight2);
        }

        StringBuilder png = new StringBuilder("data:image/jpg;base64,");
        //3.将图片输出到页面展示
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String pngBase64 = encoder.encodeBuffer(bytes).trim();
            //转换成base64串
            png.append(pngBase64.replaceAll("\n", "").replaceAll("\r", ""));
            //删除 \r\n
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResultUtil.success(map -> {
            map.put("code", randomNumber);
            map.put("codeImg", png);
        });
    }

}