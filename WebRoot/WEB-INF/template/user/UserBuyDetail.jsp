<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>详情_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
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
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>详情</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>详情</h1>
	</div>

    <section class="clearfix g-Record-ct">
    	<c:choose>
    		<c:when test="${buyHistoryJSON.buyStatus==1}">
    			<a class="fl z-Limg" href="/lotteryDetail/${buyHistoryJSON.productId }.html"><span class="z-Imgbg z-ImgbgC02"></span><em class="z-Imgtxt">已揭晓...</em><img src="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${buyHistoryJSON.productImg }" border=0 /></a><div class="u-Rcd-r gray9"><p class="z-Rcd-tt"><a href="/lotteryDetail/${buyHistoryJSON.productId }.html" class="gray6">(第${buyHistoryJSON.productPeriod }期)${buyHistoryJSON.productName }</a></p><p>获得者：<em class="blue">${buyHistoryJSON.winUser }</em></p><p>揭晓时间：<em class="gray6">${buyHistoryJSON.winDate }</em></p></div>
    		</c:when>
    		<c:otherwise>
    			
		<a class="fl z-Limg" href="/products/${buyHistoryJSON.productId  }.html"><span class="z-Imgbg z-ImgbgC01"></span><em class="z-Imgtxt">进行中...</em><img src="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${buyHistoryJSON.productImg }" border=0 />
<!--		<div class="pPurchase">限购</div>-->
		</a><div class="u-Rcd-r gray9"><p class="z-Rcd-tt"><a href="/products/${buyHistoryJSON.productId }.html" class="gray6">(第${buyHistoryJSON.productPeriod }期)${buyHistoryJSON.productName }</a></p><div class="Progress-bar"><p class="u-progress" title="已完成<fmt:formatNumber type="number" value="${(buyHistoryJSON.spellbuyCount/buyHistoryJSON.productPrice)*100 } " maxFractionDigits="2"/> %"><span class="pgbar" style="width:<fmt:formatNumber type="number" value="${(buyHistoryJSON.spellbuyCount/buyHistoryJSON.productPrice)*100 } " maxFractionDigits="2"/>%;"><span class="pging"></span></span></p><ul class="Pro-bar-li"><li class="P-bar01"><em>${buyHistoryJSON.spellbuyCount }</em>已参与</li><li class="P-bar02"><em>${buyHistoryJSON.productPrice }</em>总需人次</li><li class="P-bar03"><em>${buyHistoryJSON.productPrice- buyHistoryJSON.spellbuyCount}</em>剩余</li></ul></div></div>
    		</c:otherwise>
    	</c:choose>
    </section>
    <section class="clearfix g-member g-Record-ctlst">
	    <b class="z-arrow"></b>
	    <article class="m-round">
		    <h3>本期商品您总共拥有<em class="orange">${resultCount }</em>个<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>码</h3>
		    <ul>
		    	<s:iterator value="randomNumberJSONList" var="randomNumberJSONs">
		    		<li><p class="gray9">${randomNumberJSONs.buyDate}<span>${randomNumberJSONs.buyCount }人次</span></p>${randomNumberJSONs.randomNumbers }</li>
		    	</s:iterator>
		    </ul>
	    </article> 
    </section>
    <script type="text/javascript" src="/js/userbuydetail.js"></script>
</body>
</html>
