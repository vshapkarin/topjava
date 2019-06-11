<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <table border="1">
        <tr>
            <td>id</td>
            <td>Дата и время</td>
            <td>Описание</td>
            <td>Калории</td>
        </tr>
    <c:set var="formatter" value="${DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")}"/>
    <c:forEach items="${meals}" var="meal">
                <tr style="color: ${meal.excess ? 'red' : 'limegreen'}">
                    <td>${meal.id}</td>
                    <td>${formatter.format(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td style="border: 0; background: white"><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
                    <td style="border: 0; background: white"><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                </tr>
    </c:forEach>
    </table>
    <a href="meals?action=create">Создать</a>
</body>
</html>
