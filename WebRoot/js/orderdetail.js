$(function() {
	var a = function() {
		var f = $("#hideOrderID").val();
		var i = 0;
		var d = $("#ulAddress");
		var e = function(m, l, k, j) {
			$.PageDialog.fail(m, l, k, j)
		};
		var h = function(k, j) {
			$.PageDialog.ok(k, j)
		};
		function c(k, j) {
			$.PageDialog.confirm(k, j)
		}
		if (d.length > 0) {
			d.children("li").each(
					function() {
						var k = $(this);
						if (k.children("i").hasClass("z-bank-Roundsel")) {
							i = parseInt(k.attr("id"));
							var j = $('<a href="addressedit-' + f + ".html?cid="
									+ i + '" class="z-Modify">修改</a>');
							k.append(j)
						}
						k
								.click(function() {
									i = parseInt(k.attr("id"));
									k.children("i").removeClass("z-bank-Round")
											.addClass("z-bank-Roundsel");
									k.siblings().children("i").removeClass(
											"z-bank-Roundsel").addClass(
											"z-bank-Round");
									k.siblings().find("a.z-Modify").remove();
									if (k.find("a.z-Modify").length == 0) {
										var l = $('<a href="addressedit-' + f
												+ ".html?cid=" + i
												+ '" class="z-Modify">修改</a>');
										k.append(l)
									}
								})
					});
			$("#btnConfimAddr").click(
					function() {
						if (i > 0) {
							/*var j = function(k) {
								if (k.code == 0) {
									h("提交成功", function() {
										location.replace("/user/orderdetail-"
												+ f + ".html")
									})
								} else {
									if (k.code == 1) {
										e("请检查收货地址是否合法")
									} else {
										e("提交失败，请重试[" + k.code + "]")
									}
								}
							};*/
							var p = {
								//userId : c,
								id : i,
								orderRemarks : "",
								postDate : "时间不限",
								hidOrderNO : f
							};
							$.ajax({
								url : "/user/OrderDetailAddAddress.html",
								data : "userJSON="
										+ encodeURIComponent(j2s(p)),
								success : function(j) {
									if (j == "success") {
										h("提交成功", function() {
											location.replace("/user/orderdetail-" + f + ".html")
										})
									}else{
										e("提交失败，请重试")
									}
								},
								error : function() {
									alert("网络错误，请与管理员联系！");
									location.reload()
								}
							});
							/*GetJPData("", "confirmAddr", "oid=" + f + "&cid="
									+ i, j)*/
						} else {
							e("请先选择收货地址")
						}
					})
		}
		var g = $("#btnShiped");
		var b = function() {
			var j = function() {
				var oid = $("#hideOrderID").val();
				$.ajax({
						url : "/user/confirmOrderDetail.html",
						data : "id=" + oid,
						success : function(i) {
							if (i == "success") {
								h("提交成功", function() {
									location.replace(location.href);
								})
							} else {
								e("提交失败，请重试");
							}
						},
						error : function() {
							alert("\u7f51\u7edc\u9519\u8bef\uff0c\u8bf7\u4e0e\u7ba1\u7406\u5458\u8054\u7cfb\uff01");
							location.reload()
						}
					});
				return false
				/*var k = function(l) {
					if (l.code == 0) {
						h("提交成功", function() {
							location.replace(location.href)
						})
					} else {
						if (l.code == 1) {
							e("提交失败，请重试[" + l.code + "]")
						} else {
							location.reload()
						}
					}
				};
				GetJPData("", "confirmShiped", "oid=" + f, k)*/
			};
			c("您确定收到货了吗？", j)
		};
		if (g.length > 0) {
			g.click(b)
		}
		function j2s(k) {
			var j = [];
			for ( var h in k) {
				k[h] = typeof k[h] == "string" ? '"' + k[h] + '"'
						: (typeof k[h] == "object" ? e(k[h]) : k[h]);
				j.push(h + ":" + k[h])
			}
			return "{" + j.join(",") + "}"
		}
	};
	Base.getScript("/js/pagedialog.js?v=130826", a)
});