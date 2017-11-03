var BuyRecordFun = null;
var GetGoodsFun = null;
var SingleFun = null;
$(document)
		.ready(
				function() {
					var a = function() {
						var r = $www;
						var d = $("#hdUserID");
						var n = $("#divMidNav");
						var p = $("#divBuyRecord");
						var e = $("#divGetGoods");
						var j = $("#divSingle");
						var c = 0;
						var k = $("#btnLoadMore");
						var b = $("#divLoading");
						var o = false;
						var m = false;
						var l = false;
						n.find("span").each(function(s) {
							var t = $(this);
							t.click(function() {
								h(t, s)
							})
						});
						var h = function(t, s) {
							t.addClass("mCurr").siblings().removeClass("mCurr");
							switch (s) {
							case 0:
								c = 0;
								p.show();
								e.hide();
								j.hide();
								if (BuyRecordFun.CountFalg) {
									k.show()
								} else {
									k.hide()
								}
								_IsLoading = false;
								break;
							case 1:
								c = 1;
								p.hide();
								e.show();
								j.hide();
								if (!GetGoodsFun.initFlag) {
									b.show();
									GetGoodsFun.initData();
									GetGoodsFun.initFlag = true
								}
								if (GetGoodsFun.CountFalg) {
									k.show()
								} else {
									k.hide()
								}
								_IsLoading = false;
								break;
							case 2:
								c = 2;
								p.hide();
								e.hide();
								j.show();
								if (!SingleFun.initFlag) {
									b.show();
									SingleFun.initData();
									SingleFun.initFlag = true
								}
								if (SingleFun.CountFalg) {
									k.show()
								} else {
									k.hide()
								}
								_IsLoading = false;
								break
							}
							loadImgFun(0)
						};
						var g = function() {
							var v = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							var w = false;
							var s = false;
							var t = 1;
							var u = 10;
							var x = 0;
							var page=1;
							var psize=10;
							this.CountFalg = false;
							var A = {
								Type : 0,
								UserID : d.val(),
								PageNo : page,
								PageSize : psize,
								FIdx : t,
								EIdx : u,
								IsCount : 1
							};
							var z = function() {
								var B = "";
								B += "id=" + A.UserID;
								B += "&pageNo=" + A.PageNo;
								B += "&pageSize=" + A.PageSize;
								B += "&iscount=" + A.IsCount;
								return B
							};
							var y = function() {
								if (o) {
									b.hide();
									return
								}
								o = true;
								b.show();
								var B = function(L) {
									if (true) {
										if (A.IsCount == 1) {
											x = L.count;
											n.find("span").eq(0).find("em")
													.html(x)
										}
										var E = L;
										var F = E.length;
										var H = "";
										if (E <= 0) {
											H = v
										}
										for (var G = 0; G < F; G++) {
											var I = E[G];
											var K = I.spellbuyCount;
											var D = I.productPrice;
											var J = parseFloat(K * 100 / D);
											var C = Math.ceil(J);
											H += "<ul onclick=\"location.href='/products/"
													+ I.productId
													+ '.html\'"><li class="mBuyRecordL">';
											H += '<img src="'
													+ $img+I.productImg
													+ '" src2="'
													+ Gobal.LoadPic + '"></li>';
											H += '<li class="mBuyRecordR">(第'
													+ I.productPeriod + "云)"
													+ I.productName + "";
											if (I.buyStatus == 1) {
												H += '<p class="mValue">价值：￥'
														+ I.productPrice.toFixed(2)
														+ '</p><span>获得者：<a style="color: #22AAff" href="/u/'
														+ I.winUserId
														+ '.html">'
														+ I.winUser
														+ '</a><br>幸运夺宝码：<em class="orange">'
														+ I.winId
														+ "</em></span>"
											} else {
												H += '<div class="pRate"><div class="Progress-bar">';
												if (C == 0) {
													H += '<p class="u-progress"></p>'
												} else {
													H += '<p class="u-progress" title="已完成'
															+ J
															+ '%"><span class="pgbar" style="width: '
															+ C
															+ '%;"><span class="pging"></span></span></p>'
												}
												H += '<ul class="Pro-bar-li">';
												H += '<li class="P-bar01"><em>'
														+ I.spellbuyCount
														+ '</em>已参与</li><li class="P-bar02"><em>'
														+ I.productPrice
														+ '</em>总需人次</li><li class="P-bar03"><em>'
														+ (I.productPrice - I.spellbuyCount)
														+ "</em>剩余</li>";
												H += "</ul></div></div>"
											}
											H += "</li></ul>"
										}
										p.append(H);
										v = "";
										//loadImgFun();
										if (F == psize) {
											_IsLoading = false;
											k.show();
											b.hide();
											BuyRecordFun.CountFalg = true;
											p.removeClass("marginB")
										} else {
											k.hide();
											b.hide();
											BuyRecordFun.CountFalg = false;
											p.addClass("marginB")
										}
									} else {
										if (L.code == 1) {
											p.append('<div class="noRecords gray9"><s></s>TA的好友才可以看哦</div>')
										} else {
											if (L.code == 2) {
												p
														.append('<div class="noRecords gray9"><s></s>TA未有公开的夺宝记录哦</div>')
											} else {
												p.append(v);
												BuyRecordFun.CountFalg = false
											}
										}
										k.hide();
										b.hide()
									}
								};
								$.ajax({
									url : "/u/userBuyAjaxPage.action",
									type : "get",
									data : z(),
									success : B
								});
								//GetJPData(r, "getUserPageBuyRafPost", z(), B)
							};
							this.initData = function() {
								y();
							};
							this.getNextPage = function() {
								A.PageNo++;
								y();
							}
						};
						var i = function() {
							var v = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							this.initFlag = false;
							var w = false;
							var s = false;
							var t = 1;
							var u = 10;
							var x = 0;
							var page=1;
							var psize=10;
							this.CountFalg = false;
							var A = {
								Type : 1,
								UserID : d.val(),
								PageNo : page,
								PageSize : psize,
								FIdx : t,
								EIdx : u,
								IsCount : 1
							};
							var z = function() {
								var B = "";
								B += "id=" + A.UserID;
								B += "&pageNo=" + A.PageNo;
								B += "&pageSize=" + A.PageSize;
								B += "&iscount=" + A.IsCount;
								return B
							};
							var y = function() {
								if (m) {
									b.hide();
									return
								}
								m = true;
								b.show();
								var B = function(C) {
									if (true) {
										if (A.IsCount == 1) {
											x = C.count;
											n.find("span").eq(1).find("em")
													.html(x)
										}
										var G = C;
										var H = G.length;
										var E = "";
										if (G <= 0) {
											E = v
										}
										for (var D = 0; D < H; D++) {
											var F = G[D];
											E += "<ul onclick=\"location.href='/lotteryDetail/"
													+ F.productId
													+ '.html\'" class="BuyRecordList"><li class="mBuyRecordL">';
											E += '<img src="'
													+ $img+F.productImg
													+ '" src2="'
													+ Gobal.LoadPic + '"></li>';
											E += '<li class="mBuyRecordR">(第'
													+ F.productPeriod + "云)"
													+ F.productName
													+ '<p class="mValue">价值：￥'
													+ F.productPrice.toFixed(2)
													+ "</p>";
											E += '<span>幸运夺宝码：<em class="orange">'
													+ F.winId
													+ "</em><br>揭晓时间：<i>"
													+ F.winDate
													+ "</i></span></li></ul>"
										}
										e.append(E);
										v = "";
										//loadImgFun();
										if (H == psize) {
											_IsLoading = false;
											k.show();
											b.hide();
											GetGoodsFun.CountFalg = true;
											e.removeClass("marginB")
										} else {
											k.hide();
											b.hide();
											GetGoodsFun.CountFalg = false;
											e.addClass("marginB")
										}
									} else {
										if (C.code == 1) {
											e
													.append('<div class="noRecords gray9"><s></s>TA的好友才可以看哦</div>')
										} else {
											if (C.code == 2) {
												e
														.append('<div class="noRecords gray9"><s></s>TA未有公开的获得商品记录哦</div>')
											} else {
												e.append(v);
												GetGoodsFun.CountFalg = false
											}
										}
										k.hide();
										b.hide()
									}
								};
								$.ajax({
									url : "/u/userRaffleAjaxPage.action",
									type : "get",
									data : z(),
									success : B
								});
								//GetJPData(r, "getUserPageBuyRafPost", z(), B)
							};
							this.initData = function() {
								y()
							};
							this.getNextPage = function() {
								A.PageNo++;
								y()
							}
						};
						var q = function() {
							var v = '<div class="haveNot z-minheight"><s></s><p>暂无记录</p></div>';
							this.initFlag = false;
							var w = false;
							var s = false;
							var t = 1;
							var u = 10;
							var x = 0;
							var page=1;
							var psize=10;
							this.CountFalg = false;
							var A = {
								Type : 2,
								UserID : d.val(),
								PageNo : page,
								PageSize : psize,
								FIdx : t,
								EIdx : u,
								IsCount : 1
							};
							var z = function() {
								var B = "";
								B += "id=" + A.UserID;
								B += "&pageNo=" + A.PageNo;
								B += "&pageSize=" + A.PageSize;
								B += "&iscount=" + A.IsCount;
								return B
							};
							var y = function() {
								if (l) {
									b.hide();
									return
								}
								l = true;
								b.show();
								var B = function(K) {
									if (true) {
										if (A.IsCount == 1) {
											A.IsCount = 0;
											x = K.count;
											n.find("span").eq(2).find("em")
													.html(x)
										}
										var E = K;
										var G = E.length;
										var I = "";
										var C = "";
										if (E <= 0) {
											I = v
										}
										for (var H = 0; H < G; H++) {
											var J = E[H];
											C = J.shareContent;
											if (C.length > 75) {
												C = C.substring(0, 75) + "..."
											}
											I += '<li><a href="/shareShow/'
													+ J.uid
													+ '.html"><h3><b>'
													+ J.shareTitle
													+ "</b><em>"
													+ J.shareDate
													+ "</em></h3>";
											I += "<p>" + C + "</p><dl>";
											var D = J.shareImages.split(",");
											for (var F = 0; F < D.length; F++) {
												I += '<img src="'+$img+'/UserPost/Big/'
														+ D[F]
														+ '" src2="'
														+ Gobal.LoadPic + '">';
												if (F >= 2) {
													break
												}
											}
											I += "</dl></a></li>"
										}
										j.children().append(I);
										v = "";
										//loadImgFun();
										if (G == psize) {
											_IsLoading = false;
											k.show();
											b.hide();
											SingleFun.CountFalg = true;
											j.removeClass("marginB")
										} else {
											k.hide();
											b.hide();
											SingleFun.CountFalg = false;
											j.addClass("marginB")
										}
									} else {
										if (K.code == 1) {
											j
													.append('<div class="noRecords gray9"><s></s>TA的好友才可以看哦</div>')
										} else {
											if (K.code == 2) {
												j
														.append('<div class="noRecords gray9"><s></s>TA未有公开的晒单记录哦</div>')
											} else {
												j.children().append(v);
												SingleFun.CountFalg = false
											}
										}
										k.hide();
										b.hide()
									}
								};
								$.ajax({
									url : "/u/userPostAjaxPage.action",
									type : "get",
									data : z(),
									success : B
								});
								//GetJPData(r, "getUserPageBuyRafPost", z(), B)
							};
							this.initData = function() {
								y()
							};
							this.getNextPage = function() {
								A.PageNo++;
								y()
							}
						};
						var f = function() {
							switch (c) {
							case 0:
								o = false;
								BuyRecordFun.getNextPage();
								break;
							case 1:
								m = false;
								GetGoodsFun.getNextPage();
								break;
							case 2:
								l = false;
								SingleFun.getNextPage();
								break
							}
						};
						BuyRecordFun = new g();
						BuyRecordFun.initData();
						GetGoodsFun = new i();
						SingleFun = new q();
						k.bind("click", function() {
							k.hide();
							f()
						});
						BuyRecordFun.initData();
						GetGoodsFun.initData();
						SingleFun.initData()
					};
					a()
				});