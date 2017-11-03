$(function() {
	var d = false;
	var c = function() {
		//总共需付金额
		var shopMoney = parseInt($("#hidShopMoney").val());
		var balance = $("#hidBalance").val();
		var points = parseInt($("#hidPoints").val());
		var selPoints = $("#spPoints");
		var selBalance = $("#spBalance");
		var O = null;
		var I = $("#bankList");
		//var D = "云支付";
		var D = $("#bankList ul li").eq(1).attr("payText");
		$("#bankList ul li").eq(0).html('<s class="z-arrow"></s>选择' + D);
		var payName = $("#bankList ul li").eq(1).attr("payName");
		$("#bankList ul li").eq(1).children("i").attr("class", "z-bank-Roundsel");
		//var payNames = ["YunPay","IAppPay","JubaoPay","Swift","Alipay","jdpay"];
		//var P = parseInt(points / 100) > shopMoney ? shopMoney : parseInt(points / 100);
		var P = 0;	//用积分抵扣的金额部分
		var y = 0;	//用余额支付的部分
		var R = 0;	//还需在线支付的金额
		var L = function(e, f, g, h) {
			$.PageDialog.fail(e, f, g, h)
		};
		var a = function(e, f, g) {
			$.PageDialog.confirm(e, f, g)
		};
		if (P < shopMoney) {
			var M = parseInt(balance);
			if (M > 0) {
				var N = shopMoney - P;
				if (M >= N) {
					y = N
				} else {
					y = M;
					R = N - M
				}
			} else {
				R = shopMoney - P
			}
		}
		var E = function(e) {
			P = e;
			if (e > 0) {
				selPoints.parent().removeClass("z-pay-grayC");
				selPoints
						.attr("sel", "1")
						.attr("class", "z-pay-mentsel")
						.next("span")
						.html(
								'积分支付<em class="orange">'
										+ e
										+ ".00</em>元（您的积分："
										+ points + "）")
			} else {
				selPoints
						.attr("sel", "0")
						.attr("class", "z-pay-ment")
						.next("span")
						.html(
								'积分支付<em class="orange">0.00</em>元（您的积分：'
										+ points + "）")
			}
		};
		var Q = function(e) {
			y = e;
			if (e > 0) {
				selBalance.parent().removeClass("z-pay-grayC");
				selBalance
						.attr("sel", "1")
						.attr("class", "z-pay-mentsel")
						.next("span")
						.html(
								'余额支付<em class="orange">'
										+ e
										+ ".00</em>元（账户余额："
										+ balance + " 元）")
			} else {
				selBalance
						.attr("sel", "0")
						.attr("class", "z-pay-ment")
						.next("span")
						.html(
								'余额支付<em class="orange">0.00</em>元（账户余额：'
										+ balance + " 元）")
			}
		};
		var K = function(e) {
			//R = e;20160127杨振朋
			R = y;
			if (e > 0) {
				O.html('<s class="z-arrow"></s>选择'
						+ (D == "微信支付" ? "微信"
								: '<b class="z-mlr">' + D + "</b>")
						+ "支付"
						+ ((P > 0 || y > 0) ? "剩余" : "")
						+ '<em class="orange">' + R + ".00</em>元");
				O.removeClass("z-pay-grayC").nextAll().show();
				G = true
			} else {
				O
						.addClass("z-pay-grayC")
						.html(
								'<s class="z-arrow"></s>选择'
								+ (D == "微信支付" ? "微信"
										: '<b class="z-mlr">' + D + "</b>")
								+ "支付")
						.nextAll().hide();
				G = false
			}
		};
		if (parseInt(points / 100) > 0) {
			selPoints.parent().click(function() {
				K(0);
				if (selPoints.attr("sel") == 1) {
					E(0);
					H(shopMoney)
				} else {
					var e = parseInt(points / 100);
					if (e > 0) {
						E(e >= shopMoney ? shopMoney : e);
						H(e >= shopMoney ? 0 : shopMoney - e)
					} else {
						H(shopMoney)
					}
				}
			});
			var H = function(e) {
				if (selBalance.attr("sel") == 1) {
					var f = parseInt(balance) - e;
					if (f > 0) {
						Q(e)
					} else {
						Q(parseInt(balance));
						K(-f)
					}
				} else {
					K(e)
				}
			}
		}
		if (parseInt(balance) > 0) {
			selBalance.parent().click(function() {
				K(0);
				if (selBalance.attr("sel") == 1) {
					Q(0);
					J(shopMoney)
				} else {
					var e = parseInt(balance);
					if (e > 0) {
						Q(e >= shopMoney ? shopMoney : e);
						J(e >= shopMoney ? 0 : shopMoney - e)
					} else {
						J(shopMoney)
					}
				}
			});
			var J = function(e) {
				if (selPoints.attr("sel") == 1) {
					var f = parseInt(points / 100) - e;
					if (f > 0) {
						E(e)
					} else {
						E(parseInt(points / 100));
						K(-f)
					}
				} else {
					K(e)
				}
			}
		}
		var G = false;
		var z = 1;
		$("li", I)
				.each(
						function(f) {
							var e = $(this);
							if (f == 0) {
								O = e;
								O.click(function() {
									if (!O.hasClass("z-pay-grayC")) {
										if (!G) {
											O.nextAll().show();
											G = true
										} else {
											O.nextAll().hide();
											G = false
										}
									}
								})
							} else {
								e
										.click(function() {
											z = f;
											D = e.text();
											//payName=payNames[f-1];
											payName=e.attr("payName");
											e.children("i").attr("class",
													"z-bank-Roundsel");
											e
													.siblings()
													.each(
															function() {
																$(this)
																		.children(
																				"i")
																		.attr(
																				"class",
																				"z-bank-Round")
															});
											O
													.html('<s class="z-arrow"></s>选择<b class="z-mlr">'
															+ D
															+ "</b>支付"
															+ ((P > 0 || y > 0) ? "剩余"
																	: "")
															+ '<em class="orange">'
															+ R
															+ ".00</em>元")
										})
							}
						});
		if (R > 0) {
			O.removeClass("z-pay-grayC").html(
					'<s class="z-arrow"></s>选择<b class="z-mlr">' + D
							+ "</b>支付"
							+ ((P > 0 || y > 0) ? "剩余" : "")
							+ '<em class="orange">' + R + ".00</em>元")
					.nextAll().show();
			G = true
		} else {
			O.addClass("z-pay-grayC").nextAll().hide();
			G = false
		}
		var C = $("#btnPay");
		var A = function() {
			if (!d) {
				return
			}
			/*使用积分抵扣*/
			var e = function() {
				location.replace("/mycart/goPay.html?moneyCount=" + shopMoney
						+ "&bankMoney=0&integral=" + (P*100) + "&hidUseBalance=0&userPayType=2");
				return false
			};
			/*使用账户余额支付*/
			var g = function() {
				location.replace("/mycart/goPay.html?moneyCount=" + shopMoney
						+ "&integral=" + (P*100) + "&hidUseBalance=" + y
						+ "&bankMoney=0&userPayType=1");
				return false
			};
			/*使用积分和余额支付*/
			var f = function() {
				location.replace("/mycart/goPay.html?moneyCount=" + shopMoney
						+ "&integral=" + (P*100) + "&hidUseBalance=" + y
						+ "&bankMoney=0&userPayType=3");
				return false
			};
			var h = function() {
				location.replace("/mycart/goPay.html?moneyCount=" + shopMoney
						+ "&integral=" + (P*100) + "&hidUseBalance=" + y
						+ "&bankMoney="+(shopMoney-y-P)+"&userPayType=4&payName="+payName);
				return false
			};
			if (P >= shopMoney) {
				a("您确定使用积分抵扣吗?",e);
			} else {
				if (y >= shopMoney) {
					a("您确定使用账户余额支付吗?",g);
				} else {
					if (y + P >= shopMoney) {
						a("您确定使用积分和余额支付吗?",f);
					} else {
						h();
					}
				}
			}
		};
		C.bind("click", A);
		d = true
	};
	$.getScript("/js/pagedialog.js", c)
});
