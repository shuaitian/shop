<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 
<script type="text/javascript" src="js/ueditor.config.js"></script>
<script type="text/javascript" src="js/ueditor.all.min.js"></script> 
<script type="text/javascript" src="js/lang/zh-cn/zh-cn.js"></script>   -->
<script type="text/javascript" src="js/wangEditor.js"></script>
</head>
<body>
	<form class="form-horizontal" method="post" action="addgood.do"
		enctype="multipart/form-data" name="fileForm">
		<input type="hidden" name="id" value="${good.id }" />
		<div class="form-group ${titleError!=null?'has-error':'' }">
			<label for="inputEmail3" class="col-sm-2 control-label">标题</label>
			<div class="col-sm-9">
				<input type="input" class="form-control" id="title" name="title"
					value="${good.title }" placeholder="2-80字符">
			</div>
		</div>
		<div class="form-group ${summaryError!=null?'has-error':'' }">
			<label for="inputEmail3" class="col-sm-2 control-label">摘要</label>
			<div class="col-md-9">
				<input type="input" class="form-control" id="sumarry" name="summary"
					value="${good.summary }" placeholder="2-140字符">
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2 control-label">上传方式</label>
			<div class="col-sm-8">
				<label> <input type="radio" name="picture" id="pictype"
					value="url" ${pictype!=1?"checked":"" }> 图片地址
				</label> <label> <input type="radio" name="picture" id="pictype"
					value="local" ${pictype==1?"checked":"" }> 本地上传
				</label> <label> <span>&nbsp;&nbsp;(最多上传3张图片)</span>
				</label>
			</div>

			<div class="col-sm-2"></div>
		</div>

		<input type="hidden" name="picurl" id="picurl" value="${good.picurl }">
		<div class="form-group ${picurlError!=null?'has-error':'' }"
			id="pictypeDiv">

			<c:if test="${pictype!=1 }">
				<label for="inputEmail3" class="col-sm-2 control-label">URL</label>
				<div class="col-sm-7">
					<input id="urlText" type="text" class="form-control" value=""></input>
				</div>
				<div class="col-sm-2">
					<div class="col-sm-2">
						<button type="button" class="btn btn-primary" onclick="getimg();"
							id="getimgbtn">添加</button>
					</div>
				</div>
			</c:if>
			<c:if test="${pictype==1 }">
				<label for="inputEmail3" class="col-sm-2 control-label">文件</label>
				<div class="col-sm-7">
					<input type="file" name="localFile">
				</div>
				<div class="col-sm-2">
					<button type="button" id="uploadpicbtn" class="btn btn-primary"
						onclick="uploadpic();">添加</button>
				</div>
				<div class="col-sm-1">
					<font color="red">${picurlError!=null?"*":"" }</font>
				</div>
				<input type="hidden" name="pictype" value="1" />
			</c:if>
		</div>

		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2 control-label">已添加</label> <input
				type="hidden" value="${good.picurl }" id="added_imgs" />
			<div id="picsmall"></div>
		</div>
		<div class="form-group ${contentError!=null?'has-error':'' }">
			<label for="inputEmail3" class="col-sm-2 control-label">正文</label>
			<div class="col-sm-9">
				<!-- textarea class="form-control" rows="3" placeholder="2-1000字符"
					name="content">${good.content }</textarea -->
				<!-- 
				<script id="editor" type="text/plain"></script> -->
				
				<div id="contentDiv">
					
				</div>
				<input type="hidden" name="content" id="content" value="<c:out value="${good.content }"/>"/>
				
			</div>
		</div>

		<div class="form-group ${priceStrError!=null?'has-error':'' }">
			<label for="inputEmail3" class="col-sm-2 control-label">价格</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="price" name="priceStr"
					value="${good.price!=null?(good.price/100):'' }">
			</div>
			<div class="col-sm-1">
				<label style="margin-left: 0px; text-align: center;">元</label>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" onclick="formsubmit();"
					class="btn btn-default" id="publishButton">发布</button>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-11">
				<!-- <font color="red">${allErrors }</font> -->
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">
	var E = window.wangEditor
	var editor2 = new E('#contentDiv')
	editor2.create()
	
	$(function(){
		var content = $("#content").val();
		editor2.txt.html(content);
	})
</script>
</html>