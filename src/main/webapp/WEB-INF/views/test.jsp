<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty nome}">
        ${nome}
    </c:when>
    <c:otherwise>
        <!-- You can put anything here, or leave it empty if you don't want to display anything when "nome" is not valorizzato. -->
    </c:otherwise>
</c:choose>
</body>
</html>