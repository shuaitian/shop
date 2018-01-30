<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   

<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/my.css" rel="stylesheet" type="text/css" media="all" />

<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- start menu -->
<link href="css/memenu.css" rel="stylesheet" type="text/css" media="all" />

<script src="js/simpleCart.min.js"></script> 
<script src="js/jquery.form.js" ></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>


<script type="text/javascript" src="js/my.js"></script>
</head>
<body>
	<!--header-->
	<jsp:include page="head.jsp" />
	<div class="container">
		<div class="head-top">
			<div class="col-md-2 number">
				<span><i class="glyphicon glyphicon-phone"></i>18505813690</span>
			</div>
			<div class="col-md-8 h_menu4">
				<ul class="memenu skyblue">
					<li class=" grid"><a href="index.do">所有内容</a></li>
					<c:if test="${user.type==1 }">
						<li class=" grid"><a href="unbuy.do">未购买内容</a></li>
					</c:if>
				</ul>
			</div>
			<div class="col-md-2 search">
				<a class="play-icon popup-with-zoom-anim" href="#small-dialog"><i
					class="glyphicon glyphicon-search"> </i> </a>
			</div>
			<div class="clearfix"></div>
			<!---pop-up-box---->
			<link href="css/popuo-box.css" rel="stylesheet" type="text/css"
				media="all" />
			<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
			<!---//pop-up-box---->
			<div id="small-dialog" class="mfp-hide">
				<div class="search-top">
					<div class="login">
						<input type="submit" value=""> <input type="text"
							value="Type something..." onfocus="this.value = '';"
							onblur="if (this.value == '') {this.value = '';}">
					</div>
					<p>Shopping</p>
				</div>
			</div>
			<!---->
		</div>
	</div>
	<div class="copyrights">
		Collect from <a href="http://www.cssmoban.com/"></a>
	</div>
	<!--content-->
	<div class="content">
		<div class="container">
			<div class="content-top">
				<h1>商品列表</h1>
				<c:forEach var="good" items="${goods }" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0 }">
							<div class="content-top1">
						</c:when>
						<c:when test="${status.index!=0 && status.index%4==0 && !status.last}">
							<div class="clearfix"> </div>
							</div>
							<div class="content-top1">
						</c:when>
					</c:choose>
					
					<div class="col-md-3 col-md2">
						<div class="col-md1 simpleCart_shelfItem">
							<a href="detail.do?id=${good.id }">
							 <img class="img-responsive" style="height: 207px;width: 185px" src="${good.indexImg }" alt="" id="index_item_img"/>
							 <c:if test="${good.status == 2 }">
							 	 <div class="superscript">
										<div class="triangle">
										</div>
									<div class="desc">已购买</div>
								</div>
							 </c:if>
							 <c:if test="${good.status==1 }">
							 	<div class="superscript">
										<div class="triangle" style="border-top: 50px solid #888888;">
										</div>
									<div class="desc" style="font-size: 5px;line-height: 45px; top: 0px; left: 10px">已售${good.selledCount }件</div>
								</div>
							 </c:if>
							</a>
							<h3>
								<a href="detail.do?id=${good.id }">${good.title }
								</a>
							</h3>
							<div class="price">
								<h5 class="item_price">￥<fmt:formatNumber type="number" value="${good.price/100.0 }" pattern="0.00" maxFractionDigits="2"/>  </h5>
								
								<c:if test="${user.type==1 }">
									<a style="cursor: pointer;" class="item_add" onclick="addcart(${good.id });">添加购物车</a>
								</c:if>
								<c:if test="${user.type==2 && good.status!=1 }">
									<a style="cursor: pointer;" onclick="deleteGood(${good.id });">删除</a>
								</c:if>

								<div class="clearfix"></div>
							</div>
						</div>
					</div>
					<c:if test="${status.last }">
						<div class="clearfix"> </div>
						</div>
					</c:if>
				</c:forEach>

				
				<div class="row">
					<div class="col-md-12">
						<jsp:include page="pager.jsp">
							 <jsp:param value="index.do" name=" reqURL"/>  
						</jsp:include>
					</div>
				</div>
			</div>
		</div>
		<div class="container">

		</div>
	</div>
	<!--//content-->
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>