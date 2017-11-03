package com.egouos.util;

import com.egouos.pojo.SysConfigure;
import com.shcm.bean.BalanceResultBean;
import com.shcm.bean.SendResultBean;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;

import java.io.PrintStream;
import java.util.List;

public class Sampler
{
  private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
  private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";
  private static final String account = ApplicationListenerImpl.sysConfigureJson.getMessagePartner();
  private static final String authkey = ApplicationListenerImpl.sysConfigureJson.getMessageKey();
  private static final int cgid = Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageChannel());
  private static final int csid = Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageSign());
  
  public static List<SendResultBean> sendOnce(String mobile, String content)
    throws Exception
  {
	  String channel = ApplicationListenerImpl.sysConfigureJson.getMessageChannel();
	  String sign = ApplicationListenerImpl.sysConfigureJson.getMessageSign();
	  int intChannel =0;
	  int intSign = 0;
	  try {
		intChannel = Integer.parseInt(channel);
		intSign = Integer.parseInt(sign);
	} catch (Exception e) {
	}
    OpenApi.initialzeAccount(sOpenUrl, ApplicationListenerImpl.sysConfigureJson.getMessagePartner(), ApplicationListenerImpl.sysConfigureJson.getMessageKey(), intChannel, intSign);
    
    DataApi.initialzeAccount(sDataUrl, ApplicationListenerImpl.sysConfigureJson.getMessagePartner(), ApplicationListenerImpl.sysConfigureJson.getMessageKey());
    
    return OpenApi.sendOnce(new String[] { mobile }, content, Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageChannel()), Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageSign()), null);
  }
  
  public static String getMesBalance()
  {
    String balance = "";
    
    OpenApi.initialzeAccount(sOpenUrl, ApplicationListenerImpl.sysConfigureJson.getMessagePartner(), ApplicationListenerImpl.sysConfigureJson.getMessageKey(), Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageChannel()), Integer.parseInt(ApplicationListenerImpl.sysConfigureJson.getMessageSign()));
    
    DataApi.initialzeAccount(sDataUrl, ApplicationListenerImpl.sysConfigureJson.getMessagePartner(), ApplicationListenerImpl.sysConfigureJson.getMessageKey());
    
    BalanceResultBean br = OpenApi.getBalance();
    if (br.getResult() < 1) {
      balance = "获取可用余额失败" + br.getErrMsg();
    } else {
      balance = "可用条数: " + br.getRemain();
    }
    return balance;
  }
  
  public static void main(String[] args)
    throws Exception
  {
    OpenApi.initialzeAccount(sOpenUrl, "1001@500987080001", "EF8289967F47931C0B3A299D2BF7708F", 2186, 1983);
    
    DataApi.initialzeAccount(sDataUrl, "1001@500987080001", "EF8289967F47931C0B3A299D2BF7708F");
    
    BalanceResultBean br = OpenApi.getBalance();
    if (br.getResult() < 1)
    {
      System.out.println("获取可用余额失败: " + br.getErrMsg());
      return;
    }
    System.out.println("可用条数: " + br.getRemain());
  }
}
