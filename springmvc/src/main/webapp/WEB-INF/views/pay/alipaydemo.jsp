<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=utf8" %>
<html>
<head>
	<title>Alipay Demo</title>
</head>
<body>

<P>  Alipay Demo. </P>


for test only, actually these params should be sent by your real biz-logic not jsp pages.

<form action="pay/" method="get" target="_blank">

<div>演示 ,调试用,实际不需要显示这么多</div>

<table>
<tr>
<td>订单号</td><td>${out_trade_no } &lt;-- (一旦跳转支付宝后,同一订单号金额不再能修改)</td>
</tr>
<tr>
<td>订单subject</td><td>${subject } </td>
</tr>
<tr>
<td>订单body</td><td>${body } </td>
</tr>
<tr>
<td>金额</td><td>${total_fee } </td>
</tr>
<tr>
<td>show_url</td><td>${show_url} &lt;-- 订单地址,客户可以点击访问 </td>
</tr>
<tr>
<td>handler_name</td><td>${handler_name } &lt;-- 支付后处理 </td>
</tr>

</table>

<input type="submit"/>
</form>

</body>
</html>
