package com.baizhou.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

public class ImgCompressUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ImgCompressUtil.class);


    public static String resizeImageTo40K(String base64Img) {
        try {
            BufferedImage src = base64String2BufferedImage(base64Img);
            BufferedImage output = Thumbnails.of(src).size(src.getWidth() / 3, src.getHeight() / 3).asBufferedImage();
            String base64 = imageToBase64(output);
            if (base64.length() - base64.length() / 8 * 2 > 40000) {
                output = Thumbnails.of(output).scale(1 / (base64.length() / 40000)).asBufferedImage();
                base64 = imageToBase64(output);
            }
            return base64;
        } catch (Exception e) {
            return base64Img;
        }
    }

    /**
     * 根据指定大小压缩图片, 压缩成jpg
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] CompressPic(byte[] imageBytes, long desFileSize, String imageId, Double quality) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
            //指定输出格式为jpg, 否则压缩后容易反而变大
            Thumbnails.of(inputStream).scale(1f).outputFormat("jpg").outputQuality(quality).toOutputStream(outputStream);
            imageBytes = outputStream.toByteArray();
            LOG.info(
                    "【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
                    imageId,
                    srcSize / 1024,
                    imageBytes.length / 1024);
        } catch (Exception e) {
            LOG.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /***
     * base64转换成BufferedImage:
     * @param base64string
     * @return
     */
    public static BufferedImage base64String2BufferedImage(String base64string) {
        BufferedImage image = null;
        try {
            InputStream stream = BaseToInputStream(base64string);
            image = ImageIO.read(stream);
        } catch (IOException e) {
            LOG.info("");
        }
        return image;
    }

    /***
     * BufferedImage转换成base64，在这里需要设置图片格式，如下是jpg格式图片：
     * @param bufferedImage
     * @return
     */
    public static String imageToBase64(BufferedImage bufferedImage) {
        Base64 encoder = new Base64();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            LOG.info("");
        }
        return new String(encoder.encode((baos.toByteArray())));
    }

    /***
     * Base64转换成InputStream:
     * @param base64string
     * @return
     */
    private static InputStream BaseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stream;
    }

}
