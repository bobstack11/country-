<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>微信支付_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> 一元<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %> <%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>网</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	//调用微信JS api 支付
	function jsApiCall()
	{
	    WeixinJSBridge.invoke(
	    'getBrandWCPayRequest',
	    ${jsApiParameters},//josn串
	     function (res)
	     {
	         WeixinJSBridge.log(res.err_msg);
	         if(res.err_msg=='get_brand_wcpay_request:ok'){
	        	//支付成功
	        	location.replace('/user/UserBuyList.html');
	         }
	         //alert("|"+res.err_code +"|"+ res.err_desc +"|"+ res.err_msg);
	      }
	     );
	}
	
	window.onload = function(){
		if (typeof WeixinJSBridge == "undefined"){
		    if( document.addEventListener ){
		        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
		    }else if (document.attachEvent){
		        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
		        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
		    }
		}else{
			jsApiCall();
		}
	};
     </script>
  </head>
  
  <body>
  <div class="h5-1yyg-v1" id="loadingPicBlock">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->


</div>
</body>
</html>
