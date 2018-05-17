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
	    		<form class="layui-form" action="">
					  <div class="layui-form-item">
					    <label class="layui-form-label">输入框</label>
					    <div class="layui-input-block">
					      <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
					    </div>
					  </div>
					  <div class="layui-form-item">
					    <label class="layui-form-label">密码框</label>
					    <div class="layui-input-inline">
					      <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
					    </div>
					    <div class="layui-form-mid layui-word-aux">辅助文字</div>
					  </div>
					  <div class="layui-form-item">
					    <div class="layui-input-block">
					      <button type="button" class="layui-btn common-bg-purple">立即提交</button>
					      <button type="reset" class="layui-btn layui-btn-primary">取消</button>
					    </div>
					  </div>
				</form>
				<hr class="layui-bg-gray">
				<table id="table-singer-list" class="layui-table" lay-filter="listener-table-singer"></table>
	    		<!-- 主体内容 -->
	    	</div>
		 </div>
	</div>
</body>
</div>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/layui/layui.js"></script>
<script type="text/javascript" src="${root}/js/template-web.js"></script>
<script type="text/javascript" src="${root}/js/style.js"></script>
<script type="text/javascript" src="${root}/js/public.js"></script>
<script type="text/javascript">
	layui.use(['element','layer','table'], function() {
		var element=layui.element;
		var layer=layui.layer;
		var table = layui.table;
		table.render({
		    elem: '#table-singer-list',
		    height: 315,
		    url: '${root}/api/singer',
		    page: true,
		    skin:'line',
		    loading:true,
		    response:{
		    	statusName:'code',
		    	statusCode:200,
		    	msgName:'message',
		    	countName:'total',
		    	dataName:'items'
		    },
		    responseHandler:function(res){
		        res.items=res.body.items;
                res.total=res.body.total;
		    	return res;
		    },
		    cols: [[ //表头
		      {field: 'singerId', title: 'ID', width:'10%', sort: true, fixed: 'left'},
		      {field: 'singerName', title: '歌手姓名', width:'20%'},
		      {field: 'tag', title: '歌手标签', width:'10%'},
		      {field: 'descipt', title: '简介', width:'20%'} ,
		      {field: 'photoURL', title: '签名照', width: '15%',templet:'#'},
		      {field: 'action', title: '', width: '15%',toolbar:'#action'}
		    ]]
		  });
	});
</script>
<!-- 模板 -->
</html>