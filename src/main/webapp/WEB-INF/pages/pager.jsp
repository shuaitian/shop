<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>出错啦</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/simpleCart.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>
</head>
<body>
	<nav aria-label="...">
		<ul class="pager">
			<li class="${curPage==previous?'disabled':'' }"><a
				<c:if test="${curPage!=previous }">
						    		href="${curURIWithoutQS }?curPage=${previous }"
						    	</c:if>>上一页</a></li>
			<li class="${curPage==next?'disabled':'' }"><a
				<c:if test="${curPage!=next }">
						    		href="${curURIWithoutQS }?curPage=${next }"
						    	</c:if>>下一页</a></li>
		</ul>
	</nav>
</body>
</html>