var BuyRecordFun = null;
var GetGoodsFun = null;
var SingleFun = null;
$(document)
		.ready(
				function() {
					var b = function() {
						var a = $www;
						var z = $("#hdUserID");
						var r = $("#divMidNav");
						var q = $("#divBuyRecord");
						var y = $("#divGetGoods");
						var t = $("#divSingle");
						var A = 0;
						var s = $("#btnLoadMore");
						var B = $("#divLoading");
						r.find("span").each(function(d) {
							var c = $(this);
							c.click(function() {
								v(c, d)
							})
						});
						var v = function(c, d) {
							c.addClass("mCurr").siblings().removeClass("mCurr");
							switch (d) {
							case 0:
								A = 0;
								q.show();
								y.hide();
								t.hide();
								if (BuyRecordFun.CountFalg) {
									s.show()
								} else {
									s.hide()
								}
								_IsLoading = false;
								break;
							case 1:
								A = 1;
								q.hide();
								y.show();
								t.hide();
								if (!GetGoodsFun.initFlag) {
									B.show();
									GetGoodsFun.initData();
									GetGoodsFun.initFlag = true
								}
								if (GetGoodsFun.CountFalg) {
									s.show()
								} else {
									s.hide()
								}
								_IsLoading = false;
								break;
							case 2:
								A = 2;
								q.hide();
								y.hide();
								t.show();
								if (!SingleFun.initFlag) {
									B.show();
									SingleFun.initData();
									SingleFun.initFlag = true
								}
								if (SingleFun.CountFalg) {
									s.show()
								} else {
									s.hide()
								}
								_IsLoading = false;
								break
							}
							loadImgFun(0)
						};
						var w = function() {
							var k = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							var j = false;
							var e = false;
							var d = 1;
							var c = 10;
							var i = 0;
							this.CountFalg = false;
							var f = {
								Type : 0,
								UserID : z.val(),
								FIdx : d,
								EIdx : c,
								IsCount : 1
							};
							var g = function() {
								var l = "";
								l += "type=" + f.Type;
								l += "&userID=" + f.UserID;
								l += "&fidx=" + f.FIdx;
								l += "&eidx=" + f.EIdx;
								l += "&iscount=" + f.IsCount;
								return l
							};
							var h = function() {
								var l = function(L) {
									if (L.code == 0) {
										if (f.IsCount == 1) {
											i = L.count
										}
										var o = L.Rows;
										var n = o.length;
										var P = "";
										if (o <= 0) {
											P = k
										}
										for (var m = 0; m < n; m++) {
											var O = o[m];
											var M = O.codeSales;
											var J = O.codeQuantity;
											var N = parseFloat(M * 100 / J);
											var K = Math.ceil(N);
											P += "<ul onclick=\"location.href='/product/"
													+ O.codeID
													+ '.html\'"><li class="mBuyRecordL">';
											P += '<img src2="http://mimg.1yyg.com/GoodsPic/pic-200-200/'
													+ O.goodsPic
													+ '" src="'
													+ Gobal.LoadPic + '"></li>';
											P += '<li class="mBuyRecordR">(\u7b2c'
													+ O.codePeriod
													+ "\u671f)"
													+ O.goodsSName + "";
											if (O.codeState == 3) {
												P += '<p class="mValue">价值：￥'
														+ O.codePrice
																.toFixed(2)
														+ '</p><span>获得者：<a style="color: #22AAff" href="http://m.1yyg.com/userpage/'
														+ O.userWeb
														+ '">'
														+ O.userName
														+ '</a><br>幸运夺宝码：<em class="orange">'
														+ O.codeRNO
														+ "</em></span>"
											} else {
												P += '<div class="pRate"><div class="Progress-bar">';
												if (K == 0) {
													P += '<p class="u-progress"></p>'
												} else {
													P += '<p class="u-progress" title="已完成'
															+ N
															+ '%"><span class="pgbar" style="width: '
															+ K
															+ '%;"><span class="pging"></span></span></p>'
												}
												P += '<ul class="Pro-bar-li">';
												P += '<li class="P-bar01"><em>'
														+ O.codeSales
														+ '</em>已参与</li><li class="P-bar02"><em>'
														+ O.codeQuantity
														+ '</em>总需人次</li><li class="P-bar03"><em>'
														+ (O.codeQuantity - O.codeSales)
														+ "</em>剩余</li>";
												P += "</ul></div></div>"
											}
											P += "</li></ul>"
										}
										q.append(P);
										k = "";
										loadImgFun();
										if (i > f.EIdx) {
											_IsLoading = false;
											s.show();
											B.hide();
											BuyRecordFun.CountFalg = true
										} else {
											s.hide();
											B.hide();
											BuyRecordFun.CountFalg = false
										}
									} else {
										if (L.code == 1) {
											q
													.append('<div class="haveNot z-minheight"><s></s><p>TA的好友才可以看哦</p></div>')
										} else {
											if (L.code == 2) {
												q
														.append('<div class="haveNot z-minheight"><s></s><p>TA未有公开的夺宝记录哦</p></div>')
											} else {
												q.append(k);
												BuyRecordFun.CountFalg = false
											}
										}
										s.hide();
										B.hide()
									}
								}
							};
							this.initData = function() {
								h()
							};
							this.getNextPage = function() {
								f.FIdx += c;
								f.EIdx += c;
								h()
							}
						};
						var u = function() {
							var k = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							this.initFlag = false;
							var j = false;
							var e = false;
							var d = 1;
							var c = 10;
							var i = 0;
							this.CountFalg = false;
							var f = {
								Type : 1,
								UserID : z.val(),
								FIdx : d,
								EIdx : c,
								IsCount : 1
							};
							var g = function() {
								var l = "";
								l += "type=" + f.Type;
								l += "&userID=" + f.UserID;
								l += "&fidx=" + f.FIdx;
								l += "&eidx=" + f.EIdx;
								l += "&iscount=" + f.IsCount;
								return l
							};
							var h = function() {
								var l = function(G) {
									if (G.code == 0) {
										if (f.IsCount == 1) {
											i = G.count
										}
										var o = G.Rows;
										var n = o.length;
										var H = "";
										if (o <= 0) {
											H = k
										}
										for (var m = 0; m < n; m++) {
											var F = o[m];
											H += "<ul onclick=\"location.href='/lottery/detail-"
													+ F.codeID
													+ '.html\'" class="BuyRecordList"><li class="mBuyRecordL">';
											H += '<img src2="http://mimg.1yyg.com/GoodsPic/pic-200-200/'
													+ F.goodsPic
													+ '" src="'
													+ Gobal.LoadPic + '"></li>';
											H += '<li class="mBuyRecordR">(第'
													+ F.codePeriod
													+ "期)"
													+ F.goodsSName
													+ '<p class="mValue">价值：￥'
													+ F.codePrice.toFixed(2)
													+ "</p>";
											H += '<span>幸运夺宝码：<em class="orange">'
													+ F.codeRNO
													+ "</em><br>揭晓时间："
													+ F.codeRTime
													+ " </span></li></ul>"
										}
										y.append(H);
										k = "";
										loadImgFun();
										if (i > f.EIdx) {
											_IsLoading = false;
											s.show();
											B.hide();
											GetGoodsFun.CountFalg = true
										} else {
											s.hide();
											B.hide();
											GetGoodsFun.CountFalg = false
										}
									} else {
										if (G.code == 1) {
											y
													.append('<div class="haveNot z-minheight"><s></s><p>TA的好友才可以看哦</p></div>')
										} else {
											if (G.code == 2) {
												y
														.append('<div class="haveNot z-minheight"><s></s><p>TA未有公开的获得商品记录哦</p></div>')
											} else {
												y.append(k);
												GetGoodsFun.CountFalg = false
											}
										}
										s.hide();
										B.hide()
									}
								}
							};
							this.initData = function() {
								h()
							};
							this.getNextPage = function() {
								f.FIdx += c;
								f.EIdx += c;
								h()
							}
						};
						var p = function() {
							var k = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							this.initFlag = false;
							var j = false;
							var e = false;
							var d = 1;
							var c = 10;
							var i = 0;
							this.CountFalg = false;
							var f = {
								Type : 2,
								UserID : z.val(),
								FIdx : d,
								EIdx : c,
								IsCount : 1
							};
							var g = function() {
								var l = "";
								l += "type=" + f.Type;
								l += "&userID=" + f.UserID;
								l += "&fidx=" + f.FIdx;
								l += "&eidx=" + f.EIdx;
								l += "&iscount=" + f.IsCount;
								return l
							};
							var h = function() {
								var l = function(K) {
									if (K.code == 0) {
										if (f.isCount == 1) {
											i = K.count
										}
										var o = K.Rows;
										var m = o.length;
										var M = "";
										var J = "";
										if (o <= 0) {
											M = k
										}
										for (var N = 0; N < m; N++) {
											var L = o[N];
											J = L.postContent;
											if (J.length > 75) {
												J = J.substring(0, 75) + "..."
											}
											M += '<li><a href="http://m.1yyg.com/post/detail-'
													+ L.postID
													+ '.html"><h3><b>'
													+ L.postTitle
													+ "</b><em>"
													+ L.postOkTime
													+ "</em></h3>";
											M += "<p>" + J + "</p><dl>";
											var I = L.postAllPic.split(",");
											for (var n = 0; n < I.length; n++) {
												M += '<img src2="http://mimg.1yyg.com/userpost/small/'
														+ I[n]
														+ '" src="'
														+ Gobal.LoadPic + '">';
												if (n >= 2) {
													break
												}
											}
											M += "</dl></a></li>"
										}
										t.children().append(M);
										k = "";
										loadImgFun();
										if (i > f.EIdx) {
											_IsLoading = false;
											s.show();
											B.hide();
											SingleFun.CountFalg = true
										} else {
											s.hide();
											B.hide();
											SingleFun.CountFalg = false
										}
									} else {
										if (K.code == 1) {
											t
													.append('<div class="haveNot z-minheight"><s></s><p>TA的好友才可以看哦</p></div>')
										} else {
											if (K.code == 2) {
												t
														.append('<div class="haveNot z-minheight"><s></s><p>TA未有公开的晒单记录哦</p></div>')
											} else {
												t.children().append(k);
												SingleFun.CountFalg = false
											}
										}
										s.hide();
										B.hide()
									}
								}
							};
							this.initData = function() {
								h()
							};
							this.getNextPage = function() {
								f.FIdx += c;
								f.EIdx += c;
								h()
							}
						};
						var x = function() {
							B.show();
							switch (A) {
							case 0:
								BuyRecordFun.getNextPage();
								break;
							case 1:
								GetGoodsFun.getNextPage();
								break;
							case 2:
								SingleFun.getNextPage();
								break
							}
						};
						BuyRecordFun = new w();
						BuyRecordFun.initData();
						GetGoodsFun = new u();
						SingleFun = new p();
						s.bind("click", function() {
							s.hide();
							B.show();
							x()
						});
						isLoaded = true
					};
					b()
				});
