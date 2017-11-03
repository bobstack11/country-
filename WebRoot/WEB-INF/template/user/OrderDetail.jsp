<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="index_template" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
	<title>订单详情</title>
	<meta content="app-id=518966501" name="apple-itunes-app" />
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
</head>
<body>
<div class="h5-1yyg-v1">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/OrderList.html" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>订单进度</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="/user/OrderList.html" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>订单进度</h1>
	</div>

    <input name="hideOrderID" type="hidden" id="hideOrderID" value="${id }" />
    <section class="clearfix g-adrsnav"><div class="m-adrs-lst<c:if test='${latestlottery.status==1}'> m-adrs-lstSel</c:if>"><em>确认收货地址</em><i><s></s></i><b class="z-arrow"></b></div><div class="m-adrs-lst<c:if test='${latestlottery.status==2}'> m-adrs-lstSel</c:if>"><em>商家发货</em><i><s></s></i><b class="z-arrow"></b></div><div class="m-adrs-lst<c:if test='${latestlottery.status==3}'> m-adrs-lstSel</c:if>"><em>确认收货</em><i><s></s></i><b class="z-arrow"></b></div></section>
    <section class="clearfix g-member g-address">
        <!-- 地址列表 -->
    	<c:if test="${latestlottery.status==1}">
        
	        <article id="addrListBox" class="clearfix m-round m-address-ct">
	            <ul id="ulAddress">
	            	<s:iterator value="userbyaddressList" var="Adds" status="index">
	                <li id="${Adds.id }">
	                	<c:if test="${Adds.status==1}">
			            <i class="fl z-bank-Roundsel"><s></s></i>
			            </c:if>
			            <c:if test="${Adds.status!=1}">
			            <i class="fl z-bank-Round"><s></s></i>
			            </c:if>
			            <p class="z-addrs-r">
			                <b>${Adds.consignee }</b><b>${Adds.phone }</b><b>邮编：${Adds.zipCode }</b>
			                <span>${Adds.province } ${Adds.city } ${Adds.district } ${Adds.address }</span>
			            </p>
			        </li>
			        </s:iterator>
			    </ul>
		        <div class="u-Btn">
			        <div class="u-Btn-li"><a id="btnAddAddr" href="addressadd-${id }.html" class="z-CloseBtn">填写新地址</a></div><div class="u-Btn-li"><a id="btnConfimAddr" href="javascript:;" class="z-DefineBtn">确认地址</a></div>
		        </div>
	        </article>
	    </c:if>
	    <!-- 订单处理步骤 -->
	    
	    	<article class="clearfix m-round m-address-rst">
		        <ul>
		        <c:if test="${latestlottery.status > 1}">
			        <li>
			            <p class="gray9">
			                <span class="fr">操作：<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></span>
			                ${fn:substring(latestlottery.announcedTime,0,16)}
			            </p>
			            恭喜您<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>成功，请尽快填写收货地址，以便我们为您配送！
			        </li>
		        	<s:iterator value="orderdetailList" var="orderdetails">
			        <li>
			            <p class="gray9">
			                <span class="fr">操作：${orderdetails.userName}</span>
			                ${orderdetails.date}
			            </p>
			            ${orderdetails.detailText}
			 <!--            会员已填写配送地址信息，等待商城发货！ -->
			        </li>
		        	</s:iterator>
		        </c:if>
		        <c:if test="${latestlottery.status==3}">
		        	<li>
			            <p class="gray9">
			                <span class="fr">操作：<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></span>
			                ${orderdetailaddress.deliverTime}
			            </p>
			            您的商品由${orderdetailaddress.expressCompany}配送，请留意收货[${orderdetailaddress.expressCompany}订单号：${orderdetailaddress.expressNo}]。
			        </li>
		        </c:if>
		        </ul>
		        <c:if test="${latestlottery.status==2}">
		        <div class="u-Btn"><div class="u-Btn-li"><a  href="javascript:;" class="orgBtn z-grayBtn">备货中</a></div></div>
		        </c:if>
		        <c:if test="${latestlottery.status==3}">
		        <div class="u-Btn"><div class="u-Btn-li"><a id="btnShiped" cid="2431226" href="javascript:;" class="orgBtn">确认收货</a></div></div>
				</c:if>
				<c:if test="${latestlottery.status==4}">
				<div class="u-Btn" ><div class="u-Btn-li"><a href="/user/PostSingleAdd-${id }.html" class="orgBtn" >去晒单</a></div></div>
				</c:if>
	        </article>
	    	
	    
	    <article class="clearfix mt10 m-round g-goods-info">
		    <h3>商品信息</h3>
		    
		        <ul id="loadingPicBlock">
			        <li>
				        <a class="fl z-Limg" href="/products/${latestlottery.spellbuyProductId }.html"><img src="/Images/loading.gif" src2="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${latestlottery.productImg }" border=0 alt="">
                              
				        </a>
				        <div class="u-gds-r gray9">
					        <p class="z-gds-tt"><a href="/products/${latestlottery.spellbuyProductId }.html" class="gray6">(第${latestlottery.productPeriod }期)${latestlottery.productName}</a></p>
					        <p>价值:￥${latestlottery.productPrice}</p>
					        <p>幸运夺宝码：<em class="orange">${latestlottery.randomNumber}</em></p>
					        <p>揭晓时间：${latestlottery.announcedTime}</p>
				        </div>
			        </li>
		        </ul>
		<c:if test="${latestlottery.status!=1}">
		    <div class='useInfo'><p>收货信息</p>${orderdetailaddress.consignee} ${orderdetailaddress.phone}<br/>${orderdetailaddress.address}</div>
		</c:if>
		    <div class="g-pay-bline"></div>
	    </article> 
    </section>

    <script src="/js/orderdetail.js"></script>
  
</body>
</html>