<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>出错啦</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script src="js/simpleCart.min.js"> </script>
<script src="js/jquery.form.js" ></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>
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
		          <a class="cart href="index.do" role="button">返回主页</a>
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