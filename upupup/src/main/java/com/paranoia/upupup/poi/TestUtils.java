package com.paranoia.upupup.poi;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/11/9
 * @description
 */
public class TestUtils {

    public static void main(String[] args) throws Exception {
        //testOne();
        testTwo();
    }

    private static void testTwo() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\shicheng\\医院上传模板.xlsx");
        FileInputStream in = new FileInputStream(file);
        List<ExcelDto> list = ExcelUtils.readExcelToEntity(ExcelDto.class, in, file.getName());
        list.forEach(System.out::println);
    }

    private static void testOne() {
        File file = new File("C:\\Users\\Administrator\\Desktop\\shicheng\\医院上传模板.xlsx");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            List<ExcelHead> excelHeads = new ArrayList<ExcelHead>();
            Collections.addAll(excelHeads,
                    new ExcelHead("机构名称", "name"),
                    new ExcelHead("机构等级", "level"),
                    new ExcelHead("省", "province"),
                    new ExcelHead("市", "city", true),
                    new ExcelHead("县区市", "distinct", true),
                    new ExcelHead("详细地址", "address", true),
                    new ExcelHead("联系电话", "phone", true),
                    new ExcelHead("院方折扣", "discount", true),
                    new ExcelHead("状态", "status", true),
                    new ExcelHead("时间", "date", true));
            List<ExcelDto> list = ExcelUtils.readExcelToEntity(ExcelDto.class, in, file.getName(), excelHeads);
            for (ExcelDto excelDto : list) {
                System.out.println("excelDto = " + excelDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
