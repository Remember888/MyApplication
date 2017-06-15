package com.tedu.employeemanager.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.tedu.employeemanager.constant.IURL;
import com.tedu.employeemanager.entity.Employee;
import com.tedu.employeemanager.entity.User;
import com.tedu.employeemanager.util.ParamsUtil;
import com.tedu.employeemanager.util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class HttpManager {
    static String SESSIONID = "";

    public static boolean RegistManager(User user) {
        boolean flag = false;
        try {
            URL url = new URL(IURL.REGOST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Map<String, String> map = new HashMap<>();
            map.put("loginname", user.getLocalname());
            map.put("password", user.getPassword());
            map.put("realname", user.getRealname());
            map.put("email", user.getEmail());

            byte[] datas = ParamsUtil.createParams(map).getBytes();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(datas.length));
            connection.connect();

            //获得指向服务器的数据输出流向服务器端提交数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(datas);
            outputStream.flush();

            int stateCode = connection.getResponseCode();
            if (stateCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String jsonStr = StreamUtil.createStr(inputStream);
                JSONObject object = new JSONObject(jsonStr);
                String result = object.getString("result");
                if ("ok".equals(result)) {
                    flag = true;
                } else {
                    String msg = object.getString("msg");
                    Log.i("TAG:msg", msg);
                }
            } else {
                Log.i("TAG:statusCode", "" + stateCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Bitmap LoginCodeHttpGet() throws IOException {
        Bitmap bitmap = null;
        try {
            URL url = new URL(IURL.CODE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                SESSIONID = connection.getHeaderField("Set-Cookie").split(";")[0];
                InputStream stream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(stream);
            } else {
                Log.d("LAG", "" + statusCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static boolean LoginCodeHttpPost(String loginName, String password, String code) throws IOException {
        boolean flag = false;
        try {
            URL url = new URL(IURL.LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);

            connection.setRequestProperty("Cookie",SESSIONID);
            Map<String, String> map = new HashMap<>();
            map.put("loginname", loginName);
            map.put("password", password);
            map.put("code", code);
            byte[] datas = ParamsUtil.createParams(map).getBytes();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(datas.length));
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(datas);
            outputStream.flush();
            outputStream.close();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String jsonStr = StreamUtil.createStr(inputStream);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)) {
                    flag = true;
                } else {
                    String msg = jsonObject.getString("msg");
                    Log.d("LAG:msg", msg);
                }

            } else {
                Log.d("LAG:statusCode",""+ statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean employeeHttpPost(Employee employee) {
        boolean flag = false;
        try {
            URL url = new URL(IURL.ADDEMP_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);

            Map<String, String> map = new HashMap<>();
            map.put("name", employee.getName());
            map.put("salary",""+employee.getSalary());
            map.put("age", "" + employee.getAge());
            map.put("gender", employee.getGender());
            byte[] datas = ParamsUtil.createParams(map).getBytes();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(datas.length));
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(datas);
            outputStream.flush();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = connection.getInputStream();

                String jsonStr = StreamUtil.createStr(inputStream);
                System.out.println("jsonStr:"+jsonStr);

                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)) {
                    flag = true;
                } else {
                    String msg = jsonObject.getString("msg");
                    Log.d("LAG:msg", msg);
                }

            } else {
                Log.d("LAG:statusCode",""+ statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }
    public static List<Employee> quertDatasGet(){
        List<Employee> list = new ArrayList<>();
        Employee employee = new Employee();
        try {
            URL url = new URL(IURL.QUERTEMP_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String jsonStr = StreamUtil.createStr(inputStream);
                JSONObject object = new JSONObject(jsonStr);
                String result = object.getString("result");
                if ("ok".equals(result)) {
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        int salary = jsonObject.getInt("salary");
                        int age = jsonObject.getInt("age");
                        String gender = jsonObject.getString("gender");

                        employee.setId(id);
                        employee.setAge(age);
                        employee.setGender(gender);
                        employee.setName(name);
                        employee.setSalary(salary);

                        list.add(employee);
                    }
                } else {
                    String msg = object.getString("msg");
                    Log.d("LAG:msg", msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return list;
    }


}
