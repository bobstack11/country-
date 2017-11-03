<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="index_template" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> <%=ApplicationListenerImpl.sysConfigureJson.getSaitTitle() %></title>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" name="viewport" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <link href="/css/index.css?v=151106" rel="stylesheet" type="text/css" />
    <script id="pageJS" data="/js/index.js" language="javascript" type="text/javascript"></script>
</head>
<body fnav="3">
	<!-- 头部 -->
    <div class="m-header">
        <a class="m-header-logo-link" href="/">
            &nbsp;<i class="ico ico-logo"></i>
        </a>
        <div class="m-header-toolbar">
            <!-- <a class="m-header-toolbar-btn searchBtn" href="#" target="_self" title="搜索"><i class="ico ico-search"></i></a> -->
            <a class="m-header-toolbar-btn userpageBtn" href="/user/index.html" title="我的<%=ApplicationListenerImpl.sysConfigureJson.getShortName()%>"><i class="ico ico-userpage"></i></a>
        </div>
        <!-- <div class="g-wrap">
            <h1 class="m-header-logo">一元夺宝<a class="m-header-logo-link" href="http://m.1.163.com/"><i class="ico ico-logo"></i></a></h1>
            <div class="m-header-toolbar">
                <a class="m-header-toolbar-btn searchBtn" href="http://m.1.163.com/query.do" target="_self" title="搜索"><i class="ico ico-search"></i></a>
                <a class="m-header-toolbar-btn userpageBtn" href="http://m.1.163.com/user/index.do" title="我的夺宝"><i class="ico ico-userpage"></i></a>
            </div>
        </div> -->
    </div>
    <div class="h5-1yyg-index" id="loadingPicBlock">
        <!--app广告-->
        <input name="hidAppType" type="hidden" id="hidAppType" value="0" />
        <div class="app-icon-wrapper">
            <div id="divDownApp" class="app-icon" style="display: none;">
                <a id="downLink" href="javascript:;" target="_blank">
                    <img src="http://mskin.1yyg.com/weixin/images/download-app.jpg" alt="一元夺宝" width="320" height="50" /></a>
                <a href="javascript:;" class="close"></a>
            </div>
        </div>
        <!-- 焦点图 -->
        <section id="sliderBox" class="hotimg">
            <div class="loading clearfix"><b></b>正在加载</div>
        </section>
		
		<!-- 最新揭晓 -->
    <section class="g-main">
	    <div class="m-tt1">
		    <h2 class="fl"><a href="/lottery/index.html">最新揭晓</a></h2>
		    <div class="fr u-more">
			    <a class="u-rs-m1" href="/lottery/index.html"><b class="z-arrow"></b></a>
		    </div>
	    </div>
	    <article class="h5-1yyg-w310 m-round m-lott-li" >
	    	<ul class="clearfix"  id="divLottery">
	    		<s:iterator var="latestlotterys" value="latestlotteryList">
	    			<li>
		                <a href="/lotteryDetail/${latestlotterys.spellbuyProductId }.html" class="u-lott-pic">
		                    <img src="/Images/pixel.gif" src2="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${latestlotterys.productImg }" border="0" alt="">
		                        <div class="pPurchase" style='display:none'>限购</div>
                          </a>
                        <p><em>获得者</em><span><a href="/u/${latestlotterys.userId }.html" class="blue z-user">${latestlotterys.buyUser}</a></span></p>
			        </li>
		        </s:iterator>
	    	</ul>
