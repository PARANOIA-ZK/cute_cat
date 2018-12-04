package com.paranoia.upupup.poi;

import lombok.Data;

/**
 * @author ZHANGKAI
 * @date 2018/11/9
 * @description
 */
@Data
public class ExcelDto {

    private String name;

    private String level;

    private String province;

    private String city;

    private String distinct;

    private String address;

    private String phone;

    /**
     * 折扣
     */
    private String discount;

    private String status;

    private String date;
}
