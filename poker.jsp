<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poker.PokerModel" %>

<%
PokerModel model = (PokerModel)request.getAttribute("model");
String label = model.getButtonLabel();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Poker</title>
</head>
<body>
ポーカーゲーム
<hr>
ゲーム回数：<%=model.getGames() %>
<br>
チップ：<%=model.getChips() %>
<hr>
<%= model.getMessage() %>
<form action="/s1932069/PokerServlet" method="POST">
<table>
<tr>
<td><img src="cards/<%= model.getHandcardAt(0) %>.png" width="100" height="150"></td>
<td><img src="cards/<%= model.getHandcardAt(1) %>.png" width="100" height="150"></td>
<td><img src="cards/<%= model.getHandcardAt(2) %>.png" width="100" height="150"></td>
<td><img src="cards/<%= model.getHandcardAt(3) %>.png" width="100" height="150"></td>
<td><img src="cards/<%= model.getHandcardAt(4) %>.png" width="100" height="150"></td>
</tr>
<tr align="center">
<td><input type="checkbox" name="change" value="0"></td>
<td><input type="checkbox" name="change" value="1"></td>
<td><input type="checkbox" name="change" value="2"></td>
<td><input type="checkbox" name="change" value="3"></td>
<td><input type="checkbox" name="change" value="4"></td>
</tr>
</table>
<input type="submit" value="<%= label %>">
</form>
<hr>
<a href="/s1932069/PokerServlet">リセット</a>
</body>
</html>