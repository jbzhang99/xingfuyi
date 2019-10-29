//判断浏览器ie6创建的div的样式和非ie6创建的div的样式
//创建div
function showid(idname) {
    	//赋值验证码
	document.getElementById("code_img").src = domain + "/verify.html?d=" + new Date().getTime();
	//重置表单
	document.getElementById("dialogLoginForm").reset();
    
	var isIE = (document.all) ? true: false;
	var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
	var newbox = document.getElementById(idname);
	newbox.style.zIndex = "9999";
	newbox.style.display = "block";
	newbox.style.position = !isIE6 ? "fixed": "absolute"; 
	//newbox.style.top =document.documentElement.scrollTop+ (document.documentElement.clientHeight-this(.offsetHeight))/2+"px";
	newbox.style.top = newbox.style.left = "50%";
	newbox.style.marginTop = -newbox.offsetHeight / 2 + "px";
	newbox.style.marginLeft = -newbox.offsetWidth / 2 + "px";
	var layer = document.createElement("div");
	layer.id = "layer";
	layer.setAttribute("class","layer_class");
	layer.style.width = layer.style.height = "100%";
	layer.style.position = !isIE6 ? "fixed": "absolute";
	layer.style.top = layer.style.left = 0;
	layer.style.backgroundColor = "#000";
	layer.style.zIndex = "9998";
	layer.style.opacity = "0.15";
	layer.style.filter = "alpha(opacity=15)";
	document.body.appendChild(layer);
	var sel = document.getElementsByTagName("select");
	for (var i = 0; i < sel.length; i++) {
		sel[i].style.visibility = "visible";
	}
	function layer_iestyle() {
		layer.style.width = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
		layer.style.height = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
	}
	function newbox_iestyle() {
		newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
	}
	/*if (isIE) {
		layer.style.filter = "alpha(opacity=60)";
	}*/
	if (isIE6) {
		layer_iestyle();
		newbox_iestyle();
		window.attachEvent("onscroll",
			function() {
				newbox_iestyle();
			}
		)
		window.attachEvent("onresize", layer_iestyle);
	}
	
	function closewindw(){ //关闭弹出窗
		newbox.style.display = "none";
		//layer.style.display = "none";
		layer.parentNode.removeChild(layer);

		for (var i = 0; i < sel.length; i++) {
			sel[i].style.visibility = "visible";
		}
	}
	
/*	layer.onclick = function() {
        closewindw()	
    }
*/	

	//点击关闭弹窗按钮
	//var closezoom=document.get
	var closebg =newbox.getElementsByTagName("dl");
	for( var i=0; i<closebg.length; i++){
	closebg[i].onclick = function(){
		/*newbox.style.display = "none";
		layer.style.display = "none";
		for (var i = 0; i < sel.length; i++) {
			sel[i].style.visibility = "visible";
		}
		ss = 1;myCal1.fx.start(1, 0);
		jQuery("#calendar_id").css({"visibility":"hidden","opacity":"0","display":"none"});	*/
        closewindw();	
	}}
	
	//按esc键关闭
	var Body=document.body;
	Body.onkeyup=function(evt){
		 evt = evt ? evt : (window.event ? window.event : null);
		  if(evt.keyCode==27 ){
			closewindw();
	       }
	}

}
function closeLayer(idname){
	if(document.getElementById(idname)!=null)
	{
	document.getElementById(idname).style.display = "none";
	}

	
	if(document.getElementById('layer')!=null)
	{
		
	document.getElementById('layer').parentNode.removeChild(document.getElementById('layer'));
	}
	
} 
