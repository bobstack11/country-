$(function() {
	var a = function() {
		var b = $("#a_smallpay");
		var c = function(k, e, f, d, l) {
			var g = 255;
			var j = 126;
			if (typeof (d) != "undefined") {
				g = d
			}
			if (typeof (d) != "undefined") {
				j = l
			}
			var m = '<div class="clearfix m-round u-tipsEject"><div class="u-tips-txt">'
					+ k
					+ '</div><div class="u-Btn"><div class="u-Btn-li" style="display:none;"><a href="javascript:;" id="btnMsgCancel" class="z-CloseBtn">取消</a></div><div class="u-Btn-li"><a id="btnMsgOK" href="javascript:;" class="z-DefineBtn">确定</a></div></div></div>';
			var i = function() {
				var n = $("#pageDialog");
				n.find("a.z-DefineBtn").click(function() {
					if (typeof (e) != "undefined" && e != null) {
						e()
					}
					h.close()
				});
				n.find("a.z-CloseBtn").click(function() {
					if (typeof (f) != "undefined" && f != null) {
						f()
					}
					h.cancel()
				})
			};
			var h = new $.PageDialog(m, {
				W : g,
				H : j,
				close : true,
				autoClose : false,
				ready : i
			})
		};
		if (b.length > 0) {
			b.bind("click", function() {
				c("设置小额免密需先开启支付密码")
			})
		}
	};
	$.getScript("/js/pagedialog.js?v=130826", a)
});