<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
	<div class="check-out" id="checkOutDiv">
		  <c:if test="${cart != null }">
		  	<h1>购物车</h1>
		  </c:if>
    	    <table id="cartTable">
    	  <c:if test="${cart!=null }">
    	  		  <tr>
					<th>商品</th>
					<th>数量</th>		
					<th>单价</th>
					<th>总价</th>
					<th>操作</th>
				  </tr>
    	  </c:if>
    	  <c:if test="${cart==null }">
    	  	<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="jumbotron" style="margin-top: 20px">
			        <center><h2>购物车为空</h2></center><br>   
			        <p>
			          <center>
			          <a href="${lastURI }" class="to-buy" id="cartButton" style="cursor: pointer;">退出</a>&nbsp;&nbsp;&nbsp;&nbsp;
			          <a class="cart" href="index.do" role="button">返回主页</a>
			          </center>
			        </p>
			     </div>
		     </div>
		     <div class="col-md-3"></div>
		    </div>
    	  </c:if>
    	  <c:forEach items="${cart.items }" var="item">
    	  	<tr id="tr_${item.id }">
				<td class="ring-in"><a href="detail.do?id=${item.id }" class="at-in"><img src="${item.good.indexImg }" class="img-responsive" alt=""></a>
				<div class="sed">
					<h5>${item.good.title }</h5>
					<p>${item.good.summary }</p>
				
				</div>
				<div class="clearfix"> </div></td>
				<td class="check"><input type="text" value="${item.count }" style="width:55px" id="itemCount">
					<input type="hidden" value="${item.id }"/>
					<input type="hidden" value="${item.price/100 }">
				</td>		
				<td><fmt:formatNumber type="number" value="${item.price/100 }" pattern="0.00" maxFractionDigits="2"/></td>
				<td id="priceTD_${item.id }"><fmt:formatNumber type="number" value="${item.price * item.count/100 }" pattern="0.00" maxFractionDigits="2"/></td>
				<td><a onclick="deleteCartItem(${item.id });" class="cart" style="cursor: pointer;">删除</a></td>
			 </tr>
    	  </c:forEach>
	</table>
	<div class="row">
		<div class="col-md-12">
		<c:if test="${cart != null }">
			<center>
			<input type="hidden" name="lastURI" id="lastURI" value="${lastURI }"/>
			<a href="${lastURI }" class="to-buy" id="cartButton" style="cursor: pointer;">退出</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a onclick="createOrder();" class="to-buy" id="cartButton" style="cursor: pointer;">确定购买</a>
			</center>
		</c:if>
		</div>
	</div>
	<div class="clearfix"> </div>
    </div>
</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>