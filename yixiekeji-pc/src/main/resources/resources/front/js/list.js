$(function(){
//商品分类
    var sortall=$("#yy-sortall");
	$("#CateGorys").hover(function(){
		sortall.css("display","block");
         $(this).find("b").css("background-position","-65px -21px");
	},function(){
		sortall.css("display","none");
         $(this).find("b").css("background-position","-65px 0px");
	});

$(".list-cashcade").mouseover(function(){
		$(this).addClass("hover");
		$(this).children(":last").css("display",'block');
}).mouseout(function(){
		$(this).removeClass("hover");
		$(this).children(":last").css("display",'none');
		
});

//商品列表更多选项的点击展开效果
var content = '';
$("#SelectMore").mouseover(function(){
	$(".select-more-tag").css("border-color","#e4393c");
	$(".select-wrap").css({"color":"#e4393c","border-color":"#fff #e4393c #e4393c #e4393c"});
	content = $(".select-wrap").html();
	if(content =='更多<i></i>'){
		$(".select-wrap i").css("background-position","4px -10px");
	}
	if(content =='收起<i></i>'){
		$(".select-wrap i").css("background-position",'4px -45px');
	}
}).mouseout(function(){
	$(".select-more-tag").css("border-color","#ddd");
	$(".select-wrap").css({"color":"#333","border-color":"#fff #ddd #ddd #ddd"});
	content = $(".select-wrap").html();
	if(content =="更多<i></i>"){
		$(".select-wrap i").css("background-position","4px 7px");
	}
	if(content =='收起<i></i>'){
		$(".select-wrap i").css("background-position",'4px -28px');
	}
	
});

//默认显示两个属性
$(".select-wrap").click(function(){
	var content = $(this).html();
	console.info(content);
	$(".selector-fold").each(function(index,elem){
		if($(elem).hasClass("tag-display")){
			$(elem).removeClass("tag-display");
		}else{
			if(index>=3){
				$(elem).addClass("tag-display");	
			}
		}
	});
	
	if(content.indexOf("收起")!=-1){
		$(this).html("更多<i></i>");
		$(".select-wrap i").hover(function(){
			$(this).css("background-position",'4px -10px');
		},function(){
			$(this).css("background-position",'4px 7px');
		})
		$(".select-more-tag").css("border-color","#ddd");
		$(".select-wrap").css({"color":"#333","border-color":"#fff #ddd #ddd #ddd"});
	}else{
		$(this).html("收起<i></i>");
		$(".select-wrap i").css("background-position",'4px -28px');
		$(".select-wrap i").hover(function(){
			$(this).css("background-position",'4px -45px');
		},function(){
			$(this).css("background-position",'4px -28px');
		})
		$(".select-more-tag").css("border-color","#ddd");
		$(".select-wrap").css({"color":"#333","border-color":"#fff #ddd #ddd #ddd"});
	}
		
	
});
//商品筛选
//更多
$(".select-more").click(function(){
	
	var obt = $(this);
	var html = obt.html();
	if(obt.parent().parent().hasClass("extend")){
		obt.parent().parent().removeClass("extend");
		// obt.parent().parent().find(".select-the-letter").css("display",'none');
		if(obt.parent().parent().children().find("#BrandType").length>0){
			brand();
		}
			
		obt.html("更多<i></i>");
		obt.children().css("background-position","2px 6px");
			obt.children().hover(function(){
			$(this).css("background-position","2px -12px");
		},function(){
			$(this).css("background-position","2px 6px");
		});

		
	}else{
		obt.parent().parent().addClass("extend");
		// obt.parent().parent().find(".select-the-letter").css("display",'block');
		obt.html("收起<i></i>");
			obt.children().css("background-position","2px -29px");
			obt.children().hover(function(){
			$(this).css("background-position","2px -45px");
		},function(){
			$(this).css("background-position","2px -29px");
		})

	}
	
});

//展示商品列表某个商品的移入移出效果
$(".main-item li").mouseover(function(){
	var btn = $(this);
	btn.css("border-color",'#e4393c');

}).mouseout(function(){
	var btn = $(this);
	btn.css("border-color", '#fff');
});

//我的商城
$(".head-user-menu").mouseover(function(){
	$(".head-user-menu dd").css("display","block");
	$(".head-user-menu dt").css("border-bottom",'0');
	$(".head-user-menu dt b").css({"top":"7px","border-style":"dashed dashed solid","border-color":"transparent transparent #ccc"});
}).mouseout(function(){
	$(".head-user-menu dd").css("display","none");
	$(".head-user-menu dt").css("border-bottom",'1px solid #efefef');
	$(".head-user-menu dt b").css({"top":"9px","border-style":"dashed dashed solid","border-color":" #CCC transparent transparent"});
});


//鼠标移入移出多选按钮的效果
$(".sl-e-multiple").hover(function(){
	$(this).css({"border":"1px solid #e4393c","color":"#e4393c"});
	$(this).children().css("background-position","0px -84px");
},function(){
	$(this).css({"border":"1px solid #ddd","color":"#333"});
	$(this).children().css("background-position","0px -64px")
});


//点击多选按钮
$(".sl-e-multiple").click(function(){
	var btn = $(this);
	btn.parent().parent().addClass("multiple").parent().siblings().children().removeClass("multiple");
	btn.parent().parent().find(".value-list li").removeClass("selected");
});
//选择品牌触发点击事件
$(".value-list li").on('click',function(){
	//最多可以选择5个品牌
    var num = $(this).parent().find('.selected').length;
    if(num > 4)
    {
    	jAlert('已选条件不能大于5');
    }else
    {
        if($(this).hasClass("selected")){
            $(this).parent().parent().siblings(".sl-b-selected").css("display","none");
            $(this).removeClass("selected");
            $(this).parent().parent().siblings(".sl-btns").siblings(".btn-sure").css("display","none");
        }else{
            $(this).addClass("selected");
            $(this).parent().parent().siblings(".sl-b-selected").css("display","block");
            $(this).parent().parent().siblings(".sl-btns").children(".btn-sure").css("display","block");
        }
    }
});
//多选之后点击取消按钮
$(".btn-cancel").click(function(){
	//移除点击更多按钮添加的类multiple 
	$(this).parents(".select-brand").removeClass("multiple");

	//移除点击多选按钮添加的类extend
	$(this).parents(".select-brand").removeClass("extend");

	//隐藏多选之后出现的确定按钮
	$(this).parent().siblings(".sl-b-selected").css("display","none");

	//隐藏多选之后选择的那个品牌
	$(this).parent().parent().find(".value-list li").removeClass("selected");

	// 是否有id BrandType ,如果有默认显示１５个
	if($(this).parent().siblings(".select-brand-list").children("#BrandType").length>0){
		brand();
	}
});

//品牌
$(".select-the-letter li").mouseover(function(){
	var BrandText = $(this).data("type");//获取品牌字母的类型
	$(this).addClass("curr").siblings().removeClass("curr");
	//遍历所有的品牌
	$("#BrandType li").each(function(){ 
		var type = $(this).data("type");
		//所有的品牌的类型和品牌大写字母的类型进行比对
		if(BrandText == type){
			$(this).css("display","block");
		}else if(BrandText ==0){
			$(this).css("display","block");
		}else{
			$(this).css("display","none");
		}
	 });
	
});
// 品牌的下标默认只显示15个
function brand(){
	$("#BrandType li").each(function(index,domEle){
		if(index<15){
			$(this).css("display","block");
		}else{
			$(this).css("display","none");
		}
	});
}
 
	$(".menu-drop").hover(function(){
		 var _this=$(this);
		 _this.addClass("menu-drop_hover");
		 _this.children(".menu-drop-main").css("display","block");

	},function(){
	    var _this=$(this);
		 _this.removeClass("menu-drop_hover");
		 _this.children(".menu-drop-main").css("display","none");
	});
})
