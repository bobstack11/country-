<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta name="decorator" content="index_template" />
		<title>晒单分享_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> 一元<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %> <%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>网</title>
		<meta content="app-id=518966501" name="apple-itunes-app" />     
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
	    <meta content="yes" name="apple-mobile-web-app-capable" />     
	    <meta content="black" name="apple-mobile-web-app-status-bar-style" />     
	    <meta content="telephone=no" name="format-detection" />
	    <link href="/css/single.css" rel="stylesheet" type="text/css" />
	    <script src="/js/postlist.js" language="javascript" type="text/javascript"></script>
	</head>

<body>
<div class="h5-1yyg-v1">
    
<!-- 栏目页面顶部 -->
    <%-- <header class="header">
        <h1 class="fl"><span>一元夺宝</span><a href="/"></a></h1>
        <div class="fl u-slogan"></div>
        <div class="fr head-r">
            <a href="/user/index.html" class="z-Member"></a>
            <a id="btnCart" href="/mycart/index.html" class="z-shop"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <!-- <a href="javascript:void(0);" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a> -->
	    <h1>晒单</h1>
	</div>
	<!-- <div class="m-detail-menu" style="z-index: 9999">
	    <a id="moreBtn" style="margin-top: 6px" href="javascript:void(0)"><i class="ico ico-more"></i><i class="ico ico-dot" style="display:none"></i></a>
	    <div class="m-detail-menu-wrap" style="display:none">
	        <i class="ico ico-arrow ico-arrow-transGray"></i>
	        <ul class="m-detail-menu-list">
	            <li><a class="item" href="/mycart/index.html"><i class="ico ico-miniCart ico-miniCart-gray"></i>清单</a></li>
	            <li class="last"><a class="item" href="/"><i class="ico ico-home ico-home-light"></i>首页</a></li>
	        </ul>
	    </div>
	</div> -->
	
    <!-- 栏目导航 
    <nav class="g-snav u-nav">
	    <div class="g-snav-lst"><a href="/" >首页</a></div>
	    <div class="g-snav-lst"><a href="/list/hot20.html" >所有商品</a></div>
	    <div class="g-snav-lst"><a href="/lottery/index.html" >最新揭晓</a></div>
	    <div class="g-snav-lst"><a href="/share/new20.html" class="nav-crt">晒单</a> <s class="z-arrowh"></s></div>
    </nav>
-->

<!-- 内页顶部 -->

    
    <div id="navBox" class="g-snav m_listNav">
        <div class="g-snav-lst z-sgl-crt"><a href="javascript:;" class="gray9">最新晒单</a><b></b></div>
        <div class="g-snav-lst"><a href="javascript:;" class="gray9">人气晒单</a><b></b></div>
        <div class="g-snav-lst"><a href="javascript:;" class="gray9">评论最多</a></div>
    </div>

    <!-- 晒单列表 -->
    <section>	
		<div class="cSingleCon" id="loadingPicBlock">
		    <div id="postBox10" class="cSingleCon2"></div>
            <div id="postBox20" class="cSingleCon2" style="display:none;"></div>
            <div id="postBox30" class="cSingleCon2" style="display:none;"></div>
        </div>
		<div id="postLoading" class="loading loading2"><b></b>正在加载</div>
        <a id="btnLoadMore" class="loading" href="javascript:;" style="display:none;">点击加载更多</a>
    </section>
    
</div>
  <script language="javascript" type="text/javascript">
    $(function () {
$('#menu3').attr('class',"hover");
    	});
</script>
</body>
</html>
