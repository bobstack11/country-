<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>安全设置_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/security.css?v=130726" />
  </head>
  
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    <input name="loadDataType" type="hidden" id="loadDataType" value="0" />
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="MemberModify.html" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>安全设置</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>安全设置</h1>
	</div>

	<section>
            <div class="security-con clearfix">
                <ul>
                    <li> 
                    <s:if test="(user.userPwd==user.weixinUnionId) or (user.userPwd==user.weixinOpenId) or (user.userPwd==user.webWxOpenId)">
                    	<a href="/getbackpwd/index.html">
                            <span><b class="member-icon"></b>登录密码</span>
                            <em>设置<i class="z-arrow"></i></em>
                        </a>
                    </s:if>
                    <s:else>
                    	<a href="UpdatePassWord.html">
                            <span><b class="member-icon"></b>登录密码</span>
                            <em>修改<i class="z-arrow"></i></em>
                        </a>
					</s:else>
                    </li>
                   <%--  <li>
                        <a href="PayPwdCheck.do"><span><b class="member-icon gth"></b>支付密码</span><em>未开启<i class="z-arrow"></i></em></a>
                    </li> 
                    <li>
                        <a id="a_smallpay" href="javascript:"><span><b class="member-icon gth"></b>小额免密码设置</span><em>未设置<i class="z-arrow"></i></em></a>
                    </li>--%>
                    <li>
                <c:choose>
                    <c:when test="${user.mobileCheck==0}">
                        <a href="MobileCheck.html"><span><b class="member-icon"></b>手机号绑定</span><em><c:if test="${user.phone!=null}">
							${fn:substring(user.phone,0,3)}****${fn:substring(user.phone,7,11)}
							</c:if><i class="z-arrow"></i></em></a>
					</c:when>
					<c:otherwise>
						<a href="MobileBind.html"><span><b class="member-icon"></b>手机号绑定</span><em><c:if test="${user.phone!=null}">
							${fn:substring(user.phone,0,3)}****${fn:substring(user.phone,7,11)}
							</c:if><i class="z-arrow"></i></em></a>
					</c:otherwise>
				</c:choose>
                    </li>
                    <li>
                        <a href="MemberModify.html"><span><b class="member-icon"></b>个人设置</span><em>编辑<i class="z-arrow"></i></em></a>
                    </li>
                    <%-- <li>
                        <a href="EmailCheck.do"><span><b class="member-icon"></b>邮箱绑定</span><em>se*@163.com<i class="z-arrow"></i></em></a>
                    </li> --%>
                    <%-- <li>
                        <a href="LoginRemindSet.do"><span><b class="member-icon gth"></b>登录保护</span><em>未开启<i class="z-arrow"></i></em></a>
                    </li> --%>
                </ul>
            </div>
        </section>



    <script language="javascript" type="text/javascript" src="/js/safesettings.js?data=20131121"></script>
</body>
</html>
