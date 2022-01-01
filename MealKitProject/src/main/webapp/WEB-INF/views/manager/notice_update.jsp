<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<%@ include file="../include/includeFile.jsp" %>
<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
.container { margin-top:70px; margin-bottom:70px; }
#header .inner .gnb li:nth-child(4) a { font-weight:700; }
</style>
<body>
<%@ include file="../include/m_header.jsp" %>

<div class="container">
	<form class="form-horizontal" method="post">
		<div class="form-group">
			<div class="col-sm-offset-3">
				<h1>공지사항 수정</h1>
			</div>
		</div>

		<!-- 게시글 번호를 숨겨서 넘겨준다. -->
		<input type="hidden" name="notice_bno" value="${noticeview.notice_bno}"/>
		
		<div class="form-group">
			<label class="control-label col-sm-2">제  목</label>
			<div class="col-sm-4">
				<!-- value="${view.title}"에 쌍따옴표를 제거하면 데이터에서 공백이 있는 부분까지만 출력된다. -->
				<input type="text" class="form-control" name="notice_title" maxlength="50" value="${noticeview.notice_title}"/>
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2">내  용</label>
			<div class="col-sm-4">
				<textarea rows="10" cols="120" name="notice_content">${noticeview.notice_content}</textarea>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-3">
				<button type="submit" class="btn btn-primary">수정</button>
			</div>
		</div>
	</form>
</div>

</body>

</html>