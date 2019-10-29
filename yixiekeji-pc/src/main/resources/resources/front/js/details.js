// ==================放大镜插件=====================

//=====================全局函数========================
//Tab控制函数
function tabs(tabId, tabNum){
	//设置点击后的切换样式
	$(tabId + " .tab li").removeClass("curr");
	$(tabId + " .tab li").eq(tabNum).addClass("curr");
	//根据参数决定显示内容
	$(tabId + " .tabcon").hide();
	$(tabId + " .tabcon").eq(tabNum).show();
}
//=====================全局函数========================

//==================图片详细页函数=====================
//鼠标经过预览图片函数
function preview(img){
	$("#preview .jqzoom img").attr("src",$(img).attr("src"));
	$("#preview .jqzoom img").attr("jqimg",$(img).attr("bimg"));
}

//图片放大镜效果
$(function(){
	$(".jqzoom").jqueryzoom({xzoom:400,yzoom:400});
});

//图片预览小图移动效果,页面加载时触发
$(function(){
	var tempLength = 0; //临时变量,当前移动的长度
	var viewNum = 5; //设置每次显示图片的个数量
	var moveNum = 2; //每次移动的数量
	var moveTime = 300; //移动速度,毫秒
	var scrollDiv = $(".spec-scroll .items ul"); //进行移动动画的容器
	var scrollItems = $(".spec-scroll .items ul li"); //移动容器里的集合
	var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); //计算总长度,总个数*单个长度
	  
	//下一张
	$(".spec-scroll .next").bind("click",function(){
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	//上一张
	$(".spec-scroll .prev").bind("click",function(){
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
});
//==================图片详细页函数=====================


//**************************************************************
// jQZoom allows you to realize a small magnifier window,close
// to the image or images on your web page easily.
//
// jqZoom version 2.1
// Author Doc. Ing. Renzi Marco(www.mind-projects.it)
// First Release on Dec 05 2007
// i'm searching for a job,pick me up!!!
// mail: renzi.mrc@gmail.com
//**************************************************************

(function($){

	$.fn.jqueryzoom = function(options){
		var settings = {
				xzoom: 200,//zoomed width default width
				yzoom: 200,//zoomed div default width
				offset: 10,	//zoomed div default offset
				position: "right",//zoomed div default position,offset position is to the right of the image
				lens:1, //zooming lens over the image,by default is 1;
				preload: 1
			};

			if(options) {
				$.extend(settings, options);
			}

		    var noalt='';
		    $(this).hover(function(){

		    var imageLeft = this.offsetLeft;
		    var imageRight = this.offsetRight;
		    var imageTop =  $(this).get(0).offsetTop;
		    var imageWidth = $(this).children('img').get(0).offsetWidth;
		    var imageHeight = $(this).children('img').get(0).offsetHeight;


	        noalt= $(this).children("img").attr("alt");

		    var bigimage = $(this).children("img").attr("jqimg");

	        $(this).children("img").attr("alt",'');

		    if($("div.zoomdiv").get().length == 0){

		    $(this).after("<div class='zoomdiv'><img class='bigimg' src='"+bigimage+"'/></div>");


		    $(this).append("<div class='jqZoomPup'>&nbsp;</div>");

		    }


		    if(settings.position == "right"){

	        if(imageLeft + imageWidth + settings.offset + settings.xzoom > screen.width){

	        leftpos = imageLeft  - settings.offset - settings.xzoom;

	        }else{

		    leftpos = imageLeft + imageWidth + settings.offset;
	        }
		    }else{
		    leftpos = imageLeft - settings.xzoom - settings.offset;
		    if(leftpos < 0){

	        leftpos = imageLeft + imageWidth  + settings.offset;

		    }

		    }

		    $("div.zoomdiv").css({ top: imageTop,left: leftpos });

		    $("div.zoomdiv").width(settings.xzoom);

		    $("div.zoomdiv").height(settings.yzoom);

	        $("div.zoomdiv").show();

	        if(!settings.lens){
	          $(this).css('cursor','crosshair');
			}




				   $(document.body).mousemove(function(e){



	               mouse = new MouseEvent(e);

	               /*$("div.jqZoomPup").hide();*/
	               var bigwidth=0;
	               var bigheight=0;
	               	
	               if($(".bigimg").get(0).offsetWidth==800&&$(".bigimg").get(0).offsetHeight==800){
	   	          	 		bigwidth = $(".bigimg").get(0).offsetWidth;
	   				    	bigheight = $(".bigimg").get(0).offsetHeight;
	   	          }else{
	   	        	  	 bigwidth = 800;
						 bigheight = 800;
	   	          }
	   	          			
	               
//				    var bigwidth = $(".bigimg").get(0).offsetWidth;
//
//				    var bigheight = $(".bigimg").get(0).offsetHeight;

				    var scaley ='x';

				    var scalex= 'y';


				    if(isNaN(scalex)|isNaN(scaley)){

				    var scalex = (bigwidth/imageWidth);

				    var scaley = (bigheight/imageHeight);




				    $("div.jqZoomPup").width((settings.xzoom)/scalex );

		    		$("div.jqZoomPup").height((settings.yzoom)/scaley);

	                if(settings.lens){
	                $("div.jqZoomPup").css('visibility','visible');
					}

				   }



	                xpos = mouse.x - $("div.jqZoomPup").width()/2 - imageLeft;

	                ypos = mouse.y - $("div.jqZoomPup").height()/2 - imageTop ;

	                if(settings.lens){

	                xpos = (mouse.x - $("div.jqZoomPup").width()/2 < imageLeft ) ? 0 : (mouse.x + $("div.jqZoomPup").width()/2 > imageWidth + imageLeft ) ?  (imageWidth -$("div.jqZoomPup").width() -2)  : xpos;

					ypos = (mouse.y - $("div.jqZoomPup").height()/2 < imageTop ) ? 0 : (mouse.y + $("div.jqZoomPup").height()/2  > imageHeight + imageTop ) ?  (imageHeight - $("div.jqZoomPup").height() -2 ) : ypos;

	                }


	                if(settings.lens){

	                $("div.jqZoomPup").css({ top: ypos,left: xpos });

	                }



					scrolly = ypos;

					$("div.zoomdiv").get(0).scrollTop = scrolly * scaley;

					scrollx = xpos;

					$("div.zoomdiv").get(0).scrollLeft = (scrollx) * scalex ;


				    });
		    },function(){

	           $(this).children("img").attr("alt",noalt);
		       $(document.body).unbind("mousemove");
		       if(settings.lens){
		       $("div.jqZoomPup").remove();
		       }
		       $("div.zoomdiv").remove();

		    });

	    count = 0;

		if(settings.preload){

		$('body').append("<div style='display:none;' class='jqPreload"+count+"'>sdsdssdsd</div>");

		$(this).each(function(){

	    var imagetopreload= $(this).children("img").attr("jqimg");

	    var content = jQuery('div.jqPreload'+count+'').html();

	    jQuery('div.jqPreload'+count+'').html(content+'<img src=\"'+imagetopreload+'\">');

		});

		}

	}

})(jQuery);

function MouseEvent(e) {
this.x = e.pageX
this.y = e.pageY


}





// ================================================

//加入购物车和立即购买

$(".close").click(function(){
	$(this).parent(".tzm-border").css("display",'none');
});
//选择商品
$("#ChooseColor .item,#ChooseVersion .item").click(function(){
	$(this).addClass("selected").siblings().removeClass("selected");
});

//级别菜单
$("#sp-category dt s").click(function(){
	var open = $(this).parent().parent();
	if(open.hasClass('open')){
		open.removeClass("open");
	}else{
		open.addClass("open");

	}

});

// 商品详情商品介绍
var obj=$(".trig-item:eq(0)");//获取每个li索引
$(".trig-item").click(function(){
	$(".trig-item").eq($(this).index()).addClass("curr").siblings().removeClass("curr");
	var obj=$(this);
	var table=$(this).data('table');
	$(".b-table").removeClass("bcent-table");
	$("#table"+table).addClass("bcent-table");
});
// 评论
var obt=$(".comment-li:eq(0)");//获取每个li索引
$(".comment-li").click(function(){
	$(".comment-li").eq($(this).index()).addClass("curr").siblings().removeClass("curr");
	var obt=$(this);
	var box=$(this).data('box');
	$(".comment-none").removeClass("comment-block");
	$("#box"+box).addClass("comment-block");
});

//咨询
 var obtn=$(".advice-li:eq(0)");//获取每个li索引
$(".advice-li").click(function(){
	$(".advice-li").eq($(this).index()).addClass("curr").siblings().removeClass("curr");
	var obtn=$(this);
	var number=$(this).data('number');
	$(".advice-none").removeClass("advice-play");
	$("#number"+number).addClass("advice-play");
});

//购买数量加减

//获得文本框对象
   	var t = $("#buy-num");
//初始化数量为1,并失效减

//初始化数量为1,并失效减
$('#min').attr('disabled',true);
    //数量增加操作
$("#add").click(function(){    
    t.val(parseInt(t.val())+1)
    if (parseInt(t.val())!=1){
        $('#min').attr('disabled',false);
    }
  
});

 //数量减少操作
$("#min").click(function(){
    t.val(parseInt(t.val())-1);
    if (parseInt(t.val())==1){
        $('#min').attr('disabled',true);
    }
  
});
