<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>编辑收货地址</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
  </head>
  
<body>
<div class="h5-1yyg-v1">
    
<!-- 栏目页面顶部 -->

<!-- 内页顶部 -->

    <!-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>编辑收货地址</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> -->
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>编辑收货地址</h1>
	</div>

    <input name="hideOrderID" type="hidden" id="hideOrderID" value="${id }" />
        <input name="hideContactID" type="hidden" id="hideContactID" value="${cid }" />
        <input type="hidden" value="${userId }" id="userId"/>
         <input name="hidIsActOrder" type="hidden" id="hidIsActOrder" value="0" />
    <section class="clearfix g-member g-address">
        <!-- 地址表单 -->
            <div class="m-address-form">
                <ul>
                    <li>
                        <dl>
                            <dt>收货人</dt>
                            <dd>
                                <input id="txtUserName" type="text" class="z-input" placeholder="输入姓名" /></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>联系电话</dt>
                            <dd>
                                <input id="txtMobile" type="text" class="z-input" placeholder="联系电话" /></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>省份</dt>
                            <dd>
                                <select id="selAreaA" class="z-select">
                                    <option value="-1">请选择所在省份</option>
                                </select><b class="z-arrow"></b></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>城市</dt>
                            <dd>
                                <select id="selAreaB" class="z-select">
                                    <option value="-1">请选择所在城市</option>
                                </select><b class="z-arrow"></b></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>地区</dt>
                            <dd>
                                <select id="selAreaC" class="z-select">
                                    <option value="-1">请选择所在地区</option>
                                </select><b class="z-arrow"></b></dd>
                        </dl>
                    </li>
                    <!-- <li>
                        <dl>
                            <dt>街道</dt>
                            <dd>
                                <select id="selAreaD" class="z-select">
                                    <option value="-1">请选择所在街道或乡镇</option>
                                </select><b class="z-arrow"></b>
                            </dd>
                        </dl>
                    </li> -->
                    <li>
                        <dl>
                            <dt>街道地址</dt>
                            <dd>
                                <textarea id="txtAddr" name="comment" rows="3" class="z-textarea" placeholder="详细地址必须为3-100字之间,不包含特殊字符"></textarea></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>邮政编码</dt>
                            <dd>
                                <input id="txtZip" type="text" class="z-input" maxlength="6" placeholder="邮政编码" /></dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt></dt>
                            <dd><b id="spDefult"><span class="z-pay-ment"></span>设为默认</b></dd>
                        </dl>
                    </li>
                 </ul>
                <div class="u-Btn">
                    <div class="u-Btn-li"><a href="javascript:;" id="btnCancel" class="z-CloseBtn">取 消</a></div>
                    <div class="u-Btn-li"><a href="javascript:;" id="btnSure" class="z-DefineBtn">确 定</a></div>
                </div>
            </div>
        </div>
        
<input id="hidPageType" type="hidden" value="-1" />


    </div>

    <script language="javascript" type="text/javascript" src="/js/addressedit.js?data=20131121"></script>
</body>
</html>
