$.fn.StartTimeOut = function(t, I) {
	var u = $(this);
	var L = new Date();
	L.setSeconds(L.getSeconds() + I);
	var A = 0;
	var x = 0;
	var y = 0;
	var B = function() {
		var b = new Date();
		if (L > b) {
			var a = parseInt((L.getTime() - b.getTime()) / 1000);
			var c = a % 60;
			A = parseInt(a / 60);
			x = parseInt(c);
			if (c >= x) {
				y = parseInt((c - x) * 10)
			} else {
				y = 0
			}
			setTimeout(B, 3000)
		}
	};
	var K = u.find("b").eq(0);
	var C = u.find("b").eq(1);
	var H = u.find("i").eq(0);
	var v = u.find("i").eq(1);
	var F = 9;
	var z = function() {
		F--;
		if (F < 0) {
			F = 9
		}
		v.html(F);
		setTimeout(z, 10)
	};
	var J = function() {
		v.html("0");
		u.find("p").children("em").html("").next("cite").html("正在计算...");
		var a = null;
		var b = function() {
			$
					.ajax({
						url : "/lottery/lotteryUtil.html",
						data : "id=" + t,
						success : function(c) {
							if (c == "true") {
								$
										.ajax({
											url : "/lottery/LatestlotteryByProductId.html",
											data : "id=" + t,
											success : function(d) {
												if (d != "false") {
													u.click(
															function() {
																location.href = "/lotteryDetail/" + t
																		+ ".html"
															}).find("p").html(
															'<em>获得者</em><span><a href="/u/'
																	+ d.userId
																	+ '.html" class="blue z-user">'
																	+ d.userName + "</a></span>");
													if (a != null) {
														clearInterval(a);
														a = null
													}
													setInterval(function() {
														u.remove();
													}, 60000);
													
													/*u
															.find("p")
															.replaceWith(
																	"<p onclick=\"location.href='/lotteryDetail/"
																			+ t
																			+ '.html\'">\u606d\u559c<span class="z-user blue" style="color:#fff; text-decoration:underline;">'
																			+ d.userName
																			+ "</span>\u83b7\u5f97 (\u7b2c"
																			+ d.productPeriod
																			+ "\u671f)"
																			+ d.productName
																			+ '<b class="z-arrow"></b></p>');
													if (a != null) {
														clearInterval(a);
														a = null
													}
													setInterval(function() {
														u.remove()
													}, 60000)*/
												}
											}
										})
							}
						}
					})
		};
		a = setInterval(b, 2000)
	};
	var D = function() {
		y--;
		if (y < 1) {
			if (x < 1) {
				if (A < 1) {
					J();
					return
				} else {
					A--
				}
				x = 59
			} else {
				x--
			}
			y = 9
		}
		setTimeout(D, 100)
	};
	var G = 0, w = 0;
	var E = function() {
		H.html(y);
		if (G != x) {
			if (x < 10) {
				C.html("0" + x)
			} else {
				C.html(x)
			}
			G = x
		}
		if (w != A) {
			if (A < 10) {
				K.html("0" + A)
			} else {
				K.html(A)
			}
			w = A
		}
		setTimeout(E, 100)
	};
	B();
	D();
	z();
	E()
};
