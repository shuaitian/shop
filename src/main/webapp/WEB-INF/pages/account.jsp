<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Account</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Fashion Mania Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- start menu -->
<link href="css/memenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/memenu.js"></script>
<script>$(document).ready(function(){$(".memenu").memenu();});</script>
<script src="js/simpleCart.min.js"> </script>
<script src="js/jquery.form.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- slide -->
</head>
<body>
<jsp:include page="head.jsp" />
<div class="account">
	<div class="container">
		<h1>用户登录</h1>
		<center><span style="color: red;">${info }</span></center>
		<div class="row">
			   <div class="col-md-3"></div>
			   <div class="col-md-6 login-right">
			   <c:if test="${user!=null }">
			   		<center>${user.username }已经登录,点此<a href="#">注销</a></center>
			   </c:if>
			   <c:if test="${user==null }">			  
				<form method="post" action="login.do">

					<span>用户名</span>
					<input type="text" name="username" value="${username }" /> 
				
					<span>密码</span>
					<input type="text" name="password" value=""> 
					<div class="word-in">
				  		<a class="forgot" href="#">忘记密码?</a>
				 		 <input type="submit" value="登录">
				  	</div>
			    </form>
			   </c:if>
			   </div>
			   <div class="col-md-3"></div>
			    <!-- div class="col-md-6 login-left">
			  	 <h4>NEW CUSTOMERS</h4>
				 <p>By creating an account with our store, you will be able to move through the checkout process faster, store multiple shipping addresses, view and track your orders in your account and more.</p>
				 <a class="acount-btn" href="register.html">Create an Account</a>
			   </div -->
			   <div class="clearfix"> </div>
			 </div>
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>