<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="d"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title><d:title default="591jx.net" />
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" href="/favicon.ico" />
		<meta name="description" content="<%=ApplicationListenerImpl.sysConfigureJson.getDescription()%>" />
		<meta name="keywords" content="<%=ApplicationListenerImpl.sysConfigureJson.getKeyword()%>" />
	    <link href="/css/comm.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery190.js" language="javascript" type="text/javascript"></script>
		<d:head />
		<script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?8908e7d379072384d89956cb4c06d330";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
		</script>
	</head>

	<body <d:getProperty property="body.id" writeEntireProperty="true" /><d:getProperty property="body.class" writeEntireProperty="true" /><d:getProperty property="body.rf" writeEntireProperty="true" /> >
		<d:body />
   <!--底部-->
 <footer class="footer">
	<div class="u-ft-nav" id="fLoginInfo">
	</div>
	
    <p class="m-ftA"><a href="/">触屏版</a>
    <a href="<%=ApplicationListenerImpl.sysConfigureJson.getWwwUrl()%>">电脑版</a>
    <a href="<%=ApplicationListenerImpl.sysConfigureJson.getWwwUrl()%>/app/mobile.html" target="_blank">下载客户端</a></p>
    <p class="grayc"><%=ApplicationListenerImpl.sysConfigureJson.getIcp()%></p>
    <a id="btnTop" href="javascript:;" class="z-top" style="display:none;"><b class="z-arrow"></b></a>
	
</footer>
<script type="text/javascript">
var $img = "<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>";
var $skin = "<%=ApplicationListenerImpl.sysConfigureJson.getSkinUrl()%>";
var $www = "<%=ApplicationListenerImpl.sysConfigureJson.getWeixinUrl()%>";
var $domain = "<%=ApplicationListenerImpl.sysConfigureJson.getDomain()%>";
var $saitName = "<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%>";
var $shortName = "<%=ApplicationListenerImpl.sysConfigureJson.getShortName()%>";
</script>
<script language="javascript" type="text/javascript">
function GetVerNum(){var D=new Date();return D.getFullYear().toString().substring(2,4)+'.'+(D.getMonth()+1)+'.'+D.getDate()+'.'+D.getHours()+'.'+(D.getMinutes()<10?'0':D.getMinutes().toString().substring(0,1))}
$.getScript('/js/bottom.js?v='+GetVerNum());
</script>
</body>
</html>
