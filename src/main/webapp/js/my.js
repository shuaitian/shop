var img_count = 0; //当前添加过的图片的数量，包括已经删除的
var cur_img_count = 0;//当前存在的图片的数量，不包括已经删除的
var arr_remove = new Array();//被删除的图片的集合
var picurl_all = new Array();//所有被添加过的图片的集合
var max_img_count = 3;//最多可以上传的图片的数量

function processContent(){
	var content = editor2.txt.html();
	if(content == '<p><br></p>')
		content = '';
	$("#content").val(content);
}

function formsubmit() {
	processContent();
	$("form[name=fileForm]").submit();
}

function toPrice(val){
	return new Number(val).toFixed(2);  
}

function getimg() {
	if(cur_img_count >= max_img_count){
		$.alert({
			title: '提示',
			content: '不能继续添加啦'
		});
		return ;
	}
	var src = $("#urlText").val();
	if(src == '')
		return ;
	var content = 	'<div class="col-sm-1" id="img_'+img_count+'">'
	+		'<div class="row">'
	+		 	'<img src="'+src+'" width="70px" height="50px" style="padding-left:15px;">'
	+		'</div>'
	+		'<div class="row">'
	+			'<a style="padding-left:20px; cursor:pointer;" onclick="deleteImg('+img_count+')">删除</a>'
	+		'</div>'
	+	'</div>';
	
	
	var old = $("#picsmall").html();
	$("#picsmall").html(old+content);
	picurl_all.push(src);
	
	url = getImgURL();
	$("#picurl").val(url);
	img_count++;
	cur_img_count++;
}


function uploadpic() {
	if(cur_img_count >= max_img_count){
		$.alert({
			title: '提示',
			content: '不能继续添加啦'
		});
		return ;
	}
	var form = $("form[name=fileForm]");
	var options = {
		url : 'uploadpic.do', // 上传文件的路径
		type : 'post',
		success : function(data) {
			console.log(data);

			if (data.status == '200') {
//				$.confirm({
//					title : '提示!',
//					content : '上传成功',
//					confirm : function() {
//
//					},
//				});
				var src = data.picURL;
				var content = 	'<div class="col-sm-1" id="img_'+img_count+'">'
							+		'<div class="row">'
							+		 	'<img src="'+src+'" width="70px" height="50px" style="padding-left:15px;">'
							+		'</div>'
							+		'<div class="row">'
							+			'<a style="padding-left:20px; cursor:pointer;" onclick="deleteImg('+img_count+')">删除</a>'
							+		'</div>'
							+	'</div>';
				var old = $("#picsmall").html();
				$("#picsmall").html(old+content);
				
				picurl_all.push(src);
				
				//alert(getImgURL());
				url = getImgURL();
				$("#picurl").val(url);
				img_count++;
				cur_img_count++;
			} else {
				var info = data.error;
				$.confirm({
					title : '提示!',
					content : info,
					confirm : function() {

					},
				});
			}
		}
	};
	form.ajaxSubmit(options);

}

function getImgURL(){
	var ret = '';
	for(j=0;j<picurl_all.length;j++){
		
		if(arr_contains(arr_remove,j))
			continue;
		
		ret = ret + ";" + picurl_all[j];
	}
	
	return formatURLs(ret);
}

function arr_contains(arr,ele){
	for(i=0;i<arr.length;i++){
		if(arr[i] == ele)
			return true;
	}
	return false;
}

function deleteImg(id){
	$("#img_" + id).remove();
	arr_remove.push(id);
	url = getImgURL();
	$("#picurl").val(url);
	cur_img_count--;
}

function formatURLs(urls){
	if(urls.charAt(0) == ';')
		urls = urls.substr(1);
	if(urls.charAt(urls.length-1) == ';')
		urls = urls.substr(0,urls.length-1);
	return urls;
}

