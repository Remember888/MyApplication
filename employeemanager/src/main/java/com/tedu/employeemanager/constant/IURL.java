package com.tedu.employeemanager.constant;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public interface IURL {
    String ROOT = "http://176.121.33.35:9080/EmployeeServer/";
    String REGOST_URL = ROOT + "regist.do";
    String CODE_URL=ROOT+"getCode.do";
    String LOGIN_URL = ROOT+"login.do";
    String ADDEMP_URL = ROOT+"addEmp";
    String QUERTEMP_URL = ROOT+"listEmp";
}
