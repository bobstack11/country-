package com.egouos.util;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

public class PatchcaUtil {

	public static final String COOKIE_PATCHCA="_PCT"; 
	
	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
	private static Random r = new Random();
	private static CurvesRippleFilterFactory crff = null;
	private static MarbleRippleFilterFactory mrff = null;
	private static DoubleRippleFilterFactory drff = null;
	private static WobbleRippleFilterFactory wrff = null;
	private static DiffuseRippleFilterFactory dirff = null;
	
	@PostConstruct
	public void init(){
		cs = new ConfigurableCaptchaService();
		cf = new SingleColorFactory(new Color(25, 60, 170));
		wf = new RandomWordFactory();
		crff = new CurvesRippleFilterFactory(cs.getColorFactory());
		drff = new DoubleRippleFilterFactory();
		wrff = new WobbleRippleFilterFactory();
		dirff = new DiffuseRippleFilterFactory();
		mrff = new MarbleRippleFilterFactory();
		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(120);
		cs.setHeight(50);
	}
	
	@PreDestroy
	public void destory(){
		wf = null;
		cf = null;
		cs = null;
	}
	
	public void gen(HttpServletResponse response, OutputStream os) throws IOException{
		String codeKey = UUID.randomUUID().toString();
		CookieUtil.addCookie(response, COOKIE_PATCHCA, codeKey);
		AliyunOcsSampleHelp.getIMemcachedCache().set(codeKey, 300, buildCode(os));
	}
	
	public boolean verify(HttpServletRequest request, String verifyCode){
		
		if(StringUtil.isEmpty(verifyCode)){
			return false;
		}
		
		Cookie cookie = CookieUtil.getCookie(request, COOKIE_PATCHCA);
		if(cookie==null){
			return false;
		}
		
		String codeKey = cookie.getValue();
		if(StringUtil.isEmpty(codeKey)){
			return false;
		}
		String bevalidCode = (String)AliyunOcsSampleHelp.getIMemcachedCache().get(codeKey);
		if(verifyCode.equalsIgnoreCase(bevalidCode)){
			return true;
		}
		return false;
	}
	
	public String buildCode(OutputStream os) throws IOException{
		wf.setMaxLength(5);
		wf.setMinLength(3);
		
		switch (r.nextInt(5)) {
		case 0:
			cs.setFilterFactory(crff);
			break;
		case 1:
			cs.setFilterFactory(mrff);
			break;
		case 2:
			cs.setFilterFactory(drff);
			break;
		case 3:
			cs.setFilterFactory(wrff);
			break;
		case 4:
			cs.setFilterFactory(dirff);
			break;
		}
		
		return EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
	}
}