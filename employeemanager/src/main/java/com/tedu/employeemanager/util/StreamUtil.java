package com.tedu.employeemanager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class StreamUtil {
    public static String createStr(InputStream in) {
        BufferedReader reader = null;
        String jsonStr = "";
        try {
            reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            jsonStr = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStr;
    }

}
