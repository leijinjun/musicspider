<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta name="keywords" content="">
<meta name="description" itemprop="description" content="">
<title></title>
<link rel="stylesheet" href="${root}/layui/css/layui.css"/>
<link rel="stylesheet" href="${root}/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${root}/bootstrap/css/bootstrap-table.min.css"/>
<link rel="stylesheet" href="${root}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${root}/css/iconfont.css">
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-row">
		    <div class="layui-header common-bg-black">
		      	<jsp:include page="/WEB-INF/commons/header.jsp"/>
		    </div>
		 </div>
		 <div class="layui-row">
		 	<div class="layui-side common-bg-black left-nav">
	    		<jsp:include page="/WEB-INF/commons/nav.jsp"/>
	    	</div>
	    	<div id="main-body" class="layui-body">
	    		<p>歌手列表</p>
	    		<form class="form-inline">
				  <div class="form-group">
				    <label for="search">歌手姓名</label>
				    <input type="text" class="form-control" id="search" placeholder="邓紫棋">
				  </div>
				  <a class="btn btn-default">搜索</a>
				</form>
				<hr class="layui-bg-gray">
				<div class="btn-group" style="float: right;padding-top:12px;padding-right: 13px;">
	    			<a href="javascript:void(0);" class="table-refresh btn btn-default btn-sm"><i class="iconfont icon-shuaxin1 common-color-black"></i></a>
	    			<a href="javascript:void(0);" class="table-change-view btn btn-default btn-sm"><i class="iconfont icon-wanggeshitu-copy common-color-black"></i></a>
	    			<!-- <a href="javascript:void(0);" class="btn btn-default btn-sm" ><i class="iconfont icon-liebiao2 common-color-black"></i></a> -->
    			</div>
				<table id="table-singer-list"></table>
	    		<!-- 主体内容 -->
	    		<div class="toolbar">
	    			<div>
	    			<a href="javascript:void(0);" class="table-del-row btn btn-default btn-sm"><i class="iconfont icon-shanchu common-color-black"></i>删除</a>
	    			</div>
	    		</div>
	    	</div>
		 </div>
	</div>
</body>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/layui/layui.js"></script>
<script type="text/javascript" src="${root}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${root}/bootstrap/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${root}/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${root}/js/template-web.js"></script>
<script type="text/javascript" src="${root}/js/style.js"></script>
<script type="text/javascript" src="${root}/js/public.js"></script>
<script type="text/javascript">
	layui.use(['element','layer','table'], function() {
		var element=layui.element;
		var layer=layui.layer;
		var table = layui.table;
		
		$("#table-singer-list").bootstrapTable({
			height:500,
			striped:true,
			sidePagination: 'server',
			sortName:'singerId',
			cache:false,
			pageNumber:1,
			pageSize:10,
			pageList:[10,20,50,100],
			idField:'singerId',
			//detailView:true,//子父表
			paginationHAlign:'left',
			clickToSelect:false,
			toolbar:'.toolbar',
			//showColumns:true,
			//showRefresh:true,
			//showToggle:true,
			queryParams:function(params){
				return params;
			},
			detailFormatter:function(index,row){
			    console.log(row);
				return '<span>哈哈哈</span>';
			},
			columns:[
				{checkbox:true,align:'center',width:5},
				{field:'singerId',title:'ID',align:'center',width:10},
				{field:'singerName',title:'艺名',align:'center',width:20},
				{field: 'tag', title: '歌手标签',align:'center',width:15},
		        {field: 'descipt', title: '简介',align:'center',width:20} ,
		        {field: 'photoURL', title: '签名照',align:'center',width:20,formatter:function(value,row,index){
		            var reg=/<img.*src=["'](.*)["'].*?>/g;
		            var result=null;
		            if((result=reg.exec(value))!=null){
		            	value=result[1];
		            }
		        	return '<img height="43px;" src="'+value+'"/>';
		        }},
		        {field: 'isHot', title: '热门歌手',align:'center',width:10,formatter:function(value,row,index){
		        	return value?'是':'否';
		        }}
			],
			method:'get',
			url:'${root}/api/singer',
			responseHandler:function(res){
			    res.total=res.body.total;
			    res.rows=res.body.items;
				return res;
			},
			pagination:true
		});
		
		//刷新
		$(".table-refresh").click(function(){
			$('#table-singer-list').bootstrapTable('refresh',{silent:true,query:{name:'阿宝'}});
		});
		//切换视图
		$(".table-change-view").click(function(){
			$('#table-singer-list').bootstrapTable('toggleView');
		});
		$(".table-del-row").click(function(){
			var selRows=$('#table-singer-list').bootstrapTable('getSelections');
			console.log(selRows);
			var ids=new Array();
		    $.each(selRows,function(i,item){
		       ids.push(item.singerId);
		    });
		    if($.isEmptyObject(ids)){
		       alert("当前没有选中任何行!");
		       return;
		    }
		    $.ajaxhelper('${root}/api/singer',"post",{ids:ids},function(res){
		    	if(res.errorCode!=0){
		    		alert(res.message);
		    		return;
		    	}
				$('#table-singer-list').bootstrapTable('remove',{'field':'singerId',values:ids});
		    });
		});
	});
</script>
<!-- 模板 -->
</html>