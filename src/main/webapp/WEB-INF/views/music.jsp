<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${root}/layui/css/layui.css"/>
<link rel="stylesheet" href="${root}/css/common.css"/>
<title>评论</title>
</head>
<jsp:include page="/commons/header.jsp"/>
<div class="layui-container">
<div class="layui-row">
    <div class="layui-row container">
		<%--<c:forEach items="${songs}" var="songVo" varStatus="songStatus">
				<div class="layui-col-md4 item">
					<div class="item-img">
						<a target="_blank" href="">
						<img alt="" src="${songVo.photoURL}"/>
						</a>
					</div>
					<div class="item-text">
						<div class="item-title"><a href="" target="_blank">${songVo.songName}</a></div>
						<div class="item-desc"></div>
						<div class="item-info">
							<span><i></i>评论数：0</span>
						</div>
					</div>
			  </div>
		</c:forEach>--%>
    </div>
</div>
</div>
</body>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/layui/layui.js"></script>
</html>