$(function() {

	$('input:radio[name="picture"]')
			.click(
					function() {
						// alert("您是..." + $(this).val());
						var v = $(this).val();
						if (v == "url") {
							var content = '<label for="inputEmail3" class="col-sm-2 control-label">URL</label>'
									+ '<div class="col-sm-7">'
									+ '<input id="urlText" type="text" class="form-control" ></input></div>'
									+ '<div class="col-sm-2">'
									+ '<div class="col-sm-2"><button type="button" class="btn btn-primary" onclick="getimg();">添加</button></div>'
									+ '</div>' + '<div class="col-sm-1"></div>';
							$("#pictypeDiv").html(content);
						}
						if (v == "local") {
							var content = '<label for="inputEmail3" class="col-sm-2 control-label">文件</label>'
									+ '<div class="col-sm-7">'
									+ '<input type="file" name="localFile"></div>'
									+ '<div class="col-sm-2"><button type="button" id="uploadpicbtn" class="btn btn-primary" onclick="uploadpic();">添加</button></div>'
									+ '<div class="col-sm-1"></div>'
									+ '<input type="hidden" name="pictype" value="1"/>';
							$("#pictypeDiv").html(content);
						}
					});

});

function setnum(num) {
	$("#count").val(num);
}

function numplus() {
	var old = $("#count").val();
	setnum(parseInt(old) + 1);
}

function numsub() {
	var old = $("#count").val();
	if (old == "1") {
		return;
	}
	setnum(parseInt(old) - 1);
}

function submitupdate() {
	processContent();
	var form = $("form[name=fileForm]");

	var options = {
		url : 'updategood.do', // 上传文件的路径
		type : 'post',
		success : function(data) {
			if (data.status == 200) {
				location.reload();
				// $('#myModal').modal('hide');

			} else {
				$.confirm({
					title : '提示!',
					content : data.error,
				});
			}

		}
	};
	form.ajaxSubmit(options);
}

function isEmpty(arg){
	return arg=='' || arg==null || arg==undefined;
}

$(function(){
	picurls = $("#picurls").val();
	if(isEmpty(picurls)){
		return ;
	}
	attr = picurls.split(";");
	var content = '';
	for(i=0;i<attr.length;i++){
		content = content
			+	'<li data-thumb="'+attr[i]+'" >'
			+	'<div class="thumb-image"> <img src="'+attr[i]+'" data-imagezoom="true" class="img-responsive"> </div>'
			+	'</li>';
	}
	
	$("#slides").html(content);
});

function getFirstImgFromURLs(urls){
	return urls.split(";")[0];
}

$(function(){
	picurls = $("#added_imgs").val();
	if(isEmpty(picurls)){
		return ;
	}
	attr = picurls.split(";");
	var content = '';
	for(i=0;i<attr.length;i++){
		var content = content 
		+		'<div class="col-sm-1" id="img_'+i+'">'
		+		'<div class="row">'
		+		 	'<img src="'+attr[i]+'" width="50px" height="50px" style="padding-left: 15px">'
		+		'</div>'
		+		'<div class="row">'
		+			'<a style="padding-left: 15px" onclick="deleteImg('+i+')">删除</a>'
		+		'</div>'
		+	'</div>';
		
		picurl_all.push(attr[i]);
	}
	$("#picsmall").html(content);
	img_count = attr.length;
	cur_img_count = attr.length;
})

function updateCartTotal(curPrice){
	$("#cartTotal").html(toPrice(curPrice/100.0));
}
function addCartByCount(id,count){
	$.confirm({
	    title: '提示',
	    content: '确定加入购物车吗',
	    buttons: {
	        	确定: function () {
	        		var url = "cart/add.do?id=" + id + "&count=" + count;
	        		$.get(url,function(data){
	        			if(data.status == 200){
	        				updateCartTotal(data.totalPrice);
	        			}
	        			else{
	        				$.alert({
	        					title: '提示',
	        					content: data.error
	        				});
	        			}
	        		});
	       		},
		        取消: function () {
		        }
	    }
	});
}
function addcart(id){
	addCartByCount(id,1);
}

