//新增收货地址 或编辑收货地址
function addOrEditAddress(id){
	$("#Harvest").addClass("lay-display");
	$.ajax({
		type:"GET",
		url:domain+"/member/editaddress.html",
		dataType:"html",
		data : {id:id},
		success:function(data){
			$("#Harvest").html(data);
		},
		error:function(){
			jAlert("异常，请重试！");
		}
	});
}


function  defaultAddress(id){
	//设置默认地址
	$.ajax({
		type:"POST",
		url:domain+"/member/setdefaultaddress.html",
		dataType:"json",
		data : {id:id},
		success:function(data){
			if(data.success){
				//重新加载列表数据
				window.location.href= domain+"/member/address.html";
			}else{
				jAlert(data.message);
			}
		},
		error:function(){
			jAlert("异常，请重试！");
		}
 });
}


function  deleteAddress(id){
	if(confirm("是否确认删除")){
	//删除地址
		$.ajax({
			type:"POST",
			url:domain+"/member/deleteaddress.html",
			dataType:"json",
			async : false,
			data : {id:id},
			success:function(data){
				if(data.success){
					//重新加载列表数据
					window.location.href= domain+"/member/address.html";
				}else{
					jAlert(data.message);
				}
			},
			error:function(){
				jAlert("异常，请重试！");
			}
		});
	}
}


$(function(){
	
	//控制左侧菜单选中
	$("#reciptAddress").addClass("currnet_page");
	
});	
