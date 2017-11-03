<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
  </head>
  
<body>
 <div class="h5-1yyg-v11">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

 <!--   <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>我的<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %></h2>
        <div class="head-r">
	        <a href="/" class="z-Home"></a>
        </div>
    </header>
  -->
    <section class="clearfix g-member">
	    <div class="clearfix m-round m-name"><div class="fl f-Himg"><a href="/u/${user.userId }.html" class="z-Himg">
	    <c:choose>
	    	<c:when test="${fn:indexOf(user.faceImg,'http')!=-1}">
	    		 <img src="${user.faceImg }" border=0 />
	    	</c:when>
	    	<c:otherwise>
	    		 <img src="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl() %>${user.faceImg }" border=0 />
	    	</c:otherwise>
	    </c:choose>
	    </a>
	    
	    </div>
	    <div class="m-name-info"><p class="u-name"><b class="z-name gray01">${user.userName }</b><em>(
	    <s:if test="user.phone!=null">
	        ${user.phone }
        </s:if>
        <s:else>
        	 ${user.mail }
        </s:else>)</em></p>
		<s:if test="user.experience<10000">
            <span class="z-class-icon01 gray02"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>小将</span>
           </s:if>
           <s:elseif test="user.experience<50000">
           	<span class="z-class-icon02"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>少将</span>
           </s:elseif>
           <s:elseif test="user.experience<100000">
           	<span class="z-class-icon03"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>中将</span>
           </s:elseif>
           <s:elseif test="user.experience<500000">
           	<span class="z-class-icon04"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>上将</span>
           </s:elseif>
           <s:elseif test="user.experience<1000000">
           	<span class="z-class-icon05"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>少校</span>
           </s:elseif>
           <s:elseif test="user.experience<2000000">
           	<span class="z-class-icon06"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>中校</span>
           </s:elseif>
           <s:elseif test="user.experience<5000000">
           	<span class="z-class-icon07"><s></s><%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>上校</span>
           </s:elseif>
		   经验值 <span class="orange2">${user.experience }</span>
		</div>
		</div>
		<ul class="clearfix u-mbr-info">
	    <li><a href="/user/MemberPoints.html"><span class="orange3">${user.commissionPoints }</span>可用积分 </a></li>
	    <li><a href="/user/UserBalance.html"><span class="orange3">￥${user.balance}</span>余额  </a></li>
		<a href="/user/UserRecharge.html" class="fr z-Recharge-btn">去充值</a>
		</ul>
	    <div class="m-round m-member-nav">
		    <ul id="ulFun">
			    <li><a href="/user/UserBuyList.html"><b class="z-arrow"></b><s2 class="m_s1"></s2>我的<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>记录</a></li>
			    <li><a href="/user/OrderList.html"><b class="z-arrow"></b><s2 class="m_s2"></s2>获得的商品</a></li>
               <!-- <li><a href="/user/MemberPoints.html"><b class="z-arrow"></b>我的积分</a></li>               
                <li><a href="/user/CommissionQuery.html"><b class="z-arrow"></b>佣金明细</a></li>
				<li><a href="/user/UserBalance.html"><b class="z-arrow"></b>帐户明细</a></li>-->
			    <li><a href="/user/PostSingleList.html"><b class="z-arrow"></b><s2 class="m_s3"></s2>我的晒单</a></li>
				<li><a href="/user/MemberPoints.html"><b class="z-arrow"></b><s2 class="m_s4"></s2>我的积分</a></li> 
				<li><a href="/user/ShareList.html"><b class="z-arrow"></b><s2 class="m_s5"></s2>分享赚钱 <span class="orange8"><%=ApplicationListenerImpl.sysConfigureJson.getInvite()%>积分和<%=ApplicationListenerImpl.sysConfigureJson.getCommission()*100%>%现金奖励等你来拿！</span></a></li>
				<li><a href="/help/index.html"><b class="z-arrow"></b><s2 class="m_s6"></s2>帮助中心</a></li> 
				<li><a href="/user/AddressList.html"><b class="z-arrow"></b><s2 class="m_s7"></s2>收货地址管理</a></li>
			    <li><a href="/user/SafeSettings.html"><b class="z-arrow"></b><s2 class="m_s8"></s2>安全设置</a></li>
				<li><a href="/logout/index.html"><b class="z-arrow"></b><s2 class="m_s9"></s2>退出登录</a></li>
		    </ul>
	    </div>
    </section>
</div>
  <script language="javascript" type="text/javascript">
    $(function () {
$('#menu4').attr('class',"hover");
    	});
</script>
</body>

</html>
