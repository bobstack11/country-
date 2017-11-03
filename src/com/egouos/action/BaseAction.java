package com.egouos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egouos.pojo.Producttype;
import com.egouos.service.ProducttypeService;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ProducttypeService protypeService;
	protected List<Producttype> protypeList;
	
	protected String pt;
	protected String redirectUri;
	protected String code;
	
	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	public List<Producttype> getProtypeList() {
    	if(protypeList != null){
    		return protypeList;
    	}
    	// pzp 2015-09-18
        protypeList  = protypeService.listByProductList();
    	return protypeList;
 	}

	public void setProtypeList(List<Producttype> protypeList) {
    	this.protypeList = protypeList;
	}

}
