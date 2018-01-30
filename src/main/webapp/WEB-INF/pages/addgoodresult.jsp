<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>操作结果</title>
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
<!-- slide -->
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<div class="jumbotron" style="margin-top: 20px">
		        <center><h2>${info }</h2></center><br>   
		        <p>
		          <center>
		          <a class="cart" href="publish.do" role="button">继续添加</a>
		          <a class="cart" href="detail.do?id=${id }" role="button">查看信息</a>
		          <a class="cart" href="index.do" role="button">返回主页</a>
		          </center>
		        </p>
		     </div>
	     </div>
	     <div class="col-md-3"></div>
	    </div>
     </div>
     
     <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>