/**
 * 
 */
/*左侧导航显示隐藏*/
$(".show-hide").click(function(){
	var base=200;
	var left=$(".left-nav").css("left");
	left=parseInt(left.substring(0,left.indexOf("px")));
	$(".left-nav").animate({
		   left: -left-base, opacity: 'show'//-200-0
	}, 500);
	$("#main-body").animate({
		   left: left==0?0:200, opacity: 'show'//0-200
	}, 500);
});
