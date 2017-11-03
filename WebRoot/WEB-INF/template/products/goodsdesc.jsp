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
    <script type="text/javascript">
        /*
        * 智能机浏览器版本信息:
        *
        */
        var browser = {
            versions: function () {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {//移动终端浏览器版本信息 
                    trident: u.indexOf('Trident') > -1, //IE内核
                    presto: u.indexOf('Presto') > -1, //opera内核
                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                    iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                    iPad: u.indexOf('iPad') > -1, //是否iPad
                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                };
            }(),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        }
        var _swidth = 0;
        var _dwidth = 0;
        var _gheight = 0;
        var setZoomFun = function (isResize) {
            var _goodsdesc = $("#divGoodsDesc").show();
            var _hwidth = _goodsdesc.width();//$("header.g-header").width();
            if (_hwidth == _swidth) { return; }
            _swidth = _hwidth;//window.screen.width;
            if (_dwidth == 0) {
                _dwidth = $(document).width();
            }
            if (_gheight == 0) {
                _gheight = _goodsdesc.height();
            }
            if (!isResize) {
                _goodsdesc.find("img").each(function () {
                    var E = "src2";
                    var H = $(this).attr(E);
                    $(this).attr("src", H).removeAttr(E).show();

                });
            }
            var _zoom = parseFloat(_swidth / _dwidth);
            if (_zoom >= 1 || _zoom <= 0) {
                return;
            }
            // document.title = _zoom;
            if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
                _goodsdesc.css("-webkit-transform-origin", "left top");
                _goodsdesc.css("-moz-transform-origin", "left top");
                _goodsdesc.css("-o-transform-origin", "left top");

                _goodsdesc.css("-webkit-transform", "scale(" + _zoom + ")");
                _goodsdesc.css("-moz-transform", "scale(" + _zoom + ")");
                _goodsdesc.css("-o-transform", "scale(" + _zoom + ")");

                _goodsdesc.css("height", _gheight * _zoom + "px");

            } else {
                _goodsdesc.css("zoom", _zoom);
            }
        }

        $(document).ready(function () {
            setZoomFun(false);
        });

        $(window).resize(function () {
            setZoomFun(true);
        });


    </script>


  </head>
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    
<!-- 栏目页面顶部 -->
<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>图文详情</h2>
        <div class="head-r">
	        <a id="btnCart" href="/mycart/index.html" class="z-shop"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>图文详情</h1>
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

    <div id="divGoodsDesc" class="detailContent z-minheight" style="display: none;">
    	<div style="width:800px; margin:0px auto; padding-top:10px;">
	    	<div>
			${productInfo.productDetail2}
			</div>
		</div>
    </div>
    
</div>
</body>
</html>