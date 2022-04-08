<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
<table>
<caption>게시물 삭제</caption>
<tr>
	<th>글번호</th>
	<td>${no}<input type="hidden"/></td>
	<!-- 컨트롤러에서 넘겨준 no이다. -->
</tr>

<tr>
	<th>비밀번호</th>
	<td><input ="password" name="password" required="required" autofocus="autofocus"/>
		* 처음 글 작성시 입력했던 비밀번호를 입력하세요 !
	</td>
</tr>

</table>
</form>
</body>
</html>