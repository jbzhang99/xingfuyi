//我的商城
$(".head-user-menu").mouseover(function() {
	$(".head-user-menu dd").css("display", "block");
	$(".head-user-menu dt").css("border-bottom", '0');
	$(".head-user-menu dt b").css({
		"top" : "7px",
		"border-style" : "dashed dashed solid",
		"border-color" : "transparent transparent #ccc"
	});
}).mouseout(function() {
	$(".head-user-menu dd").css("display", "none");
	$(".head-user-menu dt").css("border-bottom", '1px solid #efefef');
	$(".head-user-menu dt b").css({
		"top" : "9px",
		"border-style" : "dashed dashed solid",
		"border-color" : " #CCC transparent transparent"
	});
});
// 

// 首页商品分类
var obj = $(".odd:eq(0)");
$(".dd-inner .odd").mouseover(
		function() {
			$(".odd").eq($(this).index()).addClass("hover").siblings()
					.removeClass("hover");
			var obj = $(this);
			var index = $(this).data('index');
			$(".item-sub").css("display", "none");
			$("#index" + index).css("display", "block");
			$(this).parent().siblings().css("display", "block");

			$(".item-sub").hover(
					function() {
						$(this).css("display", "block").parent().css("display",
								"block");
					},
					function() {
						$(this).css("display", "none").parent().css("display",
								"none");
						$(".odd").removeClass("hover");
					});
		}).mouseout(function() {
	$(".odd").eq($(this).index()).removeClass("hover");
	$(this).parent().siblings().css("display", "none");
	$(".item-sub").hover(function() {
		$(".odd").eq($(this).index()).addClass("hover");
	}, function() {
		$(".odd").removeClass("hover")
	});
});


$(function() {
   //首页轮播
	if( $('.index-lunbo-container').length>0 ){
        $(".index-lunbo-container").slide({mainCell:".bd",autoPlay:true,pnLoop:true,effect:"left",delayTime:"400"});
    }
    
	// setTimeout("takeCount()", 1000);
	// 首页Tab标签卡滑门切换
	$(".tabs-nav > li > h3").bind(
			'mouseover',
			(function(e) {
				if (e.target == this) {
					var tabs = $(this).parent().parent().children("li");
					var panels = $(this).parent().parent().parent().children(
							".tabs-panel");
					var index = $.inArray(this, $(this).parent().parent().find(
							"h3"));
					if (panels.eq(index)[0]) {
						tabs.removeClass("tabs-selected").eq(index).addClass(
								"tabs-selected");
						panels.addClass("tabs-hide").eq(index).removeClass(
								"tabs-hide");
					}
				}
			}));
});

// 首页楼层标签的切换
var interval;
$(document).ready(function() {
	var $tab_li = $('.tab li');
	$tab_li.hover(function() {
		$(this).addClass('tab-selected').siblings().removeClass(
				'tab-selected');
		var index = $tab_li.index(this);
		$('.lazy-mc .lazy-main').eq(index).removeClass("hide")
				.siblings(".lazy-main").addClass("hide");
	});
				
	$(".p-img img,.add-p-img img").lazyload({
		effect : "fadeIn",
		skip_invisible : false,
		failurelimit : 10,
		placeholder : resource_path_ + '/img/NOTICE.jpg'
	}); 
});

/**
 * 动态加载限时抢购
 */
function refreshMyQiangou(){
 	 $.ajax({
		type:"GET",
		url:domain+"/indexqianggou.html",
		dataType:"html",
		async : true,
		success:function(data){
			//加载数据
			$("#priviewMyQiangou").html(data);
		},
		error:function(){
			jAlert("异常，请重试！");
		}
	});
}

