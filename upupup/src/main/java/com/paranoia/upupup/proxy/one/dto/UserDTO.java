package com.paranoia.upupup.proxy.one.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZHANGKAI
 * @date 2019/6/14
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    private String userName;
}
