<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>Архивы файлов</title>
</head>
<body>
<div align="center">
    <table border="1">
        <caption style="font-size: larger; font-weight: bold">Список ZIP-архивов</caption>

        <tr>
            <th></th>
            <th>ID</th>
            <th>Имя ZIP-архива</th>
        </tr>

        <c:forEach items="${files}" var="file">
            <tr>
                <td>
                    <input type="checkbox" class="chbox" value="${file.getId()}"/>
                </td>
                <td>
                    <c:out value="${file.getId()}"/>
                </td>
                <td>
                    <a href="/file/${file.getId()}" download="${file.getFile().getName()}">
                        <c:out value="${file.getFile().getName()}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>

    <input type="submit" class="btn" value="Удалить выбранные архивы" onclick="selDel()"/>
    <br/><br/>
    <input type="submit" value="Обновить страницу" onclick="window.location='/';"/>
    <br/><br/>
    <form action="/add_file" enctype="multipart/form-data" method="POST">
        Добавить файл: <input type="file" name="fileD" multiple>
        <input type="submit"/>
    </form>
</div>

<script type="text/javascript">
    function selDel() {
        var chboxes = document.getElementsByClassName('chbox');
        var ids = [];
        for (var i = 0; i < chboxes.length; i++) {
            if (chboxes[i].checked) {
                ids.push(chboxes[i].getAttribute('value'));
            }
        }

        window.location = '/delete/' + ids.toString();
    }
</script>

</body>
</html>
