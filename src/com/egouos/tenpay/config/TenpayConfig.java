package com.egouos.tenpay.config;

import com.egouos.pojo.SysConfigure;
import com.egouos.util.ApplicationListenerImpl;

public class TenpayConfig
{
  public static String partner = ApplicationListenerImpl.sysConfigureJson.getTenpayPartner();
  public static String key = ApplicationListenerImpl.sysConfigureJson.getTenpayKey();
  public static String return_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/tenpay/returnUrl.action";
  public static String notify_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/tenpay/notifyUrl.action";
  public static String balance_return_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/balance/returnUrl.action";
  public static String balance_notify_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/balance/notifyUrl.action";
}
