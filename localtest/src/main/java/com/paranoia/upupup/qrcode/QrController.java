package com.paranoia.upupup.qrcode;

import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ZHANGKAI
 * @date 2018/11/27
 * @description
 */
@RestController
public class QrController {

    @GetMapping("/qrcode")
    public OutputStream getCode() throws IOException, WriterException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        CreateQrCodeTest.writeToStream("你好", 500, 500, out);
        return out;
    }
}
