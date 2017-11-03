<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>我的晒单_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
  	<script language="javascript" type="text/javascript" src="/js/postsingleadd.js?data=20131121"></script>
    <link rel="stylesheet" type="text/css" href="/css/frozen.css" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/postsingleadd.css?data=20131121" />
  </head>
  
<body ontouchstart>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    <!-- 内页顶部 -->
    <!-- <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>我的晒单</h2>
        <div class="head-r">
	        <a href="/list/a1.html" class="z-Home"></a>
        </div>
    </header> -->
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
    	<a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
		<h1>我的晒单</h1>
	</div>

    <section id="form">
	 <div class="demo-item">
         <div class="demo-block">
            <div class="ui-form ui-border-t">
                <div style="display: none;">
                     <iframe src="about:blank" scrolling="no" name="upFrame" id="upFrame"></iframe>
                </div>
                <input type="hidden" name="codeID" value="${id }" id="codeID" />
                <ul class="upload">
                    <div class="ui-form-item ui-form-item-textarea ui-border-b">
                        <label style=" font-size:16px; color:#706f6f">主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:</label>
                        <textarea style=" font-size:12px; color:#706f6f" cols="30" rows="1" class="title" name="title" value="" id="postTitle"></textarea>
                    </div>
                    <div class="ui-form-item ui-form-item-textarea ui-border-b">
                        <label style=" font-size:16px; color:#706f6f">感&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言:</label>
                        <textarea style=" font-size:12px; color:#706f6f" cols="30" rows="10" class="textarea" name="ads_code" id="postContent"></textarea>
                    </div>
                    <li><span class="pic"></span>
                         <div class="uppic">
                             <form enctype="multipart/form-data" target="upFrame" action="/user/PostSingleAddUpLoadImg.html" method="post" name="pageForm" id="pageForm">
                               <div class="upImgLoading hidden"><s></s>上传中</div>
                                <div class="localBtn">
                                    <div class="btn" id="upImgBtn"></div>
                                    <div class="btnFile" id="FileControl">
                                        <input type="file" style="cursor: pointer;" hidefocus="true" name="myFile" class="file" title="" accept="image/*" id="fuploadFile" />
                                    </div>
                                </div>
                             </form>
                             
                             <input type="hidden" name="hidPicAll" value="" id="hidPicAll" />
                             <br><br><div style="display: none;" id="ulPicBox" class="pic">
                             </div>
                         </div>
                    </li>
                <li class="value"><input type="button" value="确认提交" class="ui-btn-lg ui-btn-danger" id="but_ok"></li>
                <!-- <input type="submit" value="确认提交" class="ui-btn-lg ui-btn-danger" id="but_ok">
                <div class="ui-btn-wrap">
                <button class="ui-btn-lg ui-btn-danger active">
                    确定
                </button>
            </div>
            <div class="ui-btn-wrap">
                <button class="ui-btn-lg ui-btn-danger" disabled>
                    取消
                </button>
            </div> -->
               </ul>
            </div>
        </div>
    </div>
    </section>
    <div class="clear_process"></div>
    <link type="text/css" rel="stylesheet" href="/css/pagedialog.css" />
    <script language="javascript" type="text/javascript" src="/js/pagedialog.js"></script>
    <div class="pageDialogBG" id="pageDialogBG"></div>
	<div class="pageDialogBorder" id="pageDialogBorder"></div>
	<div class="pageDialog" id="pageDialog">
		<div class="pageDialogClose" id="pageDialogClose" title="关闭"></div>
		<div class="pageDialogMain" id="pageDialogMain">&nbsp;</div>
	</div>
</div>
</body>
</html>
