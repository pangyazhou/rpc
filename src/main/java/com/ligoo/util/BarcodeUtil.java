package com.ligoo.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 10:59:54
 * @Description:
 */
public class BarcodeUtil {

    /**
     * description: 生成条码文件
     * author: Administrator
     * date: 2019/3/12 11:07
     *
     * @param:
     * @return:
     */
    public static File generateFile(String msg, String path) throws FileNotFoundException {
        File file = new File(path);
        generate(msg, new FileOutputStream(file));
        return file;
    }

    /**
     * description: 返回条码字节流
     * author: Administrator
     * date: 2019/3/12 11:06
     *
     * @param:
     * @return:
     */
    public static byte[] generate(String msg){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        generate(msg, output);
        return output.toByteArray();
    }

    /**
     * description: 生成条码字节流
     * author: Administrator
     * date: 2019/3/12 11:06
     *
     * @param:
     * @return:
     */
    public static void generate(String msg, OutputStream output){
        if (StringUtils.isEmpty(msg) || output == null)
            return;
        Code39Bean bean = new Code39Bean();
        final int dpi = 150;
        final double moduleWidth = UnitConv.in2mm(1.0/dpi);

        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(true);

        String format = "image/png";
        try{
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(output, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, msg);
            canvas.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * description: 生成二维码
     * author: Administrator
     * date: 2019/3/12 11:14
     *
     * @param:
     * @return:
     */
    public static void encode(String msg) throws WriterException, IOException {
        String filePath = "D://";
        String fileName = "zxing.png";
        int width = 300;
        int height = 50;
        String format = "png";
        Map<EncodeHintType, Object> hits = new HashMap<>();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(msg, BarcodeFormat.CODE_128, width, height, hits);
        Path  path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
        System.out.println("success");
    }

    /**
     * description: 解码二维码
     * author: Administrator
     * date: 2019/3/12 11:14
     *
     * @param:
     * @return:
     */
    public static void decode(String path){
        String filePath = "D:\\zxing.png";
        BufferedImage image;
        try{
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hits = new HashMap<>();
            hits.put(DecodeHintType.CHARACTER_SET, "utf-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hits);
            System.out.println(result.getText());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws Exception{
        String msg = "B108120601M0015010193120003";
        encode(msg);
        //decode("");
    }
}
