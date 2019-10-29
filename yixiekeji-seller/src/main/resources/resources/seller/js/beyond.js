function getThemeColorFromCss(n) {
    var t = $("<span><\/span>").hide().appendTo("body"),
    i;
    return t.addClass(n),
    i = t.css("color"),
    t.remove(),
    i
}
    

function curfun(){
        $("#bodyejavashop").css({"width":"100%"})
        $("#bodyejavashop>div").css({"width":"100%"});
        $("#bodyejavashop>div>div").css({"width":"100%"});
        $("#bodyejavashop>div>div>div").css({"width":"100%"});
        $("#bodyejavashop>div>div>div>div").css({"width":"100%"});
        $(".datagrid-view:first").css({"width":"100%"});
        if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
            
        }else{
        	var view1width = $(".datagrid-view1").width();
        	if(!view1width)
        		view1width = 0;
        	
            if($(".sidebar-toggler").hasClass("active")==false){
                $(".datagrid-view2:first").css({"width":$(window).width()-view1width+"px"});
            }else{
            	var sidebarwidth = $("#sidebar").width();
            	if(!sidebarwidth)
            		sidebarwidth = 0;
            	var width_ = Number($(window).width() - sidebarwidth - view1width);
            	
                $(".datagrid-view2:first").css({"width":width_});
            }
            $(".datagrid-view2:first>div").css({"width":"100%"});
        }
}
// 封装 控制左侧导航栏
function InitiateSideMenu() {
    $(".sidebar-toggler").on("click", 
    function() {
        curfun();
        return $("#sidebar").toggleClass("hide"),
        $(".sidebar-toggler").toggleClass("active"),
        !1
    });
    var n = $("#sidebar").hasClass("menu-compact");
    $("#sidebar-collapse").on("click", 
    function() {
        curfun();
        return $("#sidebar").toggleClass("hide"),
        $(".sidebar-toggler").toggleClass("active"),
        !1
    });
    $(".sidebar-menu").on("click", 
    function(t) {
        var i = $(t.target).closest("a"),
        u,
        r,
        f;
        if (i && i.length != 0) {
            if (!i.hasClass("menu-dropdown")) return n && i.get(0).parentNode.parentNode == this && (u = i.find(".menu-text").get(0), t.target != u && !$.contains(u, t.target)) ? !1: void 0;
            if (r = i.next().get(0), !$(r).is(":visible")) {
                if (f = $(r.parentNode).closest("ul"), n && f.hasClass("sidebar-menu")) return;
                f.find("> .open > .submenu").each(function() {
                    this == r || $(this.parentNode).hasClass("active") || $(this).slideUp(200).parent().removeClass("open")
                })
            }
            return n && $(r.parentNode.parentNode).hasClass("sidebar-menu") ? !1: ($(r).slideToggle(200).parent().toggleClass("open"), !1)
        }
    })
}

var themeprimary = getThemeColorFromCss("themeprimary"),
themesecondary = getThemeColorFromCss("themesecondary"),
themethirdcolor = getThemeColorFromCss("themethirdcolor"),
themefourthcolor = getThemeColorFromCss("themefourthcolor"),
themefifthcolor = getThemeColorFromCss("themefifthcolor"),
rtlchanger,
popovers,
hoverpopovers;

// 更换风格背景
$("#skin-changer li a").click(function() {
    createCookie("current-skin", $(this).attr("rel"), 10);
    window.location.reload()
});


// 页面刷新loding状态
$(window).load(function() {
    setTimeout(function() {
        $(".loading-container").addClass("loading-inactive")
    },
    0)
});
// 放大网页按钮
$("#fullscreen-toggler").on("click", 
function() {
    var n = document.documentElement;
    $("body").hasClass("full-screen") ? ($("body").removeClass("full-screen"), $("#fullscreen-toggler").removeClass("active"), document.exitFullscreen ? document.exitFullscreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitExitFullscreen && document.webkitExitFullscreen()) : ($("body").addClass("full-screen"), $("#fullscreen-toggler").addClass("active"), n.requestFullscreen ? n.requestFullscreen() : n.mozRequestFullScreen ? n.mozRequestFullScreen() : n.webkitRequestFullscreen ? n.webkitRequestFullscreen() : n.msRequestFullscreen && n.msRequestFullscreen())
});

$("#refresh-toggler").on("click",function(){
	top.window.location.reload(true)
});

// 点击展示左侧边导航栏
InitiateSideMenu();

