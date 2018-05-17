<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div>
    <div class="layui-side-scroll">
      <ul class="layui-nav layui-nav-tree commone-bg-black" lay-shrink="all"  lay-filter="left-nav">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;"><i class="iconfont icon-yinyue"></i> 音乐管理</a>
          <dl class="layui-nav-child">
            <dd><a data-id="101" data-url="/admin/music/singer.html" href="javascript:void(0);">歌手管理</a></dd>
            <dd><a data-id="102" data-url="/admin/music/song.html" href="javascript:;">歌曲管理</a></dd>
            <dd><a data-id="103" data-url="/admin/music/comment.html" href="javascript:;">评论管理</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>