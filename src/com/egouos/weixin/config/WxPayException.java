package com.egouos.weixin.config;

public class WxPayException extends Exception{

	private static final long serialVersionUID = -2260254316730995571L;

	public WxPayException(){
		
	}
	
	public WxPayException(final String message){
		super(message);
	}
	
	public WxPayException(final Throwable cause){
		super(cause);
	}

	public WxPayException(final String message, final Throwable cause){
		super(message, cause);
	}
	
}
