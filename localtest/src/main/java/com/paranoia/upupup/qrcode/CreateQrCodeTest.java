package com.paranoia.upupup.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * @author ZHANGKAI
 * @date 2018/11/27
 * @description
 */
public class CreateQrCodeTest {

    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色
    private static final String imageFormat = "jpg"; //用于背景色
    private static final Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

    static {
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//      hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
//      hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        writeToStream("你好", 500, 500, out);
    }

    public static void writeToStream(String contents, int width, int height, OutputStream stream) throws IOException, WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选
        BufferedImage image = toBufferedImage(bitMatrix);

        if (!ImageIO.write(image, imageFormat, stream)) {
            throw new IOException("Could not write an image of format " + imageFormat);
        }
    }
    public static void writeToFile(String contents, int width, int height, File file) throws IOException, WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选
        BufferedImage image = toBufferedImage(bitMatrix);

        if (!ImageIO.write(image, imageFormat, file)) {
            throw new IOException("Could not write an image of format jpg to " + file);
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }
}
