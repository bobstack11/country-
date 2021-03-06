<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title><%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> 一元<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %> <%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>网</title>
    <meta content="app-id=518966501" name="apple-itunes-app" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link href="/css/goods.css" rel="stylesheet" type="text/css" />
    <script src="/js/buyrecord.js" language="javascript" type="text/javascript"></script>
  </head>
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>所有<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>记录</h2>
        <div class="head-r">
	        <a id="btnSort" href="javascript:;" class="z-sort"><i></i>排序<s class="z-SswOn"></s><s class="z-SswNt"></s></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>所有<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>记录</h1>
	</div>
	<div class="m-detail-menu" style="z-index: 9999">
	    <a id="moreBtn" style="margin-top: 6px" href="javascript:void(0)"><i class="ico ico-more"></i><i class="ico ico-dot" style="display:none"></i></a>
	    <div class="m-detail-menu-wrap" style="display:none">
	        <i class="ico ico-arrow ico-arrow-transGray"></i>
	        <ul class="m-detail-menu-list">
	            <li><a class="item" href="/mycart/index.html"><i class="ico ico-miniCart ico-miniCart-gray"></i>清单</a></li>
	            <li class="last"><a class="item" href="/"><i class="ico ico-home ico-home-light"></i>首页</a></li>
	        </ul>
	    </div>
	</div>

    <input name="hidCodeID" type="hidden" id="hidCodeID" value="${id }" />
    <input name="hidIsEnd" type="hidden" id="hidIsEnd" value="1" />

    <!-- 夺宝记录 -->
    <section id="buyRecordPage" class="goodsCon">
        <div id="divRecordList" class="recordCon z-minheight" style="display:none;">
            
        </div>
        <div id="divBuyLoading" class="loading"><b></b>正在加载</div>
        <a id="btnLoadMore" class="loading" href="javascript:;" style="display:none;">点击加载更多</a>
    </section>
    
</div>
</body>
</html>