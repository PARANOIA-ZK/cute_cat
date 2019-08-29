package com.denghuiwen.tiger.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZHANGKAI
 * @date 2018/11/9
 * @description
 */
public class ExcelUtil {


    /**
     * @param clazz    实体靶向
     * @param in       文件流
     * @param fileName 文件名称
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcelToEntity(Class<T> clazz, InputStream in, String fileName, int beginRow) throws Exception {
//        checkFile(fileName);
        Workbook workbook = createWorkbook(in, fileName);
        Sheet sheet = workbook.getSheetAt(0);
        return getData(clazz, sheet, beginRow);
    }

    /**
     * @param clazz 实体
     * @param file  文件
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcelToEntity(Class<T> clazz, File file, int beginRow) throws Exception {
        return readExcelToEntity(clazz, new FileInputStream(file), file.getName(), beginRow);
    }

    public static HSSFWorkbook generateExcel(List<Object> listTar, String sheetName, String[] tableName) {
        List<List<String>> objsColumnValues = getObjsColumnValues(listTar);
        HSSFWorkbook book = new HSSFWorkbook();

        HSSFSheet sheet = book.createSheet(sheetName);
        sheet.autoSizeColumn(1, true);

        // 填充表头标题
        HSSFRow firstRow = sheet.createRow(0);// 第几行（从0开始）
        for (int i = 0; i < tableName.length; i++) {
            firstRow.createCell(i).setCellValue(tableName[i]);
        }

        // 填充表格内容
        if (!CollectionUtils.isEmpty(objsColumnValues)) {
            for (int i = 0; i < objsColumnValues.size(); i++) {
                HSSFRow row2 = sheet.createRow(i + 1);// index：第几行
                List<String> data = objsColumnValues.get(i);
                for (int j = 0; j < data.size(); j++) {
                    HSSFCell cell = row2.createCell(j);// 第几列：从0开始
                    //设置单元格内容为字符串型
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.get(j));
                }
            }
        }
        return book;
    }

    //--------------------------------------------------------------------------------------


    private static List<String> getObjColumnValue(Object tar) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        List<String> result = new ArrayList<>();

        Class<?> clazz = tar.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method getMethod = pd.getReadMethod();
            String s = String.valueOf(getMethod.invoke(tar));
            if (StringUtils.isEmpty(s)) {
                result.add("");
            } else {
                result.add(s);
            }
        }
        return result;
    }

    private static List<List<String>> getObjsColumnValues(List<Object> listTar) {
        return listTar
                .stream()
                .map(tar -> {
                    try {
                        return getObjColumnValue(tar);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    private static <T> List<T> getData(Class<T> clazz, Sheet sheet, int beginRow) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        //创建属性map
        HashMap<String, String> valueMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        List<T> beanList = new ArrayList<>(1_000);

        //遍历excel文件
        T t;
        Cell cell;
        Row row;
        for (int x = sheet.getFirstRowNum() + beginRow - 1, y = sheet.getLastRowNum(); x <= y; x++) {
            row = sheet.getRow(x);
            for (int i = 0; i < fields.length; i++) {
                cell = row.getCell(i);
                valueMap.put(fields[i].getName(), getCellValueAsString(cell));
            }
            t = clazz.newInstance();
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

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (CellType.NUMERIC == cell.getCellTypeEnum() && HSSFDateUtil.isCellDateFormatted(cell)){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
}


