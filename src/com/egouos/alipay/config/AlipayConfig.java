package com.egouos.alipay.config;

import com.egouos.pojo.SysConfigure;
import com.egouos.util.ApplicationListenerImpl;

public class AlipayConfig
{
  public static String partner = ApplicationListenerImpl.sysConfigureJson.getAlipayPartner();
  public static String key = ApplicationListenerImpl.sysConfigureJson.getAlipayKey();
  //public static String return_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/alipay/returnUrl.action";
  public static String return_url = "http://weixin"+ApplicationListenerImpl.sysConfigureJson.getDomain()+ "/alipay/returnUrl.action";
  //public static String notify_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/alipay/notifyUrl.action";
  public static String notify_url = "http://weixin"+ApplicationListenerImpl.sysConfigureJson.getDomain()+ "/alipay/notifyUrl.action";
  public static String mail = ApplicationListenerImpl.sysConfigureJson.getAlipayMail();
  public static String log_path = "/usr/";
  public static String input_charset = "utf-8";
  public static String sign_type = "MD5";
}
