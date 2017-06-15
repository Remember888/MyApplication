package com.tedu.employeemanager.util;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class ParamsUtil {
    public static String createParams(Map<String,String> params) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        String str = builder.deleteCharAt(builder.length() - 1).toString();

        return str;
    }
}
