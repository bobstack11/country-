<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>忘记密码_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
	<meta content="app-id=518966501" name="apple-itunes-app" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link href="/css/login.css" rel="stylesheet" type="text/css" />
    <script src="/js/findpassword.js" language="javascript" type="text/javascript"></script>
  </head>
  
 <body>
     <div class="h5-1yyg-v1" id="content">
        
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <!-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>找回密码</h2>
        <div class="head-r">
	        <a href="/" class="z-Home"></a>
        </div>
    </header> -->
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>找回密码</h1>
	</div>
	
        <section>
	        <div class="registerCon">
    	        <ul>
        	        <li><input id="userAccount" type="text" placeholder="请输入手机号/邮箱" maxlength="50"></li>
                    <li><a href="javascript:void(0);" id="btnGetCode" class="nextBtn  orgBtn">下一步</a></li>
                </ul>
	        </div>
        </section>
    </div>
 </body>
</html>
