package com.enjoyor.healthhouse.url;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UrlInterface {
    public static String RELEASE_URL = "http://www.cnbale.com:9008/healthstationserver/";
    public static String TEXT_URL = "http://115.28.37.145:9008/healthstationserver/";
    //    用户注册接口
    public static String Regist_URL = TEXT_URL + "account/appregister.action";
    //发送验证码接口
    public static String SendMsg_URL = TEXT_URL + "msg/send.action";


}
