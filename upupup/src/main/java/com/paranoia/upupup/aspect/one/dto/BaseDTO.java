package com.paranoia.upupup.aspect.one.dto;

import com.paranoia.upupup.aspect.one.Inject;
import lombok.Data;

import java.util.Date;

/**
 * @author ZHANGKAI
 * @date 2019/6/14
 * @description
 */
@Data
@Inject
public class BaseDTO {

    private Date createdAt;


    private String createdUserName;


    private String createdUserCode;


    private Date updatedAt;


    private String updatedUserName;


    private String updatedUserCode;

    /**
     * NOTE : 以下删除相关的字段，只有在method_type为 DELETE 时，才会切面赋值。
     */

    private Date deletedAt;


    private String deletedUserName;


    private String deletedUserCode;
}
