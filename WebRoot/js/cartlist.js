$(function(){var a=$("#cartBody");var c=$("#divNone");var forward=$("#forward").val();var b=function(){var o="";var h=$("#divTopMoney");var g=$("#divBtmMoney");var e=function(t,s,r,q){$.PageDialog.fail(t,s,r,q)};var n=function(s,r,q){$.PageDialog.confirm(s,r,q)};if(h.length>0){h.children("a").click(function(){location.href="/mycart/payment.html?forward="+forward})}g.children("a").click(function(){location.href="/mycart/payment.html?forward="+forward});var m=function(){var q=0;var r=0;$("input:text[name=num]",a).each(function(s){var t=parseInt($(this).val());if(!isNaN(t)){r++;q+=t}});if(r>0){if(h.length>0){h.children("span").html(q+".00")}g.children("p").html("\u603b\u5171"+$shortName+'<span class="orange arial z-user">'+r+'</span>\u4e2a\u5546\u54c1  \u5408\u8ba1\u91d1\u989d\uff1a<span class="orange arial">'+q+".00</span> \u5143")}else{g.remove()}};var d=function(){var z=$(this);var t=z.attr("id");var v=t.replace("txtNum","");var q=z.next().next();var r=parseInt(z.next().next().next().val());var s,y,w=/^[1-9]{1}\d{0,6}$/;var u;o=t;var x=function(){if(o!=t){return}s=q.val();y=z.val();if(y!=""&&s!=y){var B=$(window).width();var A=(B)/2-z.offset().left-127;if(w.test(y)){u=parseInt(y);if(u<=r){q.val(y)}else{u=r;e("\u6700\u591a"+u+"\u4eba\u6b21",z,-75,A);z.val(u);q.val(u)}p(u,z);j(z,v,u);i(z,u,r);m()}else{e("\u53ea\u80fd\u8f93\u6b63\u6574\u6570\u54e6",z,-75,A);z.val(s)}}setTimeout(x,200)};x()};var p=function(r,u){var t=u.parent().parent().parent();var q=t.find("div.z-Cart-tips");if(r>100){if(q.length==0){var s=$('<div class="z-Cart-tips">\u5df2\u8d85\u8fc7100\u4eba\u6b21\uff0c'+$shortName+"\u5b58\u5728\u4e00\u5b9a\u98ce\u9669\uff0c\u8bf7\u8c28\u614e\u53c2\u4e0e\uff01</div>");t.prepend(s)}}else{q.remove()}};var l=function(){var q=$(this);if(o==q.attr("id")){o=""}if(q.val()==""){q.val(q.next().next().val())}};var j=function(q,t,r){$.ajax({url:"/list/isStatus.action",data:"id="+t,success:function(data){if(data=="false"){var v=$(window).width();var u=(v)/2-q.offset().left-127;e("\u672c\u671f\u5546\u54c1\u5df2"+$shortName+"\u5149\u4e86",q,-75,u)}else{var json=getCookie("products");if(json==null||json==""){var val="[{'pId':"+t+",'num':"+r+"}]";SetCookieByExpires("products",val,1)}else{var flag=false;json=eval("("+json+")");$.each(json,function(n,value){if(t==value.pId){value.num=(parseInt(r));flag=true}});if(!flag){json.push({pId:+t,num:r})}json=JSON.stringify(json);SetCookieByExpires("products",json,1)}q.parent().prev().html("\u603b\u5171"+$shortName+'\uff1a<em class="arial">'+r+'</em>\u4eba\u6b21/<em class="orange arial">\uffe5'+r+".00</em>")}}})};var k=function(w,v){var u=v.attr("id");var s=u.replace("txtNum","");var r=parseInt(v.next().next().next().val());var q=v.next().next();var t=parseInt(q.val())+w;if(t>0&&t<=r){i(v,t,r);q.val(t);v.val(t);p(t,v);j(v,s,t);m()}};var i=function(r,t,s){var q=r.prev();var u=r.next();if(s==1){q.addClass("z-jiandis");u.addClass("z-jiadis")}else{if(t==1){q.addClass("z-jiandis");u.removeClass("z-jiadis")}else{if(t==s){q.removeClass("z-jiandis");u.addClass("z-jiadis")}else{q.removeClass("z-jiandis");u.removeClass("z-jiadis")}}}};$("input:text[name=num]",a).each(function(q){var r=$(this);r.bind("focus",d).bind("blur",l);r.prev().bind("click",function(){k(-1,r)});r.next().bind("click",function(){k(1,r)})});var f=function(){var q=$("li","#cartBody");if(q.length<1){a.parent().remove();c.show()}else{if(q.length<4){h.remove()}}};$("a[name=delLink]",a).each(function(q){$(this).bind("click",function(){var r=$(this);var t=r.attr("cid");var s=function(){var json=getCookie("products");if(json!=null||json!=""){json=eval("("+json+")");var i=0;$.each(json,function(n,value){if(t==value.pId){i=n}});json.splice(i,1);json=JSON.stringify(json);SetCookieByExpires("products",json,1);r.parent().parent().parent().remove();m();f()}else{e("\u5220\u9664\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5")}};n("\u60a8\u786e\u5b9a\u8981\u5220\u9664\u5417\uff1f",s)})})};if(a.length>0){$.getScript("/js/pagedialog.js",b)}else{c.show()}});
