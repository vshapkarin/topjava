<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Дата и время: </dt>
            <dd><input type="datetime-local" name="datetime" value="${meal.dateTime}"></dd>
            <dt>Описание: </dt>
            <dd><input type="text" name="description" value="${meal.description}"></dd>
            <dt>Калории: </dt>
            <dd><input type="number" name="calories" value="${meal.calories}"></dd>
        </dl>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</body>
</html>
