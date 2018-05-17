/**
 * 
 */
 $.extend({
 	ajaxhelper:function(url,method,data,call){
		$.ajax({
		url:url,
		dataType:'json',
		traditional:true,
		type:method,
		data:data,
		success:function(data){
			call(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("网络异常！");
		}
		});
	}
});

$(".left-nav").on("click","a[data-id]",function(){
	var id=$(this).attr("data-id");
	var url=$(this).attr("data-url");
	location.href=url+"#"+id;
	
});
$(function(){
	var hash=location.hash;
	if(hash!=''){
		hash=hash.replace("#", "");
		$(".left-nav").find("a[data-id]").removeClass("layui-this");
		$(".left-nav").find("a[data-id='"+hash+"']").parent().addClass("layui-this");
	}
});

function getEleById(id){
	return document.getElementById(id);
}

function getElesByClass(className){
	return document.getElementsByClassName(className);
}

function getElesByTag(tag){
	return document.getElementsByTagName(tag);
}

_time=function dateFormat(date,fmt)
{
  var o = {
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
}
