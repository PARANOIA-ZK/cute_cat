package com.paranoia.mongo.controller;

import com.sun.rowset.internal.Row;
import javafx.scene.control.Cell;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

/**
 * @author ZHANGKAI
 * @date 2018/11/8
 * @description
 */
@RestController
@CrossOrigin("*")
public class TestController {

    @PostMapping("/excel")
    public String uploadExcel(@RequestParam(name = "file") MultipartFile file) throws Exception {
        Assert.notNull(file, "文件不能为空");

        String filename = file.getOriginalFilename();

        System.out.println("filename = " + filename);

        return "success";

    }
}
