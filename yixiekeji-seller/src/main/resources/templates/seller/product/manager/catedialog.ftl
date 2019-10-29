<#include "/admin/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/product/cate/"/>
<script language="javascript">
    var codeBox;
    $(function(){
        //为客户端装配本页面需要的字典数据,多个用逗号分隔
    <#noescape>
        codeBox = eval('(${initJSCodeContainer("NORM_TYPE")})');
    </#noescape>

        $('#a-gridSearch').click(function(){
            $('#dataGrid').datagrid('reload',queryParamsHandler());
        });
        $("#a-gridAdd").click(function(){
            window.location.href="${currentBaseUrl}add";
        });
        $("#a-gridEdit").click(function(){
            var selectedCode = $('#dataGrid').datagrid('getSelected');
            if(!selectedCode){
                $.messager.alert('提示','请选择操作行。');
                return;
            }
            window.location.href="${currentBaseUrl}edit?id="+selectedCode.id;
        });

        $("#a-gridRemove").click(function(){
            var selectedCode = $('#dataGrid').datagrid('getSelected');
            if(!selectedCode){
                $.messager.alert('提示','请选择操作行。');
                return;
            }
            if(selectedCode.state == 0){
                $.messager.alert('提示','已经删除。');
                return;
            }

            $.messager.confirm('确认', '确定删除吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"POST",
                        url: "${currentBaseUrl}del",
                        dataType: "json",
                        data: "id="+selectedCode.id+"&"+getCSRFTokenParam(),
                        cache:false,
                        success:function(data, textStatus){
                            if (data.success) {
                                //$('#dataGrid').datagrid('deleteRow',selectedIndex);
                                $('#dataGrid').treegrid('reload', selectedCode.parentId);
                            }else{
                                $.messager.alert('提示', data.message);
                            }
                            refrushCSRFToken(data.csrfToken);
                            $.messager.progress('close');
                        }
                    });
                }
            });
        });
    })

    function typeFormat(value,row,index){
        return codeBox["NORM_TYPE"][value];
    }
    function afterDataGridLoaded(){
    }
    function dataGridLoadFail(){
        alert("服务器异常");
    }
    function dataGridLoadSuccess(row,data){

    }
    function cc(row){
    }
    function bl(row,param){
        if (!row) {
            param.id = 0;
        }
    }
</script>

<#--1.queryForm----------------->
<div id="searchbar" data-options="region:'north'" style="margin:0 auto;" border="false">
    <h2 class="h2-title">商品分类列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span></h2>
    <div id="searchbox" class="head-seachbox">
        <div class="w-p99 marauto searchCont">
            <form class="form-search" action="doForm" method="post" id="queryForm" name="queryForm">
                <div class="fluidbox">
                    <p class="p4 p-item">
                        <label class="lab-item">类型名称 :</label>
                        <input type="text" class="txt" id="q_name" name="q_name" value="${q_name!''}"/>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>

<#--2.datagrid----------------->
<div data-options="region:'center'" border="false">
    <table id="dataGrid" class="easyui-treegrid"
           data-options="rownumbers:true
						,singleSelect:true
						,autoRowHeight:true
						,fitColumns:true
						,toolbar:'#gridTools'
						,striped:true
						,pagination:false
						,pageSize:'2'
						,fit:true
    					,url:'${currentBaseUrl}getByPid'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:dataGridLoadSuccess
    					,onLoadError:dataGridLoadFail
						,onBeforeExpand:cc
						,treeField:'name'
						,idField:'id'
    					,method:'get'
						,onBeforeLoad:bl">
        <thead>
        <tr>
            <th field="name" width="60" align="left">分类名称</th>
            <th field="scaling" width="100" align="center">分拥比例</th>
            <th field="sort" width="100" align="center">权重</th>
        </tr>
        </thead>
    </table>

    <#--3.function button----------------->
    <div id="gridTools">
        <a id="a-gridAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a id="a-gridEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
        <a id="a-gridRemove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <#--<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>-->
    <div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />

<#assign prefix="cate"/>
    <div id="${prefix}Dlg" class="easyui-dialog popBox" title="商品分类列表" style="width:750px;height:300px;" data-options="resizable:true,closable:true,closed:true,cache: false,modal: true" buttons="#dlg-buttons-${prefix}">
        <div id="searchbar" data-options="region:'north'" style="height:45px;" border="false">
            <div id="searchbox" class="head-seachbox">
                <div class="w-p99 marauto searchCont">
                    <form class="form-search" action="doForm" method="post" id="queryForm" name="queryForm">
                        <div class="fluidbox">
                            <p class="p4 p-item">
                                <label class="lab-item">类型名称 :</label>
                                <input type="text" class="txt" id="q_name" name="q_name" value="${q_name!''}"/>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" border="false">
                <table id="dataGrid" class="easyui-treegrid"
                       data-options="rownumbers:true
						,singleSelect:true
						,autoRowHeight:true
						,fitColumns:true
						,toolbar:'#gridTools'
						,striped:true
						,pagination:false
						,pageSize:'2'
						,fit:true
    					,url:'${currentBaseUrl}getByPid'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:dataGridLoadSuccess
    					,onLoadError:dataGridLoadFail
						,onBeforeExpand:cc
						,treeField:'name'
						,idField:'id'
    					,method:'get'
						,onBeforeLoad:bl">
                    <thead>
                    <tr>
                        <th field="name" width="60" align="left">分类名称</th>
                        <th field="scaling" width="100" align="center">分拥比例</th>
                        <th field="sort" width="100" align="center">权重</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    <div id="${prefix}GridTools">
        <a id="${prefix}GridQuery" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
    </div>
    <div id="dlg-buttons-${prefix}" style="text-align:right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="${prefix}Submit()">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${prefix}Dlg').dialog('close')">取消</a>
    </div>
    <script language="javascript">
        $(function(){
            <#noescape>normTypeBox = eval('(${initJSCodeContainer("NORM_TYPE")})');</#noescape>

            $("#${prefix}GridQuery").click(function(){
                $('#${prefix}DataGrid').datagrid('load',${prefix}QueryParamsHandler());
            });
        })

        function ${prefix}Submit() {
            var selectedRow = $("#${prefix}DataGrid").datagrid('getSelected');
            if (selectedRow == null) {
                $.messager.alert('友情提示','请至少选择一个对象');
                return false;
            }
            var callbackfunc = eval('${prefix}CallBack');
            callbackfunc(selectedRow);
            $("#${prefix}Dlg").dialog('close');
        }

        function ${prefix}QueryParamsHandler(){
            var strParams = '{';
            $("[name^='q_']").each(function () {
                strParams+='"'+$(this).attr('name')+'"';
                strParams+=':';
                strParams+='"'+$(this).val()+'"';
                strParams+=',';
            });
            strParams = strParams.substr(0, strParams.length-1);
            strParams += '}';
            return eval('('+strParams+')');
        }

        function typeFormat(value,row,index) {
            return normTypeBox["NORM_TYPE"][value];
        }
    </script>