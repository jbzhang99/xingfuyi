<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/datagrid-detailview.js"></script>
<script language="javascript">
    var codeBox;
    
    $(function(){
        //为客户端装配本页面需要的字典数据,多个用逗号分隔
	    <#noescape>
	        codeBox = eval('(${initJSCodeContainer("PRODUCT_STATE","PRODUCT_IS_TOP")})');
	    </#noescape>

        $('#a-gridSearch').click(function(){
            $('#dataGrid').datagrid('reload',queryParamsHandler());
        });

        //审核通过
        $('#a-gridCommit').click(function(){
        	var illegal = false;
        	var selected = $('#dataGrid').datagrid('getSelections');
        	if(selected.length < 1){
        		$.messager.alert('提示', '请选择操作数据');
    			return false;
        	}
        	
        	var ids = new Array();
        	$.each(selected,function(idx,e){
    		 	if(e.state != 2){
    		 		illegal = true;
    		 		var index = $('#dataGrid').datagrid('getRowIndex', e);
    		 		//取消非审核状态的数据
    		 		$('#dataGrid').datagrid('unselectRow', index);
                } else{
    			 	ids.push(e.id);
                }
    		});
    		if(illegal){
                $.messager.show({
                	title:'提示',
                	showType:'show',
                	msg:'只有[提交审核]状态的商品可以审核'
                });
                return false;
    		}
        	
            $.messager.confirm('确认', '确定审核通过选中的商品吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"GET",
                        url: "${currentBaseUrl}/auditProduct?ids="+ids.toString()+"&type=3",
                        success:function(e){
                        	  $.messager.progress('close');
                          	if(e.success){
  	                            $('#dataGrid').datagrid('reload');
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.data,
  	                                showType : 'show'
  	                            });
                          	} else{
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.message,
  	                                showType : 'show'
  	                            });
                          	}
                        }
                    });
                }
            });
        });

        //申请驳回
        $('#a_pro_back').click(function(){
        	var illegal = false;
        	var selected = $('#dataGrid').datagrid('getSelections');
        	if(selected.length < 1){
        		$.messager.alert('提示', '请选择操作数据');
    			return false;
        	}
        	
        	var ids = new Array();
        	$.each(selected,function(idx,e){
    		 	if(e.state != 2){
    		 		illegal = true;
    		 		var index = $('#dataGrid').datagrid('getRowIndex', e);
    		 		//取消非审核状态的数据
    		 		$('#dataGrid').datagrid('unselectRow', index);
                } else{
    			 	ids.push(e.id);
                }
    		});
    		if(illegal){
                $.messager.show({
                	title:'提示',
                	showType:'show',
                	msg:'只有[提交审核]状态的商品可以审核'
                });
                return false;
    		}
            $.messager.confirm('确认', '确定驳回选中的商品申请吗？该商品将无法上架', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"GET",
                        url: "${currentBaseUrl}/auditProduct?ids="+ids.toString()+"&type=4",
                        success:function(e){
                        	$.messager.progress('close');
                          	if(e.success){
  	                            $('#dataGrid').datagrid('reload');
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.data,
  	                                showType : 'show'
  	                            });
                          	} else{
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.message,
  	                                showType : 'show'
  	                            });
                          	}
                        }
                    });
                }
            });
        });

        //删除商品
        $('#a_pro_del').click(function(){
        	var illegal = false;
        	var selected = $('#dataGrid').datagrid('getSelections');
        	if(selected.length < 1){
        		$.messager.alert('提示', '请选择操作数据');
    			return false;
        	}
        	
        	var ids = new Array();
        	$.each(selected,function(idx,e){
    		 	if(e.state == 5){
    		 		illegal = true;
    		 		var index = $('#dataGrid').datagrid('getRowIndex', e);
    		 		//取消非审核状态的数据
    		 		$('#dataGrid').datagrid('unselectRow', index);
                } else{
    			 	ids.push(e.id);
                }
    		});
    		if(illegal){
                $.messager.show({
                	title:'提示',
                	showType:'show',
                	msg:'只能删除未删除的商品'
                });
                return false;
    		}
        	
            $.messager.confirm('确认', '确定删除选中的商品吗？删除后,该商品将被强制下架', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"GET",
                        url: "${currentBaseUrl}/auditProduct?ids="+ids.toString()+"&type=5",
                        success:function(e){
                        	$.messager.progress('close');
                          	if(e.success){
  	                            $('#dataGrid').datagrid('reload');
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.data,
  	                                showType : 'show'
  	                            });
                          	} else{
  	                            $.messager.show({
  	                                title : '提示',
  	                                msg : e.message,
  	                                showType : 'show'
  	                            });
                          	}
                        }
                    });
                }
            });
        });

        //下架
        $("#a_pro_down").click(function(){
        	var illegal = false;
        	var selected = $('#dataGrid').datagrid('getSelections');
        	if(selected.length < 1){
        		$.messager.alert('提示', '请选择操作数据');
    			return false;
        	}
        	
        	var ids = new Array();
        	$.each(selected,function(idx,e){
    		 	if(e.state == 7){
    		 		illegal = true;
    		 		var index = $('#dataGrid').datagrid('getRowIndex', e);
    		 		//取消非审核状态的数据
    		 		$('#dataGrid').datagrid('unselectRow', index);
                } else{
    			 	ids.push(e.id);
                }
    		});
    		if(illegal){
                $.messager.show({
                	title:'提示',
                	showType:'show',
                	msg:'只能操作未下架的商品'
                });
                return false;
    		}
            $.messager.confirm('确认', '确定下架选中的商品吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"GET",
                        url: "${currentBaseUrl}/auditProduct?ids="+ids.toString()+"&type=7",
                        success:function(e){
                            $.messager.progress('close');
                        	if(e.success){
	                            $('#dataGrid').datagrid('reload');
	                            $.messager.show({
	                                title : '提示',
	                                msg : e.data,
	                                showType : 'show'
	                            });
                        	} else{
	                            $.messager.show({
	                                title : '提示',
	                                msg : e.message,
	                                showType : 'show'
	                            });
                        	}
                        }
                    });
                }
            });
        });
        
      	//商品详情
    	$("#a-details").click(function (){
    		var selected = $('#dataGrid').datagrid('getSelected');
    		if(!selected){
    			$.messager.alert('提示', '请选择操作行。');
    			return;
    		}
    		location.href="${domainUrlUtil.urlResources}/admin/product/details?productId="+selected.id;
    	});
    });

    function stateFormat(value,row,index){
        return codeBox["PRODUCT_STATE"][value];
    }

    function isTopFormat(value,row,index){
        return codeBox["PRODUCT_IS_TOP"][value];
    }

    function sellerIsTopFormat(value,row,index){
        return codeBox["PRODUCT_IS_TOP"][value];
    }

    function openwin(id){
        window.open("${(domainUrlUtil.frontUrl)!}/product/"+ id +".html?preview=1");
    }

    //操作
    function handler(value,row,index){
        var html ="";
        if(row.isTop==1){
            html += "<a href='javascript:;' onclick='recommond("+row.id+
                    ",true,"+row.state+")'>推荐</a>&nbsp;&nbsp;<a href='javascript:;' onclick='del("+
                    row.id+")'>删除";
        } else{
            html += "<a href='javascript:;' onclick='recommond("+row.id+
                    ",false,"+row.state+")'>取消推荐</a>&nbsp;&nbsp;<a href='javascript:;' onclick='del("+
                    row.id+")'>删除";
        }
        html += "</a>";
        return html;
    }

    //推荐/取消推荐
    function recommond(id,isRec,status){
        if(status==5){
            $.messager.alert('提示','该商品已被删除');
            return;
        }
        try{
            $.ajax({
                url:'${currentBaseUrl}/recommond?id='+id+'&isRec='+isRec,
                success:function(e){
                    $.messager.show({
                        title:'提示',
                        msg:e,
                        showType:'show'
                    });
                    $('#dataGrid').datagrid('reload');
                }
            });
        } catch(e){
            throw new Error(e);
        }
    }

    function del(id){
        $.messager.confirm('确认', '确定删除该商品吗？删除此商品后,商家店铺的该商品将被迫下架', function(r){
            if (r){
                $.messager.progress({text:"提交中..."});
                $.ajax({
                    type:"get",
                    url: "${currentBaseUrl}/del?id="+id,
                    success:function(e){
                        $.messager.show({
                            title:'提示',
                            msg:e,
                            showType:'show'
                        });
                        $.messager.progress('close');
                        $('#dataGrid').datagrid('reload');
                    }
                });
            }
        });
    }

    function getLength(str){
        var realLength = 0;
        for (var i = 0; i < str.length; i++){
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128)
                realLength += 1;
            else
                realLength += 2;
        }
        return realLength;
    }

    function loadSuccess(data){
    	if(data.rows.length==0){
    		//无数据提示
    		var body1 = $(this).data().datagrid.dc.body1;
    		var body2 = $(this).data().datagrid.dc.body2;
    		body1.find('table').html('<tr class="datagrid-row"><td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber"></div></td></tr>');
    		body2.find('table').width('100%').find('tbody').append('<tr><td style="height: 25px; text-align: center;">没有数据</td></tr>');
    	}
    	
    	$("table.datagrid-btable tr td .datagrid-cell font").hover(function() {
            var index = $(this).parents(".datagrid-row").attr("datagrid-row-index");
            var outerheight = $(this).closest(".datagrid-body").offset().top;
            var height = $(this).offset().top - outerheight + 20;
            if($("#proinfo").length < 1)
                $("<div id='proinfo' class='proInfo'>点击商品名称去商城查看此商品</div>").appendTo($(this));
            $(".proInfo").animate({opacity: "show", top: height}, 300);
        }, function() {
            $("#proinfo").remove();
        });
    }

    function proTitle(value,row,index){
        return "<font style='color:blue;cursor:pointer' title='"+
                value+"' onclick='openwin("+row.id+")'>"+value+"</font>";
    }
    

	function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
    }
    
 	function onExpandRow(index,row){
        var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
        ddv.datagrid({
           fitColumns:true,
           singleSelect:true,
           method:'get',
           url:'${currentBaseUrl}/list_goods?productId='+row.id,
			loadMsg : '数据加载中...',
			height : 'auto',
			columns : [[{
				field : 'normName',
				title : '规格值',
				width : 150,
				align : 'left',
				halign : 'center'
			}, {
				field : 'sku',
				title : 'SKU',
				width : 150,
				align : 'center'
			}, {
				field : 'mallPcPrice',
				title : '商城PC价格',
				width : 150,
				align : 'center'
			}, {
				field : 'mallMobilePrice',
				title : '商城Mobile价格',
				width : 150,
				align : 'center'
			}, {
   				field : 'weight',
   				title : '重量kg',
   				width : 70,
   				align : 'center'
   			}, {
   				field : 'length',
   				title : '长度cm',
   				width : 70,
   				align : 'center'
   			}, {
   				field : 'width',
   				title : '宽度cm',
   				width : 70,
   				align : 'center'
   			}, {
   				field : 'height',
   				title : '高度cm',
   				width : 70,
   				align : 'center'
   			}, {
				field : 'productStock',
				title : '库存',
				width : 150,
				align : 'center'
			}]],
			onResize : function() {
				$('#dataGrid').datagrid('fixDetailRowHeight',index);
			},
			onLoadSuccess : function() {
				setTimeout(function() {
					$('#dataGrid').datagrid('fixDetailRowHeight',index);
				}, 0);
			}
		});
	}
    
</script>