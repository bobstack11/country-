$(function(){var b=function(){$("#divPeriod").touchslider();$.getScript("/js/goodspicslider.js",function(){$("#sliderBox").picslider()});$("div.pOngoing").click(function(){location.href="/products/"+$(this).attr("codeid")+".html"})};$.getScript("/js/periodslider.js",b)});