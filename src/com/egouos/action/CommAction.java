/**
 * 
 */
package com.egouos.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.egouos.util.PatchcaUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

@Component("CommAction")
public class CommAction extends ActionSupport {

	@Autowired
	private PatchcaUtil patchcaUtil;
	
	private String code;
	
	public void catchca() throws IOException{
		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType("image/png");
		response.setHeader("cache", "no-cache");
		
		OutputStream os = response.getOutputStream();
		
		patchcaUtil.gen(response, os);
		
		os.flush();
		os.close();
		
	}
	
	public String validCatchca(){
		HttpServletRequest request =  Struts2Utils.getRequest();
		Struts2Utils.renderText(String.valueOf(patchcaUtil.verify(request, code)));
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
