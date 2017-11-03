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
	<link href="/css/layoutcommodity.css?data=150930" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/css/layoutwap.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/layoutsetup.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/header.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
	<link href="/css/dateinput.css?data=20131121" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	min-width: 100%;
}
.info {
	clear: both;
	margin: 2px 0 0 5px;
}
.order_shipAddr .Remark textarea {
	width:250px;
}
.order_shipAddr .addressBox ul {
	padding-left:3px;
}
.addressBox .addAddress .form {
	padding-left:2px;
}
.addressBox .addAddress .form label {
	width:60px;
}
.addAddress .go {
	margin: 10px 0 5px 55px!important;
}
.order_shipAddr {
	width: 88%;
	margin: 7px 7px 7px 7px;
	padding: 10px 10px;
}
.order_shipAddr h5 {
	padding-gglweishi
}
</style>
</head>
<body>
<div class="main-content clearfix">
	<div class="R-content">
	
	<input type="hidden" value="${id }" id="hidOrderNO" name="hidOrderNO" />
	<input type="hidden" value="${userId }" id="userId"/>
    <div class="info">
         <div class="clearfix g-adrsnav">
			<div class="m-adrs-lst<c:if test='${latestlottery.status==1}'> m-adrs-lstSel</c:if>">
				<em>确认收货地址</em><i><s></s></i><b class="z-arrow"></b>
			</div>
			<div class="m-adrs-lst<c:if test='${latestlottery.status==2}'> m-adrs-lstSel</c:if>">
				<em>商家发货</em><i><s></s></i><b class="z-arrow"></b>
			</div>
			<div class="m-adrs-lst<c:if test='${latestlottery.status==3}'> m-adrs-lstSel</c:if>">
				<em>确认收货</em><i><s></s></i><b class="z-arrow"></b>
			</div>
			<div class="m-adrs-lst<c:if test='${latestlottery.status==4}'> m-adrs-lstSel</c:if>">
				<em>晒单分享</em><i><s></s></i><b class="z-arrow"></b>
			</div>
		</div>
        <div class="clearfix g-member g-address marginB">
			<c:if test="${latestlottery.status==1}">
            <div class="clearfix m-round m-address-ct">
                <!-- 地址列表 -->
				<div class="order_shipAddr">
					<h5>确认收货地址</h5>
					<div class="addressBox" id="addressBox">
					<ul>
						<s:iterator value="userbyaddressList" var="Adds" status="index">
        				<s:if test="#index.first">
        				<li>
							<input type="radio" checked="checked" value="${Adds.id }" id="addrId${Adds.id }" name="selectAddrID" />
							<label for="addrId${Adds.id }">
								${Adds.province }&nbsp;${Adds.city }&nbsp;${Adds.district }&nbsp;${Adds.address }
								<em>(收货人：${Adds.consignee }&nbsp;${Adds.phone })</em>
							</label>
	           			</li>
        				</s:if>
        				<s:else>
        	 			<li >
							<input type="radio" value="${Adds.id }" id="addrId${Adds.id }" name="selectAddrID" />
							<label for="addrId${Adds.id }">
								${Adds.province }&nbsp;${Adds.city }&nbsp;${Adds.district }&nbsp;${Adds.address }
								<em>(收货人：${Adds.consignee }&nbsp;${Adds.phone })</em>
							</label>
	           			</li>
        				</s:else>
						</s:iterator>
						<c:if test="${fn:length(userbyaddressList)<4}">
	        			<li id="liAddAddrTools">
							<input type="radio" value="" id="addrIDOther"  />
							<label for="addrIDOther" id="lblNewAddrTitle1">使用其它地址</label>
						</li>
						<li class="addAddress" id="liAddAddrBox">
							<div class="container">
								<input type="radio" checked="checked" id="radNewAddress" name="newAddress" value="" />
								<label id="lblNewAddrTitle2">使用其它地址</label>
								<div class="msg max-notice" id="msgAddrOver">
									<p class="attention">地址库已满，新地址为临时地址，无法保存入库，但本次可用</p>
								</div>
								<div class="form">
								<ul>
	                            <li>
	                                <label>省市区：</label>
	                                <span>
	                                    <ul class="addr-dist">
	                                        <li>
	                                            <select id="shipAreaA" name="shipAreaA">
	                                            </select>
	                                        </li>
	                                        <li>
	                                            <select id="shipAreaB" name="shipAreaB">
	                                            </select></li>
	                                        <li>
	                                            <select id="shipAreaC" name="shipAreaC">
	                                            </select></li>
	                                    </ul>
	                                    <div class="msg" id="shipAreaValidMsg">
	                                    </div>
	                                </span></li>
	                            <li>
	                                <label for="addrMail">街道地址：</label>
	                                <span>
	                                    <input type="text" style="width: 160px;" class="text" maxlength="100" size="100" id="shipAddress" name="shipAddress" />&nbsp;
	                                    <div class="msg" id="shipAddressValidMsg">
	                                    </div>
	                                </span></li>
	                            <li>
	                                <label>邮政编码：</label>
	                                <span>
	                                    <input type="text" class="text" id="shipZip" maxlength="6" size="20" name="shipZip" />
	                                    <div class="msg" id="shipZipValidMsg">
	                                    </div>
	                                </span></li>
	                            <li>
	                                <label for="addrName">收货人：</label>
	                                <span>
	                                    <input type="text" class="text" maxlength="20" size="20" id="shipName" name="shipName" />&nbsp;
	                                    <div class="msg" id="shipNameValidMsg">
	                                    </div>
	                                </span></li>
	                            <li>
	                                <label>手机号码：</label>
	                                <span>
	                                    <input type="text" class="text" maxlength="20" size="20" name="shipMobile" id="shipMobile" />
	                                    <div class="msg" id="shipMobileValidMsg">
	                                    </div>
	                                </span>
								</li>
								</ul>
								<div class="go">
									<input type="button" value="保 存" id="btn_consignee_save" class="bluebut" />
									<input type="button" value="取 消" id="btn_consignee_cancle" class="cancelBtn" />
								</div>
								</div>
							</div>
						</li>
					</ul>
				</c:if>
			</div>
			<!--配送方式-->
			<h5>确认配送时间<span>请选择送货上门的时间</span></h5>
			<div class="express" id="express">
				<ul id="ulExpress">
				<li>
					<input type="radio" checked="checked" value="1" id="shipType1" name="shipType" />
					<label for="shipType1">普通快递送货上门</label>
				</li>
				</ul>
				<ul>
					<li class="time">
					<select id="shipTime" name="shipTime">
						<option value="周一至周五">周一至周五</option>
						<option value="周六及公众假期">周六及公众假期</option>
						<option selected="selected" value="时间不限">时间不限</option>
					</select>
					</li>
				</ul>
			</div>
			<!--订单备注-->
			<h5>添加订单备注<span>请备注与订单相关的信息，如商品为手机充值卡可以填写要充值的手机号码等</span></h5>
			<div class="Remark" id="notes">
				<textarea rows="3" cols="100" id="shipRemark" name="shipRemark"></textarea>
				<div class="msg" id="shipRemarkValidMsg">
				</div>
			</div>
			<div class="order_go"><input type="button" value="确认提交配送信息" id="btnSubmitCart" class="orangebut" /></div>
		</div>
		</div>
		</c:if>
		<div class="clearfix m-round m-address-ct">
                <!-- 订单处理步骤 -->
                <ul>
					<li>
						<p class="gray9">
							<span class="fr">
								操作：<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%>
							</span>
							${fn:substring(latestlottery.announcedTime,0,16)}
						</p>
						恭喜您<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>成功，请尽快确认收货地址，以便我们为您配送！
					</li>
					<s:iterator value="orderdetailList" var="orderdetails">
					<li>
						<p class="gray9">
							<span class="fr">操作：${orderdetails.userName}</span>${orderdetails.date}
						</p>
						${orderdetails.detailText}
					</li>
					</s:iterator>
				</ul>
				<c:if test="${latestlottery.status==3}">
				<div class="u-Btn" style="display:block;float:left;">
					<div class="u-Btn-li">
						<input type="button" value="确认收货" name="btnReceived" class="orangebut" />
					</div>
				</div>
				</c:if>
				<c:if test="${latestlottery.status==4}">
				<div class="u-Btn" style="display:block;float:left;">
					<div class="u-Btn-li">
						<input type="button" onclick="javascript:location.href='/user/PostSingleAdd-${id }.html';" value="立即晒单" class="orangebut" />
					</div>
				</div>
				</c:if>
            </div>
            <!-- 商品信息 -->
            <div class="clearfix mt10 m-round g-goods-info">
                <h3>配送信息</h3>
                <ul id="loadingPicBlock">
                    <li>
						<a class="fl z-Limg" 
							href="/products/${latestlottery.spellbuyProductId }.html">
							<img alt="${latestlottery.productTitle }" 
								src="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${latestlottery.productImg }" />
                        </a>
						<div class="u-gds-r gray9">
							<p class="z-gds-tt">
								<a href="/products/${latestlottery.spellbuyProductId }.html" class="gray6">
									(第${latestlottery.productPeriod }期)${latestlottery.productName}
								</a>
							</p>
							<p>价值：￥${latestlottery.productPrice}</p>
							<p>幸运夺宝码：<em class="orange">${latestlottery.randomNumber}</em></p>
							<p>揭晓时间：${latestlottery.announcedTime}</p>
						</div>
					</li>
				</ul>
				
                <!-- 快递信息 -->
                <c:if test="${latestlottery.status!=1}">
                <!-- 收货地址信息 -->
                <div class="m-Express">
					<p>收货地址</p>
					<p class="f-addressLH gray6">
						<span>${orderdetailaddress.address}</span>
						<span>${orderdetailaddress.consignee} ${orderdetailaddress.phone}</span>
					</p>
				</div>
				</c:if>
            </div>
        </div>
    </div>
	<div class="clear_process"></div>
    <link type="text/css" rel="stylesheet" href="/css/pagedialog.css" />
	<script src="/js/comm.js"></script>
    <script src="/js/orderdetail.js"></script>
    <script src="/js/order-pagedialog.js"></script>
    <div class="pageDialogBG" id="pageDialogBG"></div>
	<div class="pageDialogBorder" id="pageDialogBorder"></div>
	<div class="pageDialog" id="pageDialog">
		<div class="pageDialogClose" id="pageDialogClose" title="关闭"></div>
		<div class="pageDialogMain" id="pageDialogMain">&nbsp;</div>
	</div>
</body>
</html>