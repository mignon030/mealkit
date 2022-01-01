<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1 문의</title>
<%@ include file="../include/includeFile.jsp" %>
<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
.container { margin-top:70px; margin-bottom:70px; }

.notice { font-size:20px; margin-bottom:30px; }
tr th:first-child { text-align:center; }
</style>

</head>
<body>
<%@ include file="../include/header.jsp" %>

<div class="container">
	<div align="center">
		<p class="notice">1:1 문의</p>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-10">
			<a href="${path}/board/boardWrite" class="btn btn-primary">게시글 쓰기</a>
		</div>
	</div>
	<table class="table table-bordered table-striped table-hover">
		<thead>
			<tr>
				<th>번호</th>
				<th>문의유형</th>
				<th>제    목</th>
				<th>작성일자</th>
			</tr>
		</thead>
		<tbody>
			<% int i=0; %>
			<c:forEach items="${boardlist}" var="Boardlist">
			<tr>
				<td align="center"><%=++i%></td>
				<td>${Boardlist.b_kind}</td>
				<td><a href="/board/boardView?bno=${Boardlist.bno}">${Boardlist.title}</a></td>
				<td><fmt:formatDate value="${Boardlist.b_reg_Date}" pattern="yyyy년 MM월 dd일"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>	

<%@ include file="../include/footer.jsp" %>
</body>
</html>