<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>收货地址管理_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
  </head>
  
<body class="g-acc-bg">
 <div class="h5-1yyg-v1" id="loadingPicBlock">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <!-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/index.html" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>收货地址管理</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> -->
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="/user/index.html" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>收货地址管理</h1>
	</div>
    
    
	<input type="hidden" value="${userId }" id="userId"/>
    <input name="hidTotalCount" type="hidden" id="hidTotalCount" />
    <input name="hidPageMax" type="hidden" id="hidPageMax" />
    <!-- 完善地址 -->
    <div id="div_confirm">
        <div class="addre-wapper">
            <ul class="addre-list">
                <s:iterator value="userbyaddressList" var="Adds" status="index">
	             <li id="${Adds.id }">
	                 <span class="name gray6">${Adds.consignee }</span>
	                 <span class="tel gray6">${Adds.phone }</span>
	                 <p>
	                     <span class="gray9">${Adds.province }${Adds.city }</span>
	                     <span class="gray9">${Adds.district }</span>
	                     <span class="gray9">${Adds.address }</span>
	                 </p>
	                 <c:if test="${Adds.status==1}">
		             <i class="z-set" style="display: block;"></i>
		             </c:if>
		             <c:if test="${Adds.status!=1}">
		             <i class="z-set" style="display: none;"></i>
		             </c:if>
	             </li>
	             </s:iterator>
	         </ul>
	         <a id="btnAddAddr" href="AddressAdd.html" class="addre-btn">添加新地址</a>
	         <%-- <div class="opt-wrapper clearfix"><a id="btnUpdateAddr" href="javascript:;" class="opt-btn addnew"><span>修改</span></a></div> --%>
	    </div>
	</div>
	
	<script language="javascript" type="text/javascript" src="/js/addresslist.js?data=20131121"></script>
</body>
</html>
