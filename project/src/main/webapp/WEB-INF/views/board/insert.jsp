	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
<table>
<caption>게시물 쓰기</caption>
<tr>
	<th>제목</th>
	<td><input type="text" name="title" autofocus="autofocus" 
		required="required"/></td>
</tr>
<tr>
	<th>이름</th>
	<td><input type="text" name="name" required="required"/></td>
</tr>
<tr>
	<th>비밀번호</th>
	<td><input type="password" name="password" required="required" /></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="content" rows="5" cols="40" 
		required="required"></textarea></td>
</tr>
<tr id="inputForm">

</tr>
<tr>
	<td colspan="2" align="center"><input type="submit" value="완료" /></td>
</tr>
</table>
</form>
<div class ='uploadDiv'>
	<input type ='file' name ='uploadFile' multiple>
</div>



<div class ='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
</div>
<style>
.uploadResult{
	width: 100%;
	background-color: gray;
}

.uploadResult ul{
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li{
	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}

.uploadResult ul li img{
	width: 100px;
}

.uploadResult ul li span{
	color: white;
}

.bigPictureWrapper{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: gray;
	z-index: 100;
	background: rgba(255,255,255,0.5);
}

.bigPicture{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img{
	width: 600px;
}


</style>
<div class = 'uploadResult'>
	<ul>
	</ul>
</div>

<button id = 'uploadBtn'>Upload</button>
<script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/dist/jquery.js" />"></script>
<script>
function showImage(fileCallPath){
	/* alert(fileCallPath); */
	$(".bigPictureWrapper").css("display","flex").show();
	$(".bigPicture")
	.html("<img src='/project/display?fileName=" +encodeURI(fileCallPath)+"'>")
	.animate({width: '100%', height: '100%'}, 1000);
	}

	$(".bigPictureWrapper").on("click", function(e){
		$(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
		setTimeout(()=> {
			$(this).hide();
		}, 1000);
	});
		
	$(document).ready(function(){
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880;
		
		function checkExtension(fileName, fileSize){
			if(fileSize >= maxSize){
				alert("파일 사이즈 초과");
				return false;
			}
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드 할 수 없습니다.");
				return false;
			}
			return true;
		}
	
		
		var cloneObj = $(".uploadDiv").clone();
		
		var uploadResult = $(".uploadResult ul");
		
			function showUploadedFile(uploadResultArr){
				var str = "";
				$(uploadResultArr).each(function(i, obj){
					if(!obj.image){
						var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
						var fileLink = fileCallPath.replace()
						str += "<li id="+obj.uuid+"><div><a href='${app}/download?fileName="+fileCallPath+"'>"+"<img src = './resources/img/attach.png'>"+obj.fileName+"</a>"+
								"<span data-file=\'"+fileCallPath+"\' data-type='file'>X</span>"+"</div></li>"
					}else{
						//str += "<li>" + obj.fileName + "</li>"
						var fileCallPath = encodeURIComponent(obj.uploadPath+ "/s_"+obj.uuid+"_"+ obj.fileName);
						
						var originPath = obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName;
						
						originPath = originPath.replace(new RegExp(/\\/),"/");
						str += "<li id="+obj.uuid+"><a href =\"javascript:showImage(\'"+originPath+"\')\"><img src='${app}/display?fileName="+fileCallPath+"'></a>"
								+ "<span data-file=\'"+fileCallPath+"\' data-type='image'>X</span>"+"</li>";
						
						//str += "<li><img src = '${app}/display?fileName="+fileCallPath+"'><li>";
					}
				});
				uploadResult.append(str);
			}
		$(".uploadResult").on("click","span", function(e){
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			console.log(targetFile);
			var id = $(this).parent("li").attr("id");
			
			$.ajax({
				url: 'deleteFile',
				data: {fileName: targetFile, type: type},
				dataType: 'text',
				type: 'POST',
					success: function(result){
						alert(result);
						$(".uploadResult li").remove("#"+id);
					}
			});
		});

		
		$("#uploadBtn").on("click", function(e){
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			
			for(var i = 0; i < files.length ; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				formData.append("uploadFile", files[i]);
			}
			
			$.ajax({
				url:'uploadAjaxAction',
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				dataType:'json',
				success:function(result){
					console.log(result);
					showUploadedFile(result);
					$(".uploadDiv").html(cloneObj.html());
					for (let i = 0; i < result.length; i++) {
						let tagArea = document.getElementById('inputForm');
						let newDiv = document.createElement('input');
						console.log(result[i].connect_num);
						newDiv.setAttribute('name', 'fileName');
						newDiv.setAttribute('value', 'mkmk');
						newDiv.setAttribute('type', 'hidden');
						tagArea.appendChild(newDiv);
						//  tagArea는 form을 생성할 태그 아이디를 지정해준다.
						
						/*  목적  */
						/*  form 내부에 동적으로 태그를 생성하여 value를 줌으로 써 
							submit 시에 함께 데이터를 받을 생각이었다.
							받을 값은 file의 이름, 경로, uuid 등 혹은 새로운 컬럼을 추가하
							board table의 sequnce_num과 매치시켜 글번호에 맞는 file을
							불러와야겠다는 생각을 했다. -> 실패						   */
					}
					alert("Uploaded");
				}
			});
		});
	});
</script>
</body>
</html>