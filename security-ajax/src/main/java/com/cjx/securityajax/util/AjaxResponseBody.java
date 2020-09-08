package com.cjx.securityajax.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponseBody {

    //返回体中返回的状态码
    private String status;
    //返回的错误信息
    private String msg;
}
