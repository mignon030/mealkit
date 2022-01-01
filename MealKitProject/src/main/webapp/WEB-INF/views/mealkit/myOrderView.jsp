<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${member.id}님의 주문 조회</title>
<%@ include file="../include/includeFile.jsp" %>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@400;800&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');

/* visual */
.t_visual { height:300px; width:100%; background:url("/resources/images/food3.jpg") no-repeat top center; position:relative; margin-bottom:70px; }
.t_visual .inner { width:960px; position:relative; }
.t_visual_black { height:300px; width:100%; background:rgba(0,0,0,0.5); position:absolute; }

.me { position:absolute; left:0; top:0; margin-top:90px; }
.me p { color:#fff; font-family:'Nanum Myeongjo',serif; }
.me p:first-child { font-weight:800; font-size:40px; margin-bottom:15px; }
.me p:last-child { font-size:20px; }

/* myContent */
#myContent { height:750px; }
#myContent .inner { width:960px; }
#myContent .inner:after { content:""; clear:both; display:block; }

.left_bar { float:left; }
.left_bar .myShopping { width:200px; border-bottom:1px solid #888; margin-bottom:20px; }
.left_bar .myShopping:last-child { border:0; }
.left_bar .myShopping li { margin-bottom:20px; position:relative; }
.left_bar .myShopping li:first-child { color:#333; font-weight:700; font-size:20px; } 
.left_bar .myShopping li a { color:#666; font-weight:300; font-size:15px; }
.glyphicon-chevron-right { color:#666; position:absolute; right:0; top:4px; }

.m_content { float:right; width:700px; }
.m_content .order { font-size:20px; }

th { text-align:center; }

</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="t_visual">
	<div class="t_visual_black"></div>
	
	<div class="inner">
		<div class="me">
			<p>${member.name}</p>
			<p>${member.id}님 환영합니다.</p>
		</div>
	</div>
</div>

<div id="myContent">
	<div class="inner">
		<div class="left_bar">
			<ul class="myShopping">
				<li>나의 쇼핑 정보</li>
				<li><a href="myPage">주문/배송 조회 및 변경</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="../shop/goReviewPage">상품후기 작성</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="../board/boardList">1:1 문의</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="../shop/zzim">내가 찜한 제품</a><span class="glyphicon glyphicon-chevron-right"></span></li>
			</ul>
			
			<ul class="myShopping">
				<li>나의 계정</li>
				<li><a href="updateMypage">회원 정보 수정</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="#">쿠폰</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="#">적립금</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="cc_id">추천인 아이디</a><span class="glyphicon glyphicon-chevron-right"></span></li>
			</ul>
			
			<ul class="myShopping">
				<li>고객센터</li>
				<li><a href="#">조리법</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="../notice_board/notice_list">공지사항</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="#">자주 묻는 질문</a><span class="glyphicon glyphicon-chevron-right"></span></li>
				<li><a href="#">업데이트 소식</a><span class="glyphicon glyphicon-chevron-right"></span></li>
			</ul>
		</div>
		
		<div class="m_content">
			<p class="order">주문/배송 조회 </p>
			<hr>
			<table class="table table-bordered table-hover">
				<thead>
					<tr class="info">
						<th align=center>주문번호</th>
						<th align=center>주문날짜</th>
						<th align=center>상품명</th>
						<th align=center>가격</th>
						<th align=center>수량</th>
						<th align=center>주문상태</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="orderView" items="${orderView}">				
						<tr>
							<td align=center>${orderView.order_id}</td>
							<td align=center>${orderView.buy_date}</td>
							<td align=center>${orderView.mk_name}</td>
							<td align=center><fmt:formatNumber pattern="###,###,###" value="${orderView.buy_price}" /> 원</td>
							<td align=center>${orderView.buy_count}</td>
							<td class="delivery" align=center>${orderView.delivery}</td>
						</tr>
					</c:forEach>
				</tbody>
		
	 			<tr>
					<td class="amount" align="right" colspan="6">
						<c:choose>
							<c:when test= "${allMoney < 30000}">
								주문금액 <fmt:formatNumber value="${allMoney}" />원 + 배송비 5,000원 = <fmt:formatNumber value="${allMoney + 5000}" />원
							</c:when>
							
							<c:when test= "${allMoney > 30000}">
								주문금액 <fmt:formatNumber value="${allMoney}" />원 + 배송비 0원 = <fmt:formatNumber value="${allMoney}" />원
							</c:when>
						</c:choose>
					</td>
				</tr>
			</table>		
			
			<table class="table table-bordered table-hover orderInfo">
				<c:forEach items="${orderView}" var="orderView" varStatus="status">
					<c:if test="${status.first}">
						<thead>
							<tr class="info">
								<th align=center>수령인 이름</th>
								<th align=center>수령인 휴대폰 번호</th>
								<th align=center>수령인 주소</th>
								<th align=center>최종 결제금액</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td align=center>${orderView.delivery_name}</td>
								<td align=center>${orderView.delivery_tel}</td>
								<td align=center>${orderView.delivery_address}</td>
								<td align=center>
									<c:choose>
										<c:when test="${allMoney < 30000}">
											<fmt:formatNumber value="${allMoney + 5000}" />원
										</c:when>
										
										<c:when test="${allMoney > 30000}">
											<fmt:formatNumber value="${allMoney}" />원
										</c:when>
									</c:choose>	
								</td>
							</tr>
						</tbody>		
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>
</body>
</html>