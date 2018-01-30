<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/jquery-confirm.css" />
<script type="text/javascript" src="js/jquery-confirm.js"></script>
<script type="text/javascript" src="js/md5.js"></script>
</head>
<body>
	<script type="text/javascript">
	function login(){
		var form = $("#loginForm");
		var pwd = $("#password").val();
		var pwdMD5 = hex_md5(pwd);
		$("#password").val(pwdMD5);
		var options = {
				url : 'loginjson.do',  
				type : 'post',
				success : function(data) {
					if(data.status=='200'){
						location.reload();
						//$('#myModal').modal('hide');
						
					}else{
						$("#info").html(data.error);
					}
					
				}
			};
		form.ajaxSubmit(options);
	}
	</script>
	<!--header-->
	<div class="header">
		<div class="header-top">
			<div class="container">
				<div class="col-md-4">
						<a href="index.do" class="cart">首页</a>
						<c:if test="${user.type==2 }">
								<a href="publish.do" class="cart">发布</a>
								<!-- a class="btn btn-info" href="publish.do" role="button">发布</a -->
						</c:if>
						<c:if test="${user.type==1 }">
							<a href="buyed.do" class="cart">账务</a>
							<a href="cart.do?curURI=${curURI }" class="cart">购物车</a>
						</c:if>
				</div>
				<div class="col-md-3 logo">
					<a href="index.do"><img src="images/logo.png" alt=""></a>
				</div>

				<div class="col-md-4 header-left">
					<c:if test="${user==null }">
						<p class="log">
							游客你好，请<a href="" data-toggle="modal" data-target="#loginModal">登录</a>
						</p>
					</c:if>
					<c:if test="${user!=null }">
						<p class="log">
							<c:if test="${user.type==1  }">
								买家，${user.username }
							</c:if>
							<c:if test="${user.type==2  }">
								卖家，${user.username }
							</c:if>
							<a href="logout.do">[退出]</a>
						</p>
					</c:if>

					<div class="cart box_1">
						<a href="cart.do">
							<h3>
								<div class="total">
									￥<span class="" id="cartTotal"><fmt:formatNumber type="number" value="${totalPrice/100 }" pattern="0.00" maxFractionDigits="2"/> </span>
								</div>
								<img src="images/cart.png" alt="" />
							</h3>
						</a>
						<p>
							<a style="cursor: pointer;" onclick="clearCart();" class="simpleCart_empty">清空购物车</a>
						</p>

					</div>

					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--//header-->
	
	<!-- Modal -->
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">登录</h4>
	      </div>
	      <div class="modal-body">
	        <div class="container">
			<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-5">
			<h1>用户登录</h1>
			<br>
			<span id="info" style="color: red;"></span>
			</div>
			</div>
			<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-4">
			<span id="info" style="color: red;"></span>
			</div>
			</div>
			<div class="row">
					<div class="col-md-1"></div>
				   <div class="col-md-4 login-right">
				   <c:if test="${user==null }">			  
					<form method="post" action="login.do" id="loginForm">
	
						<span>用户名：</span>
						<input type="text" name="username"/> 
					
						<span>密码：</span>
						<input type="password" id="password" class="password" name="password" value=""> 
				    </form>
				   </c:if>
				   </div>
				   <div class="col-md-3"></div>
				   <div class="clearfix"> </div>
				 </div>
		 </div>
	      </div>
	      <div class="modal-footer">
	        <a type="button" class="cart" onclick="login();">登录</a>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>