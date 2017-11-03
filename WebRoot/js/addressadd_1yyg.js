$(function() {
	var a = $("#hideOrderID").val();
	var e = $("#hidIsActOrder").val();
	var j = 0;
	var k = $("#txtUserName");
	var r = $("#txtMobile");
	var m = $("#selAreaA");
	var p = $("#selAreaB");
	var q = $("#selAreaC");
	var t = $("#selAreaD");
	var l = $("#txtAddr");
	var o = $("#txtZip");
	var s = $("#spDefult");
	var n = "输入姓名";
	var d = "联系电话";
	var h = "请选择所在省份";
	var g = "请选择所在城市";
	var c = "请选择所在地区";
	var b = "请选择所在街道或乡镇";
	var u = "详细地址";
	var f = "邮政编码";
	var i = function() {
		var v = function(H, G, F, E) {
			var D = function(I) {
				if (I.code == 0) {
					G.empty();
					var M = I.data;
					var L = h;
					if (E == "B") {
						L = g
					} else {
						if (E == "C") {
							L = c
						} else {
							if (E == "D") {
								L = b
							}
						}
					}
					var K;
					G.append($('<option value="-1" zip="">' + L + "</option>"));
					for (var J = 0; J < M.length; J++) {
						K = $('<option value="' + M[J].areaID + '" zip="'
								+ M[J].areaZip + '">' + M[J].areaName
								+ "</option>");
						if (M[J].areaID == F) {
							K.attr("selected", true)
						}
						G.append(K)
					}
				}
				if (E == "D") {
					if (G.find("option").length <= 1) {
						G.parent().parent().parent().hide()
					} else {
						G.parent().parent().parent().show()
					}
				}
			};
			if (H > -1) {
				GetJPData("", "getChildArea", "areaID=" + H, D)
			}
		};
		var z = function(E) {
			var D = /(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}1[0-9]{10}$)/;
			return D.test(E)
		};
		var C = function(E) {
			var D = /^\d{6}$/;
			return D.test(E)
		};
		var A = function(E) {
			var D = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
			return D.test(E)
		};
		var y = function(G, F, E, D) {
			$.PageDialog.fail(G, F, E, D)
		};
		var B = function(E, D) {
			$.PageDialog.ok(E, D)
		};
		function x(E, D) {
			$.PageDialog.confirm(E, D)
		}
		var w = function(H) {
			var E = -1;
			var J = -1;
			var F = -1;
			var D = -1;
			var I = function(K) {
				K.focus(function() {
					K.css("color", "#666666")
				}).blur(function() {
					if (K.val() == "") {
						K.val(defVal).css("color", "#bbbbbb")
					}
				})
			};
			I(k);
			I(r);
			I(l);
			I(o);
			m.change(function() {
				var K = m.val();
				if (K == -1) {
					m.css("color", "#bbbbbb");
					p.html('<option value="-1">' + g + "</option>").css(
							"color", "#bbbbbb");
					q.html('<option value="-1">' + c + "</option>").css(
							"color", "#bbbbbb");
					t.html('<option value="-1">' + b + "</option>").css(
							"color", "#bbbbbb")
				} else {
					m.css("color", "#666666");
					p.html('<option value="-1">' + g + "</option>");
					q.html('<option value="-1">' + c + "</option>");
					t.html('<option value="-1">' + b + "</option>");
					o.val("");
					v(K, p, 0, "B")
				}
			});
			p.change(function() {
				var K = p.val();
				if (K == -1) {
					p.css("color", "#bbbbbb");
					q.html('<option value="-1">' + c + "</option>").css(
							"color", "#bbbbbb");
					t.html('<option value="-1">' + b + "</option>").css(
							"color", "#bbbbbb")
				} else {
					p.css("color", "#666666");
					q.html('<option value="-1">' + c + "</option>");
					t.html('<option value="-1">' + b + "</option>");
					v(K, q, 0, "C")
				}
			});
			q.change(function() {
				var L = q.val();
				if (L == -1) {
					q.css("color", "#bbbbbb");
					t.html('<option value="-1">' + b + "</option>").css(
							"color", "#bbbbbb")
				} else {
					q.css("color", "#666666");
					t.html('<option value="-1">' + b + "</option>");
					var K = q.find("option:selected").attr("zip");
					o.val(K);
					v(L, t, 0, "D")
				}
			});
			t.change(function() {
				var K = t.val();
				if (K == -1) {
					t.css("color", "#bbbbbb")
				} else {
					t.css("color", "#666666")
				}
			});
			if (E == -1) {
				v(1, m, 0, "A");
				m.css("color", "#bbbbbb");
				p.html('<option value="-1">' + g + "</option>").css("color",
						"#bbbbbb");
				q.html('<option value="-1">' + c + "</option>").css("color",
						"#bbbbbb");
				t.html('<option value="-1">' + b + "</option>").css("color",
						"#bbbbbb");
				o.css("color", "#bbbbbb")
			} else {
				m.css("color", "#666666");
				p.css("color", "#666666");
				q.css("color", "#666666");
				o.css("color", "#666666");
				v(1, m, E, "A");
				v(E, p, J, "B");
				v(J, q, F, "C");
				v(F, t, D, "D")
			}
			var G = false;
			s.click(function() {
				s.children("span").attr("class",
						G ? "z-pay-ment" : "z-pay-mentsel");
				G = !G
			});
			$("#btnCancel").click(function() {
				history.go(-1)
			});
			$("#btnSure")
					.click(
							function() {
								var P = $(window).width();
								var O = (P) / 2 - k.offset().left - 127;
								if (k.val() == "" || k.val() == n) {
									y("请填写收货人姓名", k, 15, O);
									k.focus();
									return false
								}
								if (r.val() == d) {
									y("请填写联系电话", r, 15, O);
									r.focus();
									return false
								}
								if (!z(r.val())) {
									y("联系电话格式不正确", r, 15, O);
									r.focus();
									return false
								}
								var N = m.val();
								if (N == -1) {
									y("请选择省份", m, 15, O);
									return false
								}
								var Q = p.val();
								if (Q == -1) {
									y("请选择城市", p, 15, O);
									return false
								}
								var K = q.val();
								if (K == -1) {
									y("请选择地区", q, 15, O);
									return false
								}
								var M = t.val();
								if (t.find("option").length > 1) {
									if (M == -1) {
										y("请选择街道或乡镇", t, 15, O);
										return false
									}
								}
								if (l.val() == "" || l.val() == u) {
									y("请填写详细地址", l, 15, O);
									l.focus();
									return false
								}
								var L = o.val();
								if (L != "" && L != f) {
									if (!C(L)) {
										y("邮编格式不正确", o, 15, O);
										return false
									}
								}
								var R = m.find("option:selected").text() + " "
										+ p.find("option:selected").text()
										+ " "
										+ q.find("option:selected").text();
								if (M > 0) {
									R = R + " "
											+ t.find("option:selected").text()
								}
								$
										.post(
												"http://weixin.1yyg.com/JPData",
												{
													action : "editUserContact",
													Name : k.val(),
													Phone : r.val(),
													OrderAddress : R,
													Address : l.val(),
													Zip : L,
													IsDefault : G ? 1 : 0,
													AddrID : H == null ? 0 : j,
													OID : a,
													CID : q.val(),
													DID : M
												},
												function(S) {
													if (S.code == 11) {
														B(
																"提交成功",
																function() {
																	if (e == "1") {
																		window.location
																				.replace("http://weixin.1yyg.com/member/actorderdetail-"
																						+ a
																						+ ".do")
																	} else {
																		window.location
																				.replace("http://weixin.1yyg.com/member/orderdetail-"
																						+ a
																						+ ".do")
																	}
																})
													} else {
														if (S.code == 12) {
															B(
																	"添加成功",
																	function() {
																		window.location
																				.replace(location.href)
																	})
														} else {
															if (S.code == 3) {
																y("收货地址不能超过四个")
															} else {
																y("提交失败")
															}
														}
													}
												}, "json")
							})
		};
		w()
	};
});
