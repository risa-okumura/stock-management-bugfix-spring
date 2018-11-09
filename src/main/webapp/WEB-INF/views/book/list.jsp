<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/common.jsp"  %>
<body>
<div class="container">
	<c:out value="${member.name}"/>さん　こんにちは！<br>
	<a href="${pageContext.request.contextPath}/logout/sessionInvalidate">ログアウト</a>
	
	<form:form action="${pageContext.request.contextPath}/book/insertBook" modelAttribute="bookForm" method="post" enctype="multipart/form-data">
		
		書籍名：<form:input path="name"/>
		著者：<form:input path="author"/><br>
		出版社：<form:input path="publisher"/>
		価格：<form:input path="price"/><br>
		ISBN：<form:input path="isbncode"/>
		発売日：<form:input path="saledate" type="date"/><br>
		説明：<form:input path="explanation"/><br>
		画像:<input name="image" type="file" accept="image/*">
		在庫数：<form:input path="stock"/><br>
		<input type="submit" value="書籍情報の登録">
	
	</form:form>
	
	
	
	<h3>書籍一覧</h3>
	<div class="span8">
		<div class="row">
			<table class="table table-striped">
			  <tr>
			    <th>書籍</th>
			    <th>在庫数</th>
			  </tr>
			  <c:forEach var="book" items="${bookList}">
			  <tr>
			    <td>
			      <a href="${pageContext.request.contextPath}/book/show/${book.id}"><c:out value="${book.name}" /></a>
			    </td>
			    <td><c:out value="${book.stock}"/></td>
			  </tr>
			  </c:forEach>
			</table>


		</div>
	</div>
</div>
</body>
</html>
