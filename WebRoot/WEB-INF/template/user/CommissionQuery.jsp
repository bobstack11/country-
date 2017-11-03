<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>我的<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>中心_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> 一元<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %> <%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>网</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
  </head>
  
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    <input name="loadDataType" type="hidden" id="loadDataType" value="0" />
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/index.html"  class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>佣金明细</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    
	<div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>佣金明细</h1>
	</div>
	
<input type="hidden" value="${userId }" id="userId"/>
    <section class="clearfix g-member">
	    <article class="clearfix m-round g-userMoney">
		    可用佣金余额<span class="orange">${user.commissionBalance}</span>元
	    </article>
	    <article class="mt10 m-round">
		    <ul class="m-userMoneyNav">
			    <li style="float: left;height: 41px;overflow: hidden;position: relative;width: 100%;"><a id="btnConsumption" href="javascript:;"><b>佣金明细</b></a></li>
		    </ul>
		    
		    <ul id="ulConsumption" class="m-userMoneylst m-Consumption">
		    </ul>
		    <div id="divLoad" class="loading"><b></b>正在加载</div>
		    <a id="btnLoadMore" class="loading" href="javascript:;" style="display:none;">点击加载更多</a>
	    </article>
    </section>
    <script language="javascript" type="text/javascript" src="/js/commissionquery.js?data=20131121"></script>
</body>
</html>
