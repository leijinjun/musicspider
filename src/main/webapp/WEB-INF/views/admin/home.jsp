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
	    	<div id="main-body" class="layui-body main">
	    		<!-- 主体内容 -->
	    	</div>
		 </div>
	</div>
</body>
<div id="rb-msg" class="common-hide common-color-white home-rb-tip">
</div>
<script type="text/javascript" src="${root}/layui/layui.js"></script>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/js/template-web.js"></script>
<script type="text/javascript" src="${root}/js/style.js"></script>
<script type="text/javascript" src="${root}/js/public.js"></script>
<script type="text/javascript" src="${root}/js/canvas-particle.js"></script>
<script type="text/javascript">
window.onload = function() {
    //配置
    var config = {
        vx: 4,	//小球x轴速度,正为右，负为左
        vy: 4,	//小球y轴速度
        height: 2,	//小球高宽，其实为正方形，所以不宜太大
        width: 2,
        count: 200,		//点个数
        color: "121, 162, 185", 	//点颜色
        stroke: "130,255,255", 		//线条颜色
        dist: 6000, 	//点吸附距离
        e_dist: 20000, 	//鼠标吸附加速距离
        max_conn: 10,	//点到点最大连接数
        ele:'main-body'  //容器Id
    }
    //调用
    CanvasParticle(config);
}
	layui.use(['element','layer'], function() {
		var element=layui.element;
		var layer=layui.layer;
		$(function(){
			var html=template("rb-tip-template",{nickname:'admin',
				lastLoginTime:_time(new Date(),"yyyy-MM-dd hh:mm"),
				unreadMessage:'5'});
			$("#rb-msg").append(html);
			setTimeout(function() {
				layer.open({
					type:1,
					content:$('#rb-msg'),
					skin:'common-bg-purple',
					title:false,
					area: ['340px', '215px'],
					offset:'rb',
					closeBtn:0,
					shade: 0,
					shadeClose: true,
					time:2500,
					anim: 2,
					end:function(){
						
					}
				});
			}, 500);
		});
	});
</script>
<!-- 模板 -->
<script type="text/html" id="rb-tip-template">
	<p>欢迎{{nickname}},上次登录时间:{{lastLoginTime}}</p>
    <p>您有<a href="javascript:void(0);"><span class="layui-badge">{{unreadMessage}}</span></a>条未读通知</p>
</script>
</html>