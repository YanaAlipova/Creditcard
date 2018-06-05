<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="header.jsp"%>
<%@ include file="loginForm.jsp"%>
<br />
<br />
<br />
<br />
<br />
<br />
<b>ps: имеющиеся логины и пароли в системе (демо)</b>
(в качестве логинов использованы цифры - id, пароли - строки)
<table>
	<tr>
		<th>Логин</th>
		<th>Пароль</th>
		<th>Роль</th>
	</tr>
	<tr>
		<td>1</td>
		<td>1112</td>
		<td>админ</td>
	</tr>
	<tr>
		<td>2</td>
		<td>1112</td>
		<td>пользователь (несколько счетов + несколько карт на счет)</td>
	</tr>
	<tr>
		<td>3</td>
		<td>1112</td>
		<td>пользователь (один счет, несколько карт)</td>
	</tr>
	<tr>
		<td>4</td>
		<td>1112</td>
		<td>админ</td>
	</tr>
</table>
<%@ include file="footer.jsp"%>