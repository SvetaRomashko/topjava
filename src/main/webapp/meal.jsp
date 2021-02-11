<%--
  Created by IntelliJ IDEA.
  User: Светлана
  Date: 09.02.2021
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create new meal</title>
</head>
<body>

<h2>Edit meal</h2>

<form method="POST" action='meals' name="meal">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.id}" />" /> <br />
    Date time: <input
        type="datetime-local" name="datetime"
        value="<c:out value="${meal.dateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />

    <input type="submit" value="Save" />
    <input type="submit" href="meals?action=ListMeals" onnclick="document.hisstory.back();" value="Cancel" />
</form>

</body>
</html>
