<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<caption>게시물 상세보기</caption>
<tr>
	<th>번호</th>
	<td>${boardDTO.no}</td>
</tr>
<tr>
	<th>제목</th>
	<td>${boardDTO.title}</td>
</tr>
<tr>
	<th>작성자</th>
	<td>${boardDTO.name}</td>
</tr>
<tr>
	<th>내용</th>
	<td>${boardDTO.content}</td>
</tr>
<tr>
	<th>작성일</th>
	<td><fmt:formatDate value="${boardDTO.regdate}" type="both" /></td>
</tr>
<tr>
	<th>조회수</th>
	<td>${boardDTO.readcount}</td>
</tr>
<tr>
	<th>첨부파일</th>
	<td></td>
</tr>
</table><br/>

<ul>
	<c:forEach items ="${reply}" var ="reply">
	<li>
		<div>
			<p>${reply.name} / ${reply.regdate}</p>
			<p>${reply.re_context}</p>
	</div>
	</li>
	</c:forEach>
</ul>
<!-- 댓글을 작성 할 때 마다 작성자와 시간 / 내용을 작성하는 영역이 생성되게 하였다. -->
<div> 

 <form method="post">
		<p>
			<label>댓글 작성자</label><input type ="text" name="name">
		</p>
		<p>
			<textarea rows ="5" cols="50" name="re_context"></textarea>
		</p>
		<p>
			<input type = "hidden" name="re_no" value="${boardDTO.no}">
			<button type = "submit">댓글 등록</button>
		</p>
	</form>
</div>
<!-- 해당 글 번호에 따른 댓글을 입력할 수 있도록 하였다. -->
<!-- 댓글 삭제 기능 구현 x -->
<!-- 댓글 끝   -->

<a href="../">리스트</a> |
<a href="update">수정</a> |
<a href="delete">삭제</a>
</body>
</html>