package com.paranoia.upupup.poi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZHANGKAI
 * @date 2018/11/9
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHead {

    private String excelName;             //Excel名
    private String entityName;            //实体类属性名
    private boolean required=false;      //值必填

    public ExcelHead(String excelName, String entityName) {
        this.excelName = excelName;
        this.entityName = entityName;
    }
}
