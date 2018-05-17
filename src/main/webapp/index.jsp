<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${root}/css/common.css">
<link rel="stylesheet" type="text/css" href="${root}/css/swiper.min.css">
<title>首页_musicspider</title>
</head>
<body>
<div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide"><img alt="" src="/images/130670babeafdd4f26c79e677b256e6e_d833c895d143ad4b13016da38e025aafa40f06a1.jpg"/> </div>
      <div class="swiper-slide"><img alt="" src="/images/1392201185688_image_6498.jpg"/></div>
      <div class="swiper-slide"><img alt="" src="/images/222931l1lgx9gawwgygzil.jpg"/></div>
    </div>
    <!-- Add Pagination -->
    <div class="swiper-pagination"></div>
  </div>
</body>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/js/swiper.min.js"></script>
<script type="text/javascript" src="${root}/js/style.js"></script>
<script type="text/javascript">
$(function() {
/*轮播*/
var swiper = new Swiper('.swiper-container', {
    spaceBetween: 30,
    centeredSlides: true,
    init:false,//手动初始化，调用init()
    autoplay: {
      delay: 2500,
      disableOnInteraction: false,
    },
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
});
	swiper.init();
});
</script>
</html>