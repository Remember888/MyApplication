package com.example.musicmanager.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class StreamUtil {



    public static String createStr(InputStream inputStream) {
        String jsonStr = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = "";
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

    public static byte[] createBytes(InputStream is) {
        ByteArrayOutputStream out = null;
        byte[] datas = null;
        try {
            out = new ByteArrayOutputStream();
            byte[] buffer  = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, buffer.length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        datas = out.toByteArray();

        return datas;
    }
}
