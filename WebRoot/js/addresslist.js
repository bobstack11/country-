$(function() {
	var a = function() {
		//var d = $("#hideOrderID").val();
		//var i = $("#hidOrderState").val();
		var b = 0;
		var c = function(r, q, p, o) {
			$.PageDialog.fail(r, q, p, o)
		};
		var g = function(p, o) {
			$.PageDialog.ok(p, o)
		};
		function m(p, o) {
			$.PageDialog.confirm(p, o)
		}
		
		//var k = $("#btnUpdateAddr");
		$(".addre-list").children("li").each(
				function() {
					var o = $(this);
					/*if (o.find("i.z-set").is(":visible")) {
						b = parseInt(o.attr("id"));
						k.attr("href", "AddressEdit.html?cid=" + b)
					}*/
					o.click(function() {
						b = parseInt(o.attr("id"));
						location.href = "AddressEdit.html?cid=" + b;
					})
					/*o.click(function() {
						b = parseInt(o.attr("id"));
						o.find("i.z-set").show();
						o.siblings().find("i.z-set").hide();
						$("#btnUpdateAddr").attr("href",
								"AddressEdit.html?cid=" + b)
					})*/
				});
		
	};
	Base.getScript("/js/pagedialog.js?v=130826", a)
});