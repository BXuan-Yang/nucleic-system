package com.ydf.queue.utils;

import com.aliyun.oss.OSSClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ydf.queue.domain.Oss;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author ywb
 * 二维码工具类
 **/
public class BarCodeUtils {

    /**
     * 通过bufferedImage，获取图片的base64
     * @param bufferedImage bufferedImage
     * @return String
     */
    public static String getImage2Base64String(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "png", bos);
                byte[] imageBytes = bos.toByteArray();
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static BufferedImage generateBarcodeWithoutWhite(String content, Color color) {
        return generateBarcodeWithoutWhite(content, 200, 200, color);
    }

    /**
     * @param content 二维码内容
     * @param width   二维码图片宽
     * @param height  二维码图片高
     * @param color   二维码图片颜色
     * @return BufferedImage
     */
    private static BufferedImage generateBarcodeWithoutWhite(String content, int width, int height, Color color) {
        try {
            //配置图片的一些属性
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//设置二维码的编码
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//设置二维码的纠错率
            hints.put(EncodeHintType.MARGIN, 0);//设置条形码的间距

            BitMatrix encode = encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //如果传入的颜色是null的话,将颜色设置成黑色
            if (color == null) {
                color = Color.BLACK;
            }
            //初始化图片对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            //将二维码对象中的有效数据展示出来，把encode的数据在image中显示
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    //对image设置，有效数据就显示黑色，否则显示白色
                    image.setRGB(x, y, encode.get(x, y) ? color.getRGB() : Color.white.getRGB());
                }
            }
            return image;
        } catch (Exception var9) {
            return null;
        }
    }

    private static BitMatrix encode(String contents, BarcodeFormat format, int width, int height,
                                    Map<EncodeHintType, Object> hints) throws WriterException {
        //对数据进行验证
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("数据不能为空");
        } else if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("只能生成二维码: ".concat(String.valueOf(format)));
        } else if (width >= 0 && height >= 0) {
            if (hints == null) {
                //如果hints为null,设置默认的信息
                hints = new HashMap<>();
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//设置纠错能力
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置编码，防止中文乱码
                hints.put(EncodeHintType.MARGIN, 0);//设置留白的大小
            }
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            return multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, width, height,hints);
        } else {
            throw new IllegalArgumentException("要求的尺寸太小:" + width + 'x' + height);
        }
    }



    /**
     *
     * @param bufferedImage 图片输入流
     * @param oss  oss对象
     * @param fileName  文件名
     * @return 上传到oss后的链接
     */
    public static String uploadFileToOss(BufferedImage bufferedImage, Oss oss, String fileName) {
        if (bufferedImage != null) {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = null;
            try {
                imOut = ImageIO.createImageOutputStream(bs);
                ImageIO.write(bufferedImage, "png", imOut);
                InputStream is = new ByteArrayInputStream(bs.toByteArray());
                fileName = fileName == null || fileName.isEmpty() ? UUID.randomUUID().toString() + ".png" : fileName + ".png";
                OSSClient ossClient = new OSSClient(oss.getEndpoint(), oss.getAccessKeyId(), oss.getSecretAccessKey());
                ossClient.putObject(oss.getBucketName(), "testmanage/"+fileName, is);
                return " https://" + oss.getBucketName() + ".oss-cn-guangzhou.aliyuncs.com/" +oss.getFileDir()+ fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (imOut != null) {
                    try {
                        imOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;

    }


    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println();
    }
}