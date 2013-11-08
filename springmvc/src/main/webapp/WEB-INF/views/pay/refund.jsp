<%
/* *
 *功能：即时到账批量退款有密接口接入页
 *版本：3.3
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.hhsailors.pay.alipay.config.*"%>
<%@ page import="org.hhsailors.pay.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.MessageFormat"%>
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="org.slf4j.Logger"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>支付宝即时到账批量退款有密接口</title>
	</head>
	<%
		Logger logger = LoggerFactory.getLogger("refund.jsp");
			////////////////////////////////////请求参数//////////////////////////////////////
			//退款当天日期
			String refund_date =  (String)request.getAttribute("WIDrefund_date");
			//必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13
			//批次号
			String batch_no = (String)request.getAttribute("WIDbatch_no");
			//必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001
			//退款笔数
			String batch_num = (String)request.getAttribute("WIDbatch_num");
			//必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
			//退款详细数据
			String detail_data = (String)request.getAttribute("WIDdetail_data");
			//必填，具体格式请参见接口技术文档

			logger.debug(MessageFormat.format("send refund request with params:batch_no={0}, detail_data={1}, batch_num={2}, refund_date={3}",
				  batch_no, detail_data, batch_num, refund_date));
			//////////////////////////////////////////////////////////////////////////////////

			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
	    sParaTemp.put("partner", AlipayConfig.partner);
	    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
	    sParaTemp.put("notify_url", AlipayConfig.notify_url);
	    sParaTemp.put("seller_email", AlipayConfig.seller_email);
			sParaTemp.put("refund_date", refund_date);
			sParaTemp.put("batch_no", batch_no);
			sParaTemp.put("batch_num", batch_num);
			sParaTemp.put("detail_data", detail_data);

			//建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
