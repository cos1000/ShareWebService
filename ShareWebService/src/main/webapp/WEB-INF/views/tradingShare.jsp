<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Trading Share Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>
	Trading Share Detail
</h1>
<c:if test="${!empty records}">
	<table class="tg">
	<tr>
		<th width="80">Share ID</th>
		<th width="120">Transaction Date</th>
		<th width="120">Closing Amount</th>
	</tr>
	<c:forEach items="${records}" var="record">
		<tr>
			<td>${record.share_id}</td>
			<td>${record.transaction_date}</td>
			<td>${record.closing}</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<c:if test="${!empty message}">
    <h3>${message}</h3>
</c:if>
</body>
</html>