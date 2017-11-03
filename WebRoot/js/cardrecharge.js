$(function() {
	var b = function() {
		var y = function(c) {
			$.PageDialog.fail(c)
		};
		var w = function(c, d) {
			$.PageDialog.ok(c, d)
		};
		var v = function(j, e, i, g, c, k) {
			var h = 255;
			var d = 45;
			if (g > 0) {
				h = g
			}
			if (c > 0) {
				d = c
			}
			var f = $(window).width();
			$.PageDialog('<div class="Prompt">' + j + "</div>", {
				W : h,
				H : d,
				obj : e,
				oT : i,
				oL : (f - h) / 2,
				close : false,
				autoClose : true,
				submit : function() {
					if (k) {
						k()
					}
				}
			})
		};
		var a = $("#txtCard");
		/*var u = $("#txtPwd");*/
		var s = $("#btnSubmit");
		var x = $("#li_info");
		var handle = function() {
			s
					.attr("class", "orgBtn")
					.unbind("click")
					.bind(
							"click",
							function() {
								if (!isLoaded) {
									return
								}
								isLoaded = false;
								var c = a.val().replace(/\s/g, "");
								/*var d = u.val();*/
								if (!(c.length == 12 && parseInt(c) > 0)) {
									v("请输入正确的充值卡号", a, -43)
								} else {
									/*if (!(d.length == 8 && parseInt(d) > 0)) {
										v("请输入正确的充值卡密码！", u, -43)
									} else {
									}*/
									$.ajax({
										url : "/user/doCardRecharge.action",
										type : "get",
										data : "id="+c,
										success : function(q) {
											if (q == 'yes') {
												w("充值成功！",
														function() {
															location.href = "UserBalance.html"
														})
											} else {
												v("充值失败，请重试！", x, -43)
											}
											isLoaded = true
										}
									});
								}
								isLoaded = true
							})
		};
		var z = function() {
			if (x.html().length > 0) {
				x.html("");
				s.attr("class", "grayBtn").unbind("click");
				isLoaded = true
			}
		};
		var q = null;
		var o = "", r = /^\d{1,}$/;
		$.fn.bankInput = function(c, i, h) {
			var e = {
				min : 10,
				max : 25,
				deimiter : " ",
				onlyNumber : true,
				copy : true
			};
			var g = $.extend({}, e, i);
			var f = $(c);
			f.css({
				imeMode : "Disabled"
			}).attr("maxlength", g.max);
			if (f.val() != "") {
				f.val(f.val().replace(/\s/g, "").replace(/(\d{4})(?=\d)/g,
						"$1" + g.deimiter))
			}
			var d = function() {
				if (f.val() != "" && f.val() != o) {
					if (g.onlyNumber) {
						if (!r.test(f.val().replace(/\s/g, ""))) {
							f.val(f.val().replace(/\D/g, ""))
						}
					}
					var j = g.max - (g.max % 4);
					if (j > 0 && f.val().length > j) {
						f.val(f.val().replace(/\s/g, "").substring(0, j))
					}
					f.val(f.val().replace(/\s/g, "").replace(/(\d{4})(?=\d)/g,
							"$1" + g.deimiter));
					o = f.val();
					if (h) {
						h()
					}
				}
			};
			f.bind("dragenter", function() {
				return false
			}).bind("onpaste", function() {
				return !clipboardData.getData("text").match(/\D/)
			}).bind("focus", function() {
				q = setInterval(d, 200)
			}).bind("blur", function() {
				if (q != null) {
					clearInterval(q)
				}
			})
		};
		var t = function(c) {
			c = c.replace(/\s/g, "");
			if (c.length == 12 && parseInt(c) > 0) {
				if (!isLoaded) {
					return
				}
				isLoaded = false;
				$.ajax({
					url : "/user/getCardInfoByAccount.action",
					type : "get",
					data : "id=" + c,
					success : function(p) {
						if (p.cardState == 1) {
							x.html('充值卡金额：<span class="orange">￥'
									+ p.wasteMoney + ".00</span>");
							handle();
						} else {
							if (p.cardState == 2) {
								v("充值卡号已失效！", a, -43);
								z()
							} else {
								v("充值卡号不正确！", a, -43);
								z()
							}
						}
						isLoaded = true
					}
				});
			} else {
				z()
			}
		};
		a.bankInput(a, {
			min : 0,
			max : 14
		}, function() {
			t(a.val());
		});
		/*u.bankInput(u, {
			min : 0,
			max : 8,
			deimiter : ""
		});*/
	};
	isLoaded = true;
	$.getScript("/js/pagedialog.js?v=130830", b)
});
