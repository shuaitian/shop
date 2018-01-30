<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   

<!DOCTYPE html>
<html>
<head>
<title>${good.title }</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>
<script type="text/javascript" src="js/my.js"></script>

</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<div class="single">

<div class="container">
<div class="col-md-12">
	<div class="col-md-1"></div>
	<div class="col-md-4">		
		<div class="flexslider">
			  <input type="hidden" id="picurls" value="${good.picurl }">
			  <ul class="slides" id="slides">
			  </ul>
		</div>
	</div>	
	<div class="col-md-6">
		<div class="single-para simpleCart_shelfItem">
			<h1>${good.title }</h1>
			<p>${good.summary }</p>

			<div class="star-on">
								<ul>
									<li><a href="#"><i class="glyphicon glyphicon-star"> </i></a></li>
									<li><a href="#"><i class="glyphicon glyphicon-star"> </i></a></li>
									<li><a href="#"><i class="glyphicon glyphicon-star"> </i></a></li>
									<li><a href="#"><i class="glyphicon glyphicon-star"> </i></a></li>
									<li><a href="#"><i class="glyphicon glyphicon-star"> </i></a></li>
								</ul>
								<div class="review">
									<a href="#">查看评论 </a>/
									<a href="#">编写评论 </a>
								</div>
							<div class="clearfix"> </div>
			</div>
			
			<label  class="add-to item_price">￥<font color='red'><fmt:formatNumber type="number" value="${good.price/100 }" pattern="0.00" maxFractionDigits="2"/>  </font></label>
								
			<div class="available">
				<h6>购买数量：</h6>
					<div class="row">
			            <div class="col-md-3">
			                <div class="input-group">
			                    <span class="input-group-btn">
			                        <button class="btn btn-default" type="button" onclick="numsub();">-</button>
			                    </span>
			                    <input type="text" class="form-control" value="1" id="count">
			                    <span class="input-group-btn">
			                        <button class="btn btn-default" type="button" onclick="numplus();">+</button>
			                    </span>
			                </div><!-- /input-group -->
			            </div><!-- /.col-lg-6 -->
			        </div><!-- /.row -->
			</div>
			<c:if test="${user.type==1 }">
				<c:if test="${good.status==2 }">
					<a style="cursor:not-allowed; background: #888888;" class="cart">已购买</a>
				</c:if>
				<c:if test="${good.status!=2 }">
					<a onclick="addCartWithCount(${good.id} );" style="cursor: pointer;" class="cart item_add">加入购物车</a>
				</c:if>
				
			</c:if>
			<c:if test="${user.type==2 }">
				<a href="#" class="cart" data-toggle="modal" data-target="#myModal">编辑</a>
			</c:if>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>
<!-- model -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改商品</h4>
      </div>
      <div class="modal-body">
      	<jsp:include page="goodform.jsp"></jsp:include>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="submitupdate();">保存修改</button>
      </div>
    </div>
  </div>
</div>
<!-- //model -->

<div class='row'>
<div class="col-md-1"></div>
<div class="col-md-10">
	<div class="page-header">
		 <h1><small>商品详情</small></h1>
	</div>
	<div>
	<center>
	${good.content }
	</center>
	</div>
</div>
<div class="col-md-1"></div>
</div>

</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>


<!-- slide -->

<script src="js/imagezoom.js"></script>
<!-- start menu -->
<script src="js/simpleCart.min.js"> </script>
  <script defer src="js/jquery.flexslider.js"></script>
<link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" />
<script>

$(function(){
	$("#publishButton").remove();
})

// Can also be used with $(document).ready()
$(window).load(function() {
  $('.flexslider').flexslider({
    animation: "slide",
    controlNav: "thumbnails"
  });
});
</script>
<!---pop-up-box---->
					<link href="css/popuo-box.css" rel="stylesheet" type="text/css" media="all"/>
					<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
					<!---//pop-up-box---->
					 <script>
						$(document).ready(function() {
						$('.popup-with-zoom-anim').magnificPopup({
							type: 'inline',
							fixedContentPos: false,
							fixedBgPos: true,
							overflowY: 'auto',
							closeBtnInside: false,
							preloader: false,
							midClick: true,
							removalDelay: 300,
							mainClass: 'my-mfp-zoom-in'
						});
																						
						});
				</script>	
</body>
</html>