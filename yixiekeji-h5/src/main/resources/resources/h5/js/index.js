$(function(){
	// 导航
	$('#fa-bars').on("click",function(){
      $("#nav").toggleClass('addnav');
	});
	// 点击菜单变红
	$("#nav-2-bar div").on("click",function(){
       var _this=$(this);
           _this.addClass("clr53").siblings().removeClass("clr53");

	});

	// 点击筛选
	$("#list-filter").on("click",function(){
	  if($("#slidebar").hasClass("show")==false){
	      $("#header").addClass('pull');
	      $("#container").addClass('pull');
	      $("#push_msk").css("display","block");
	      $("#slidebar").addClass("show")
	      $("html,body").css({"overflow":"hidden"});
	   }else{
          $("html,body").css({"overflow":"visible"});
	   }
	});
	$("#sliderarrow").on("click",function(){
		
       if($("#slidebar").hasClass("show")==true){
       	   $("#slidebar").removeClass("show")
       	   $("#header").removeClass('pull');
       	   $("#container").removeClass('pull');
       	   $("#push_msk").css("display","none");
       	   
		   $("html,body").css({"overflow":"visible"});
       }else{
          $(window).bind('touchmove', function(e) {
//				e.preventDefault();
//				e.stopImmediatePropagation();
  		  });
       	  
       	  $("html,body").css({"overflow":"hidden"});
       }
	});
	$("#slidercomfirm").on("click",function(){
		$('#sliderarrow').trigger('click');
	});
	$('.push_msk').on('touchmove', function(e) {
		//alert("v")
		
			$('#sliderarrow').trigger('click');
		
		return false;
	});

	// 筛选二级菜单
	$(".menu-2-ul li").on("click",function(){
	   var _this=$(this);
       _this.addClass("clr53").find(".fa-check").css("display","inline-block");
       _this.siblings().removeClass("clr53").find(".fa-check").css("display","none");
	});

	// 商品详情
	function minus() {
		var a = parseInt($("#number").val(), 10);
		if (a <= 1) {
			$("#number").val(1);
			$("#amount").html("1")
		} else {
			a--;
			$("#number").val(a);
			$("#amount").html(a)
		}
	}

	function plus() {
		var a = parseInt($("#number").val(), 10);
		if (a >= 999) {
			$("#number").val(1);
			$("#amount").html("1")
		} else {
			a++;
			$("#number").val(a);
			$("#amount").html(a )
		}
	}
	$("#minus").on("click",function(){
		 minus();
	})
	$("#plus").on("click",function(){
		 plus()
	})

	// 商品评价
	//tab切换
	$("#evaluat-nav div").on("click",function(){
      var _this=$(this);
      var index=_this.index();
         _this.addClass("clr53").siblings().removeClass("clr53");
         $("#content>div").eq(index).css("display","block").siblings().css("display","none");
	});

	// 好评百分比展示
	$(".rate").each(function(){
		var rate=$(this).html();
	    // alert(rate)
	    $(this).siblings(".perecnt").find("span").width(rate);
	})

	// 商品分类
	$("#swiper-container").on("click",".swiper-slide",function(){
		 $(this).addClass('slide-active').siblings().removeClass('slide-active');
	});
	
	// 我的收藏 tab切换
	$("#colect_nav div").on("click",function(){
		var _this=$(this);
		_this.addClass('clr53').siblings().removeClass('clr53');
		var index=_this.index();
		$("#container > div").eq(index).css("display","block").siblings().css("display","none");

	});

    $(".menubox").on("click",function(){
	      var _this=$(this),
	          s=_this.find(".fa-angle-down"),
	          g=_this.children(".menu-2-ul"),
	        s_2=_this.siblings().find(".fa-angle-down"),
	        g_2=_this.siblings().children(".menu-2-ul"),
	          t=_this.children("dd"),
	        t_2=_this.siblings().children("dd");
	       
	        if(s.hasClass("addangle-down")){
	        	t.slideUp();
	        	g.slideUp();
	        	s.removeClass("addangle-down");
	        }else{
	        	t.slideDown();
	        	g.slideDown();
	        	s.addClass("addangle-down");
	        }
      
          
    });

    // 首页 搜索框
    var l=$("#search-cover")
    $(window).scroll(function(){
		var h=$(window).scrollTop();
		if(h>0){
		  l.css("opacity","0.85");
		}else{
		  l.css("opacity","0");
		}

    });
    
//    //多惠专区
//   	new Swiper ('.bestie-new-container', {
//        slidesPerView: 2.5,
//        paginationClickable:true,
//        spaceBetween:0
//      });
//   	
// // 掌上秒杀
//   	new Swiper ('.seckill-new-container', {
//       slidesPerView: 2.5,
//       paginationClickable:true,
//       spaceBetween:0
//     });
});