function addCartWithCount(id){
	var count = $("#count").val();
	addCartByCount(id,count);
}

function deleteGood(id){
	
	$.confirm({
	    title: '提示',
	    content: '确定删除吗',
	    buttons: {
	        	确定: function () {
		        	data = $.get("delete.do?id="+id,function(data){
		        		if(data.status==200){
			        		$.alert({
			        		    title: '提示!',
			        		    content: '删除成功!',
			        		    buttons: {
			        		        OK: function () {
			        		            location.reload();
			        		        },
			        		    }
			        		});
			        	}
			        	else{
			        		$.alert({
			        			title: '提示!',
			        			content: data.error,
			        		});
			        	}
		        	});	
		        	
	       		},
		        取消: function () {
		            
		        }
	    }
	});
	
	
}

function deleteCartItem(id){
	$.confirm({
	    title: '提示',
	    content: '确定删除吗',
	    buttons: {
	        	确定: function () {
		        	data = $.ajax({url:"cart/delete.do?id="+id,async:false});	
		        	$.get("cart/delete.do?id="+id,function(data){
		        		if(data.status==200){
			        		$("#tr_"+id).remove();
			        		updateCartTotal(data.totalPrice);
		        		}
			        	else{
			        		$.alert({
			        			title: '提示!',
			        			content: data.error,
			        		});
			        	}
		        	});
		        	
	       		},
		        取消: function () {
		            
		        }
	    }
	});
}

function clearCart(){
	$.get("cart/clear.do",function(data){
		if(data.status != 200){
			$.alert({
				title: '提示',
				content: data.error,
			});
			return ;
		}
		$.alert({
			title:"提示",
			content:'清空成功'
		});
		updateCartTotal(data.totalPrice)
		var obj = $("#cartTable");
		if(obj.html() != undefined){
			content = '<div class="row">'
				+'<div class="col-md-3"></div>' 
				+'<div class="col-md-6">'
				+'<div class="jumbotron" style="margin-top: 20px">'
			    +    '<center><h2>购物车为空</h2></center><br>'
			    +    '<p>'
			    +    '  <center>'
			    +    '<a href="'+$("#lastURI").val()+'" class="to-buy" id="cartButton" style="cursor: pointer;">退出</a>&nbsp;&nbsp;&nbsp;&nbsp;'
			    +    '  <a class="cart" href="index.do" role="button">返回主页</a>'
			    +    '  </center>'
			    +    '</p>'
			    + '</div>'
			    +'</div>'
			    +'<div class="col-md-3"></div>'
			    +'</div>';
			$("#checkOutDiv").html(content);
		}
	});
	
}

function info(data){
	$.alert({
		title: '提示',
		content: data
	});
}



$(function(){
	$("#cartTable input[type=text]").on('input',function(e){  
		var id = $(this).next().val();
		var price = $(this).next().next().val();
		var count = $(this).val();
		if(count == ''){
			return ;
		}
		
		$.get("cart/set.do?id=" + id + "&count=" + count,function(data){
			if(data.status!=200){
				info(data.error);
				return ;
			}
			
			updateCartTotal(data.totalPrice)
			
			newPrice = parseFloat(price) * count;
			$("#priceTD_"+id).html(toPrice(newPrice));
		});
	}); 
});

function createOrder(){

	
	$.confirm({
	    title: '提示',
	    content: '确定购买吗',
	    buttons: {
	        	确定: function () {
	        		$.get("order/create.do",function(data){
	        			if(data.status == 200){
	        				$.confirm({
	        					title:'提示',
	        					content:'购买成功',
	        					buttons:{
	        						确定:function(){
	        							window.location.href="buyed.do";
	        						}
	        					}
	        				});
	        			}
	        			else{
	        				info(data.error);
	        			}
	        		});
	       		},
		        取消: function () {
		            
		        }
	    }
	});
}

