<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>我的晒单_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
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

    <!-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/index.html"  class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>我的晒单</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> -->
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
    	<a href="/user/index.html" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>我的晒单</h1>
	</div>
<input type="hidden" value="${userId }" id="userId"/>
    <div class="g-snav m_listNav">
	    <div class="g-snav-lst z-sgl-crt"><a id="btnPost" href="javascript:;" class="gray9">已晒单</a><b></b></div>
	    <div class="g-snav-lst"><a id="btnUnPost" href="javascript:;" class="gray9">未晒单</a></div>
    </div>
    <section id="divPost" class="clearfix g-Single-lst z-minheight">
        <ul>
        </ul>
    </section>
    <section id="divUnPost" class="clearfix g-Single-lst z-minheight" style="display:none;">
        <ul>
        </ul>
    </section>
    <div id="divPostLoad" class="loading"><b></b>正在加载</div>
    <a id="btnLoadMore" class="loading" href="javascript:;" style="display:none;">点击加载更多</a>
    <script language="javascript" type="text/javascript" src="/js/postsinglelist.js?data=20131121"></script>
</body>
</html>
