var ReplyFun=null;$(function(){var m="postAdmire";var p=$("#CommentNav");var t=$("#hidPostID").val();var u=$("#ReplyCount");var n=$("#replyList");var q=$("#fillDiv");var x=$("#divLoading");var o=$("#btnLoadMore");var w=$("#btnCancel");var r=false;var v=function(){var M={empty:"\u8bf4\u70b9\u4ec0\u4e48\u5427\uff01",cmtErr:"\u8bc4\u8bba\u5185\u5bb9\u4e0d\u80fd\u5c11\u4e8e3\u4e2a\u5b57\uff01",contentErr:"\u8bc4\u8bba\u5185\u5bb9\u4e0d\u80fd\u591a\u4f59150\u4e2a\u5b57\uff01",subFail:"\u63d0\u4ea4\u5931\u8d25\uff01",notMine:"\u4e0d\u80fd\u5bf9\u81ea\u5df2\u56de\u590d\uff01",tooFast:"\u56de\u590d\u592a\u9891\u7e41\uff0c\u8bf7\u7a0d\u5019\uff01"};var j=function(y){$.PageDialog.ok(y)};var c=function(y){var z=$(window).width();var A=(z)/2-w.offset().left-127;$.PageDialog.fail(y,w,-80,A)};var L=0;var g=0;var Q=0;var b=false;var P=false;var K=false;var f=0;var d=function(){g=$(window).scrollTop();if(!K){L=p.offset().top;K=true}if(g>=L){b=true;if(!r){p.addClass("z-fixed");if($("div.m-comment").is(":visible")){q.show().attr("style","height:164px;")}}}else{b=false;p.removeClass("z-fixed");q.hide().attr("style","");K=false}};$(window).scroll(d);o.bind("click",function(){x.show();o.hide();ReplyFun.getNextPage()});var k=function(y){return y.replace(/&/ig,"&amp;").replace(/</ig,"&lt;").replace(/>/ig,"&gt;").replace(/\[(\/)?(b|br)\]/ig,"<$1$2>").replace(/\[s:(\d+)\]/ig,'<img src="http://skin.1yyg.com/Images/Emoticons/$1.gif" alt="" />').replace(/\[url=([^\]]*)\]([^\[]+)\[\/url\]/ig,'<a href="$1" target="_blank" class="blue">$2</a>').replace(/\s{2}/ig,"&nbsp;&nbsp;")};var h=function(){var y=false;var C=false;var E=1;var A=10;var D=0;var z={PostID:t,FIdx:E,EIdx:A,IsCount:1};var B=function(){var G="";G+="shareId="+z.PostID;G+="&pageNo="+z.FIdx;G+="&pageSize="+z.EIdx;G+="&iscount="+z.IsCount;return G};var F=function(){x.show();$.ajax({url:"/shareShow/shareCommentListAjaxPage.action",data:B(),success:function(Y){if(Y!=0){if(z.IsCount==1){D=Y.length}var H=Y;var G=H.length;var I="";for(var X=0;X<G;X++){var Z=H[X].userFace.indexOf("http")!=-1?H[X].userFace:$img+H[X].userFace;I+='<li><a class="fl u-comment-img" href="/u/'+H[X].userId+'.html"><img border="0" alt="" src="'+Z+'">';I+='</a><div class="u-comment-r"><p class="z-comment-name"><a href="/u/'+H[X].userId+'.html" class="blue">'+H[X].userName+"</a></p>";I+='<p class="gray6"><span>'+k(H[X].content)+"</span><b>"+H[X].createDate+"</b></p></div></li>"}n.append(I);if(D>=z.EIdx){P=false;o.show()}else{o.hide()}}x.hide()}})};this.getInitPage=function(){F()};this.getNextPage=function(){z.FIdx+=1;z.EIdx=A;F()}};var O=function(){var B=false;var C=$("#emHits");var A=getCookie("UP_"+t);var z=C.find("em");var y=z.html();if(A=="1"){B=true;C.addClass("z-btn-moodgray").html("<s></s>\u5df2\u7fa1\u6155("+y+")")}else{C.unbind("click").bind("click",function(){if(B){return false}$.ajax({url:"/shareShow/upShareInfo.action",data:"shareId="+t,success:function(D){if(D=="true"){A=A+t+",";SetCookieByExpires("UP_"+t,"1",1);B=true;C.addClass("z-btn-moodgray").html("<s></s>\u5df2\u7fa1\u6155("+(parseInt(y)+1)+")");c("\u7fa1\u6155+1")}else{O()}}})})}};function e(){var z=null;var A='<div class="clearfix m-round f-share-tips"><div class="f-share-tit">\u8bf7\u9009\u62e9\u4ee5\u4e0b\u65b9\u5f0f\u5206\u4eab</div><a id="btnMsgCancel" href="javascript:void(0)" class="f-share-Close"></a><ul id="shareType" class="f-share-li"><li><a href="javascript:void(0);"><b class="z-sina"><s></s></b><em>\u65b0\u6d6a\u5fae\u535a</em></a></li><li><a href="javascript:void(0);"><b class="z-qq"><s></s></b><em>\u817e\u8baf\u5fae\u535a</em></a></li><li><a href="javascript:void(0);"><b class="z-qzone"><s></s></b><em>QQ\u7a7a\u95f4</em></a></li></ul></div> ';var y=function(){$("#btnMsgCancel").bind("click",function(){z.cancel()})};z=new $.PageDialog(A,{W:300,H:100,autoClose:false,ready:y});$("#shareType").find("b").each(function(){$(this).click(function(){i($(this).attr("class"))})})}function i(B){var y=$("#shareContent").text();if(y.length>150){y=y.substring(0,150)+"..."}var C=$saitName+"-\u6652\u5355\u5206\u4eab:"+y;var z=self.location.toString();var A=$(".m-share-con").find("img:first-child").attr("src");l(B,C,z,A)}function l(F,z,G,B){var E=G,D=/v=pad\&/g,A=/v=touch\&/g;if(D.test(E)){var G=E.replace(D,"")}else{if(A.test(E)){var G=E.replace(A,"")}else{var G=E}}if(F!="weixin"){z=encodeURIComponent(z);G=encodeURIComponent(G);B=encodeURIComponent(B)}var y="http://service.weibo.com/share/share.php?url="+G+"&title="+z+"&pic="+B;var H="http://v.t.qq.com/share/share.php?title="+z+"&pic="+B+"&url="+G;var C="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+G+"&title="+z+"&pics="+B;if(F=="z-sina"){window.open(y);return}else{if(F=="z-qq"){window.open(H);return}else{if(F=="z-qzone"){window.open(C);return}}}}var J=$("#comment");var T=J.val();$("#btnComment").bind("click",function(){var y=self.location.toString();if($("#userState").val()==""){location.href="/login/index.html?forward="+y}else{$("div.m-comment").show();J.focus()}});var a=false;var S=function(){var y=J.val();if(y.length>150){c("\u8bc4\u8bba\u5185\u5bb9\u5df2\u8fbe\u4e0a\u9650\uff01");J.val(y.substring(0,150))}if(a){setTimeout(S,200)}};J.bind("focus",function(){r=true;a=true;var y=$(this).val();if(y==T){$(this).val("").attr("style","color:#666666")}S()}).bind("blur",function(){r=false;a=false;var y=$(this).val();if(y==""){$(this).val(M.empty).attr("style","color:#bbbbbb")}});$("#btnCancel").bind("click",function(){$("div.m-comment").hide();q.hide().attr("style","")});var N=function(){if(!isLoaded){return false}var y=J.val();if(y==T||y==M.empty){c(M.empty);J.focus();return false}else{if(y.length<3){c(M.cmtErr);return false}else{if(y.length>150){c(M.contentErr);return false}}}$.ajax({url:"/shareShow/postComment.action",data:"commentText="+y+"&shareId="+t+"&userId="+$("#userState").val(),success:function(A){if(A=="true"){j("\u8bc4\u8bba\u6210\u529f");$("#comment").val("");isLoaded=true;var z=parseInt(u.html());if(z==0){u.parent().parent().show();$("div.m-shareT-round").show()}u.html(z+1);$("div.m-comment").hide();var B='<li><a class="fl u-comment-img" href="/u/'+$("#userState").val()+'.html"><img border="0" alt="" src="/Images/defaultUserFace.png">';B+='</a><div class="u-comment-r"><p class="z-comment-name"><a href="/u/'+$("#userState").val()+'" class="blue">'+$(".Member").text()+"</a></p>";B+='<p class="gray6"><span>'+y+"</span><b>\u521a\u521a</b></p></div></li>";n.prepend(B)}else{if(A=="false"){c(M.subFail)}else{if(A=="false"){c(M.notMine)}else{if(A=="false"){c(M.tooFast)}else{if(A=="false"){c(M.cmtErr)}else{if(A=="false"){location.replace(location.href)}else{c(M.subFail)}}}}}}}})};$("#btnPublish").bind("click",N);$("#btnShare").bind("click",e);var R=function(){if(u.html()==0){$(".m-share-comment").hide();$(".m-shareT-round").hide()}else{if(u.html()>10){o.show()}}};isLoaded=true;R();O();ReplyFun=new h();ReplyFun.getInitPage()};var s=function(){$.getScript("/js/pagedialog.js?v=130826",v)};$.getScript("/js/comm.js?v=130826",s)});