<!--	    <div id="154145" class="m-lott-conduct"><p class="z-lott-tt">(第2980期)小米（MIUI） 红米1S 3G手机 移动版 <b class="z-arrow"></b><span class="z-lott-time">揭晓倒计时<span class="minute">00</span>:<span class="second">17</span>:<span class="millisecond">7</span><span class="last">6</span></span></p></div>-->
<!--	    <div id="154415" class="m-lott-conduct"><p class="z-lott-tt">(第9855期)小米（MIUI）10400mAh移动电源<b class="z-arrow"></b><span class="z-lott-time">正在计算,请稍后...</span></p></div>-->
<!--	    <div id="130949" class="m-lott-conduct"><p onclick="location.href='/lottery/detail-130949.html'">恭喜<span class="z-user blue">不会中奖的命</span>获得 (第596期)闪迪（SanDisk）酷捷 (CZ51)16GB U盘 <b class="z-arrow"></b></p></div>-->
		    <%-- <ul class="clearfix">
		        <s:iterator var="latestlotterys" value="latestlotteryList">
			        <li>
		                <a class="u-lott-pic" href="/lotteryDetail/${latestlotterys.spellbuyProductId }.html">
		                	<img border="0" class="scrollLoading" data-url="<%=ApplicationListenerImpl.sysConfigureJson.getImgUrl()%>${latestlotterys.productImg }" src="/Images/pixel.gif" />
		                </a>
		                <span style="color:#fff; text-decoration:underline;">恭喜<a class="blue z-user" href="/u/${latestlotterys.userId }.html">${latestlotterys.buyUser}</a>获得</span>
			        </li>
		        </s:iterator>
		    </ul> --%>
	    </article>
    </section>
		
		
        <!--导航-->
        <input name="hidOrderFlag" type="hidden" id="hidOrderFlag" value="hot20" />
        <nav id="goodsNav" class="nav-wrapper">
            <div class="nav-inner">
                <ul id="ulOrder" class="nav-list clearfix">
				    <li order="hot20" class="current" ><a href="javascript:;"><span>人气</span></a></li>
                    <li order="surplus20"><a href="javascript:;"><span>即将揭晓</span></a></li>
                    <li order="date20"><a href="javascript:;"><span>最新</span></a></li>
                    <li type="price" order="priceAsc20"><a href="javascript:;"><span>价值</span></a></li>
                </ul>
            </div>
            <!--点击添加或移除current-->
            <div id="divSort" class="select-btn">
                <span class="select-icon">
                    <i></i><i></i><i></i><i></i>
                </span>
                分类
            </div>
            <!--分类-->
            <div class="select-total" style="display: none">
                <ul class="sort_list">
                    <li sortid="" class="all"><a href="javascript:;">全部分类</a></li>
                  <s:iterator var="protype" value="protypeList">
                    <li sortid="<s:property value='#protype.typeId'/>"><a href="javascript:;"><s:property value='#protype.typeName'/></a></li>
                  </s:iterator>
                    <!-- <li sortid="100" class="phone"><a href="javascript:;">手机数码</a></li>
                    <li sortid="106" class="computer"><a href="javascript:;">电脑办公</a></li>
                    <li sortid="104" class="device"><a href="javascript:;">家用电器</a></li>
                    <li sortid="2" class="makeup"><a href="javascript:;">化妆个护</a></li>
                    <li sortid="222" class="watches"><a href="javascript:;">钟表首饰</a></li>
                    <li sortid="397"><a href="javascript:;">食品饮料</a></li>
                    <li sortid="312" class="other"><a href="javascript:;">其他商品</a></li>
                    <li sortid="400" class="purchase"><a href="javascript:;">限购专区</a></li> -->
                </ul>
            </div>
        </nav>
        <!--商品列表-->
        <div class="goods-wrap marginB">
            <ul id="ulGoodsList" class="goods-list clearfix"></ul>
            <div class="loading clearfix"><b></b>正在加载</div>
        </div>
        <!--底部-->

		<div class="footnav">
		    <p class="m-ftA"><a href="/">触屏版</a>
		    <a href="<%=ApplicationListenerImpl.sysConfigureJson.getWwwUrl()%>">电脑版</a>
		    <%-- <a href="<%=ApplicationListenerImpl.sysConfigureJson.getWwwUrl()%>/app/mobile.html" target="_blank">下载客户端</a></p> --%>
		    <p class="grayc"><%=ApplicationListenerImpl.sysConfigureJson.getIcp()%></p>
		    <a id="btnTop" href="javascript:;" class="z-top" style="display:none;"><b class="z-arrow"></b></a>
		</div>
	
	</div>
        
<script language="javascript" type="text/javascript">
var $indexImg = ${indexImgList };
$(function () {
	$('#menu1').attr('class',"hover");
	    	});
</script>
</body>
</html>
