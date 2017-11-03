package com.egouos.sms;

import com.egouos.sms.chuangming.CmSender;
import com.egouos.sms.smsbao.SmsbaoSender;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.StringUtil;

public class SmsSenderFactory {

	public static SmsSender create() {
		String smsType = ApplicationListenerImpl.sysConfigureJson.getSmsType();
		if(StringUtil.equals(smsType, "chuangming"))
			return new CmSender();
		else if(StringUtil.equals(smsType, "smsbao"))
			return new SmsbaoSender();
		return new CmSender();
	}
	
	public static SmsSender cmsender() {
		return new CmSender();
	}
	
	public static SmsSender smsbaosender() {
		return new SmsbaoSender();
	}
	
}
