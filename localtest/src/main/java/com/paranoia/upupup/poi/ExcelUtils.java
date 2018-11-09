package com.paranoia.upupup.poi;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/11/9
 * @description
 */
public class ExcelUtils {

    private static final String FULL_DATA_FORMAT = "yyyy/MM/dd  HH:mm:ss";
    private static final String SHORT_DATA_FORMAT = "yyyy/MM/dd";


    public static <T> List<T> readExcelToEntity(Class<T> classzz, InputStream in, String fileName, List<ExcelHead> excelHeads) throws Exception {
        checkFile(fileName);
        Workbook workbook = createWorkbook(in, fileName);
        List<T> excelForBeans = readExcel(classzz, workbook, excelHeads);
        return excelForBeans;
    }

    public static <T> List<T> readExcelToEntity(Class<T> classzz, InputStream in, String fileName) throws Exception {
        checkFile(fileName);
        Workbook workbook = createWorkbook(in, fileName);

        Sheet sheet = workbook.getSheetAt(0);

        //创建属性map
        HashMap<String, String> valueMap = new HashMap<>();
        Field[] fields = classzz.getDeclaredFields();
        List<T> beanList = new ArrayList<>();

        //遍历excel文件
        for (int x = sheet.getFirstRowNum() + 1; x <= sheet.getLastRowNum(); x++) {
            Row row = sheet.getRow(x);
            for (int i = 0; i < fields.length; i++) {
                Cell cell = row.getCell(i);
                cell.setCellType(CellType.STRING);
                valueMap.put(fields[i].getName(), cell.getStringCellValue());
            }
            T t = classzz.newInstance();
            //使用BeanUtils将封装的属性注入对象
            BeanUtils.populate(t, valueMap);
            //将对象添加至容器
            beanList.add(t);
        }
        return beanList;

    }


    private static void checkFile(String fileName) throws Exception {
        if (!StringUtils.isEmpty(fileName) && !(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
            throw new Exception("不是Excel文件！");
        }
    }


    private static Workbook createWorkbook(InputStream in, String fileName) throws IOException {
        if (fileName.endsWith(".xls")) {
            return new HSSFWorkbook(in);
        } else {
            return new XSSFWorkbook(in);
        }
    }

    private static <T> List<T> readExcel(Class<T> classzz, Workbook workbook, List<ExcelHead> excelHeads) throws Exception {
        List<T> beans = new ArrayList<>();
        int sheetNum = workbook.getNumberOfSheets();

        for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            //第一行数据  做数据匹配
            Row head = sheet.getRow(firstRowNum);
            if (head == null)
                continue;
            short firstCellNum = head.getFirstCellNum();
            short lastCellNum = head.getLastCellNum();
            Field[] fields = classzz.getDeclaredFields();
            for (int rowIndex = firstRowNum + 1; rowIndex <= lastRowNum; rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                if (dataRow == null)
                    continue;
                T instance = classzz.newInstance();
//                if(CollectionUtils.isEmpty(excelHeads)){  //非头部映射方式，默认不校验是否为空，提高效率
//                    firstCellNum=dataRow.getFirstCellNum();
//                    lastCellNum=dataRow.getLastCellNum();
//                }
                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; cellIndex++) {
                    Cell headCell = head.getCell(cellIndex);
                    if (headCell == null)
                        continue;
                    Cell cell = dataRow.getCell(cellIndex);
                    headCell.setCellType(CellType.STRING);
                    String headName = headCell.getStringCellValue().trim();
                    if (StringUtils.isEmpty(headName)) {
                        continue;
                    }
                    ExcelHead eHead = null;
                    if (!CollectionUtils.isEmpty(excelHeads)) {
                        for (ExcelHead excelHead : excelHeads) {
                            if (headName.equals(excelHead.getExcelName())) {
                                eHead = excelHead;
                                headName = eHead.getEntityName();
                                break;
                            }
                        }
                    }
                    for (Field field : fields) {
                        if (headName.equalsIgnoreCase(field.getName())) {
                            String methodName = MethodUtils.setMethodName(field.getName());
                            Method method = classzz.getMethod(methodName, field.getType());
                            if (isDateFied(field)) {
                                Date date = null;
                                if (cell != null) {
                                    date = cell.getDateCellValue();
                                }
                                if (date == null) {
                                    volidateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, cell.getDateCellValue());
                            } else {
                                String value = null;
                                if (cell != null) {
                                    cell.setCellType(CellType.STRING);
                                    value = cell.getStringCellValue();
                                }
                                if (StringUtils.isEmpty(value)) {
                                    volidateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, convertType(field.getType(), value.trim()));
                            }
                            break;
                        }
                    }
                }
                beans.add(instance);
            }
        }
        return beans;
    }

    /**
     * 是否日期字段
     *
     * @param field
     * @return
     */
    private static boolean isDateFied(Field field) {
        return (Date.class == field.getType());
    }

    /**
     * 空值校验
     *
     * @param excelHead
     * @throws Exception
     */
    private static void volidateValueRequired(ExcelHead excelHead, String sheetName, int rowIndex) throws Exception {
        if (excelHead != null && excelHead.isRequired()) {
            throw new Exception("《" + sheetName + "》第" + (rowIndex + 1) + "行:\"" + excelHead.getExcelName() + "\"不能为空！");
        }
    }

    /**
     * 类型转换
     *
     * @param classzz
     * @param value
     * @return
     */
    private static Object convertType(Class classzz, String value) {
        if (Integer.class == classzz || int.class == classzz) {
            return Integer.valueOf(value);
        }
        if (Short.class == classzz || short.class == classzz) {
            return Short.valueOf(value);
        }
        if (Byte.class == classzz || byte.class == classzz) {
            return Byte.valueOf(value);
        }
        if (Character.class == classzz || char.class == classzz) {
            return value.charAt(0);
        }
        if (Long.class == classzz || long.class == classzz) {
            return Long.valueOf(value);
        }
        if (Float.class == classzz || float.class == classzz) {
            return Float.valueOf(value);
        }
        if (Double.class == classzz || double.class == classzz) {
            return Double.valueOf(value);
        }
        if (Boolean.class == classzz || boolean.class == classzz) {
            return Boolean.valueOf(value.toLowerCase());
        }
        if (BigDecimal.class == classzz) {
            return new BigDecimal(value);
        }
       /* if (Date.class == classzz) {
            SimpleDateFormat formatter = new SimpleDateFormat(FULL_DATA_FORMAT);
            ParsePosition pos = new ParsePosition(0);
            Date date = formatter.parse(value, pos);
            return date;
        }*/
        return value;
    }

    /**
     * 获取properties的set和get方法
     */
    static class MethodUtils {
        private static final String SET_PREFIX = "set";
        private static final String GET_PREFIX = "get";

        private static String capitalize(String name) {
            if (name == null || name.length() == 0) {
                return name;
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        private static String setMethodName(String propertyName) {
            return SET_PREFIX + capitalize(propertyName);
        }

        private static String getMethodName(String propertyName) {
            return GET_PREFIX + capitalize(propertyName);
        }
    }
}
