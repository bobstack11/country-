$(function() {
	var m = 10;
	var j = false;
	var i = null;
	var k = null;
	var h = null;
	var n = 1;
	var payName = $("#ulBankList li").eq(1).attr("payName");
	$("#ulBankList li").eq(1).children("i").attr("class", "z-bank-Roundsel");
	var l = function() {
		var b = function(g, q, r, f) {
			$.PageDialog.fail(g, q, r, f)
		};
		function a(f) {
			f = Math.round(f * 1000) / 1000;
			f = Math.round(f * 100) / 100;
			if (/^\d+$/.test(f)) {
				return f + ".00"
			}
			if (/^\d+\.\d$/.test(f)) {
				return f + "0"
			}
			return f
		}
		var e = /^[1-9]{1}\d*$/;
		var c = "";
		var d = function() {
			var f = i.val();
			if (f != "") {
				if (c != f) {
					if (!e.test(f)) {
						i.val(c).focus()
					} else {
						c = f;
						k
								.html('选择网银充值<em class="orange">'
										+ a(f) + "</em>\u5143")
					}
				}
			} else {
				c = "";
				i.focus();
				k
						.html('选择网银充值<em class="orange">0.00</em>\u5143')
			}
		};
		$("#ulOption > li")
				.each(
						function(f) {
							var g = $(this);
							if (f < 5) {
								g
										.click(function() {
											j = false;
											m = g.attr("money");
											g.children("a").addClass("z-sel");
											g.siblings().children()
													.removeClass("z-sel")
													.removeClass("z-initsel");
											k
													.html('选择网银充值<em class="orange">'
															+ g.attr("money")
															+ ".00</em>元")
										})
							} else {
								i = g.find("input");
								i
										.focus(
												function() {
													j = true;
													if (i.val() == "输入金额") {
														i.val("")
													}
													i.parent().addClass(
															"z-initsel")
															.parent()
															.siblings()
															.children()
															.removeClass(
																	"z-sel");
													if (h == null) {
														h = setInterval(d, 200)
													}
												}).blur(function() {
											clearInterval(h);
											h = null;
										})
							}
						});
		$("#ulBankList > li").each(function(f) {
			var g = $(this);
			if (f == 0) {
				k = g;
			} else {
				g.click(function() {
					n = f;
					payName = g.attr("payName");
					g.children("i").attr("class", "z-bank-Roundsel");
					g.siblings().children("i").attr("class", "z-bank-Round")
				});
			}
		});
		$("#btnSubmit").click(function() {
			m = j ? i.val() : m;
			if (m == "" || parseInt(m) == 0) {
				b("请输入充值金额")
			} else {
				var g = /^[1-9]\d*\.?\d{0,2}$/;
				if (g.test(m)) {
					/*var f = "";
					if (n == 1) {
						//f = "Swift"
						f = "YunPay";
					} else {
						if (n == 2) {
							f = "Alipay";
						} else {
							if (n == 3) {
								f = "JubaoPay";
							}
						}
					}*/
					location.replace("/balance/goBalance.html?moneyCount=" + m	+"&payName="+payName+"&payBank=0");
				} else {
					b("充值金额输入有误");
				}
			}
		})
	};
	l()
});
