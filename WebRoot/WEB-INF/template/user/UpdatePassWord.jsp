<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>修改密码_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
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
	<!-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/index.html"  class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>个人设置</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> -->
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>个人设置</h1>
	</div>
	
	<section>
		<input type="hidden" value="${userId }" id="userId"/>
		<div class="secrityBg update-pwd clearfix">
	    	<dl>
	        	<dt class="gray3">账户名： <c:choose>  
									<c:when test="${user.phone!=null}">
										${user.phone }
										<c:choose>
											<c:when test="${user.mobileCheck==0}">(已验证)</c:when>
											<c:otherwise>(未验证)</c:otherwise>
										</c:choose>
									</c:when>
								</c:choose></dt>
	            <dd><input id="txtOldPwd" type="password" maxlength="20" placeholder="请输入原密码"><em>当前密码</em></dd>
	            <dd><input id="txtNewPwd" type="password" maxlength="20" placeholder="8-20位字母,数字或符号两种或以上组合"><em>新密码</em></dd>
	            <dd><input id="txtConNewPwd" type="password" maxlength="20" placeholder="重复输入新密码"><em>确认新密码</em></dd>				
	        </dl>
	        <p><a id="btnSubmit" href="javascript:;" class="orgBtn">保 存</a></p>
	        <span><a href="/Passport/FindPassword.html">忘记登录密码？</a></span>
	    	
	    </div>
	</section>

</div>
  <%-- <div class="main-content clearfix">
	<div class="R-content">
	<input type="hidden" value="${userId }" id="userId" name="userId" />
            <div class="subMenu">
                <a title="个人资料" href="/user/MemberModify.html">个人资料</a>
	            <a class="current" title="密码修改" href="/user/UpdatePassWord.html">密码修改</a>
            </div>
            <div class="pwdCon">
                <table cellspacing="0" cellpadding="0" border="0" align="center">
                    <tbody><tr>
                        <td align="right" width="80"><label>原密码：</label></td>
                        <td><input type="password" maxlength="16" class="pwd" id="OldPass"></td>
                        <td id="lOldPass" width="120"></td>
                    </tr>
                    <tr>
                        <td align="right"><label>新密码：</label></td>
                        <td><input type="password" maxlength="16" class="pwd" id="NewPass"></td>
                        <td id="lNewPass">6-16位字符</td>
                    </tr>
                    <tr class="safety">
                        <td>&nbsp;</td>
                        <td><span>安全强度</span> <ul id="pwdStrength"><li class="gray04"></li></ul></td>
                        <td id="tdStrength">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right"><label>再次输入：</label></td>
                        <td><input type="password" maxlength="16" class="pwd" id="NewPassAgain"></td>
                        <td id="lNewPassAgain">确认密码</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="button" title="保存" value="保存" class="bluebut" id="btnSubmitSave"></td>
                        <td><span style="display:none;" id="SaveSubmitStatus">正在保存</span></td>
                    </tr>
                </tbody></table>
            </div>
        </div>
	</div> --%>
    <link type="text/css" rel="stylesheet" href="/css/pagedialog.css?data=20131121" />
    <script language="javascript" type="text/javascript" src="/js/updatepassword.js?data=20131121"></script>
    <script language="javascript" type="text/javascript" src="/js/pagedialog.js?data=20131121"></script>
</body>
</html>
