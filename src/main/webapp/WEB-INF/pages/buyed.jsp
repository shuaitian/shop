<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script src="js/simpleCart.min.js">
	<script src="js/simpleCart.min.js">
	<script src="js/jquery.form.js">
</script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/my.js"></script>
<title>购物车</title>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="container">
		<div class="check-out">
			<c:if test="${orderItems != null }">
				<h1>已购买商品</h1>
			</c:if>
			<table>
				<c:if test="${orderItems!=null }">
					<tr>
						<th>内容</th>
						<th>购买时间</th>
						<th>数量</th>
						<th colspan="2" style="text-align: right">购买价格</th>
					</tr>
				</c:if>
				<c:if test="${orderItems==null }">
					<div class="row">
						<div class="col-md-3"></div>
						<div class="col-md-6">
							<div class="jumbotron" style="margin-top: 20px">
								<center>
									<h2>没有购买任何商品</h2>
								</center>
								<br>
								<p>
								<center>
									<a class="cart" href="index.do" role="button">返回主页</a>
								</center>
								</p>
							</div>
						</div>
						<div class="col-md-3"></div>
					</div>
				</c:if>
				<c:forEach items="${orderItems }" var="item">
					<tr>
						<td class="ring-in"><a href="detail.do?id=${item.goodId }"
							class="at-in"><img src="${item.indexImg }"
								class="img-responsive" alt=""></a>
							<div class="sed">
								<h5>${item.title }</h5>
								<p>${item.summary }</p>

							</div>
							<div class="clearfix"></div></td>
						<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="check"><input type="text" value="${item.count }"
							style="width: 55px" readonly="readonly"></td>
						<td colspan="2" style="text-align: right"><fmt:formatNumber type="number"
								value="${item.price/100 }" pattern="0.00" maxFractionDigits="2" /></td>
					</tr>
				</c:forEach>
				<tr style="border-top:#111111 solid 1px;">
					<td colspan="4" style="text-align: right">总价：<fmt:formatNumber type="number" value="${buyedTotalPrice/100 }" pattern="0.00" maxFractionDigits="2"/></td>
				</tr>
			</table>
		</div>
		
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>