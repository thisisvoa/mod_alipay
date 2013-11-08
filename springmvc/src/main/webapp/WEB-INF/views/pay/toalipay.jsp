<%
/* *
 *功能：纯网关接口接入页
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *************************注意*****************
 *如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
 *1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
 *2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
 *3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
 *如果不想使用扩展功能请把扩展功能参数赋空值。
 **********************************************
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="org.slf4j.Logger"%>
<%@ page import="org.hhsailors.pay.alipay.config.*"%>
<%@ page import="org.hhsailors.pay.alipay.util.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>支付宝纯网关接口</title>
	</head>
	<%
		////////////////////////////////////请求参数//////////////////////////////////////

		    Logger log = LoggerFactory.getLogger("toalipay.jsp");


// 		//支付类型
// 		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		// 		String notify_url = "http://www.xxx.com/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp";
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		//页面跳转同步通知页面路径
		// 		String return_url = "http://www.xxx.com/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
// 		//卖家支付宝帐户
// 		String seller_email = "22578616@qq.com";
		//new String(request.getParameter("WIDseller_email");
		//必填
		// 		//商户订单号
		// 		String out_trade_no = request.getParameter("WIDout_trade_no");
		// 		//商户网站订单系统中唯一订单号，必填
		// 		//订单名称
		// 		String subject = request.getParameter("WIDsubject");
		// 		//必填
		// 		//付款金额
		// 		String total_fee = request.getParameter("WIDtotal_fee");
		//必填
		//订单描述// 		String body = request.getParameter("WIDbody");

		//默认支付方式"bankPay";
			 			String paymethod = request.getParameter("WIDpay_method");
		//必填
		//默认网银
		String defaultbank = request.getParameter("bankCode");
		//必填，银行简码请参考接口技术文档
		// 		//商品展示地址
		// 		String show_url = request.getParameter("WIDshow_url");
		//需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数
		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1


		//////////////////////////////////////////////////////////////////////////////////
		//把请求参数打包成数组
		Map<String, String> paramMap = (Map<String, String>) session.getAttribute(AlipayConfig.SESSION_KEY_PAYMENT_PARAM_MAP);
		log.info("paramMap={}", paramMap);
		if (paramMap == null) {
		  out.println("超时,请关闭页面重新付款.");
		  return;
		}
		session.removeAttribute(AlipayConfig.SESSION_KEY_PAYMENT_PARAM_MAP);

		//= new HashMap<String, String>();
		paramMap.put("service", "create_direct_pay_by_user");
    paramMap.put("partner", AlipayConfig.partner);
    paramMap.put("_input_charset", AlipayConfig.input_charset);
		paramMap.put("payment_type", AlipayConfig.payment_type);
		paramMap.put("notify_url", AlipayConfig.notify_url);
		paramMap.put("return_url", AlipayConfig.return_url);
		paramMap.put("seller_email", AlipayConfig.seller_email);

		// 		paramMap.put("out_trade_no", out_trade_no);
		// 		paramMap.put("subject", subject);
		// 		paramMap.put("total_fee", total_fee);
		// 		paramMap.put("body", body);
		log.info("paymethod={} defaultbank={}", paymethod, defaultbank);
		    if(defaultbank != null && !defaultbank.isEmpty()) {
		      paramMap.put("paymethod", paymethod);
		      paramMap.put("defaultbank", defaultbank);
		    }
		// 		paramMap.put("show_url", show_url);
		paramMap.put("anti_phishing_key", anti_phishing_key);
		paramMap.put("exter_invoke_ip", exter_invoke_ip);
		String handler = paramMap.remove("handler_name");
		if(handler == null) {
			out.println("参数handler_name错误");
			return;
		}
		StringBuilder extra_param = new StringBuilder();
		extra_param.append(handler);
		// extra_param.append(",");
		// extra_param.append("other_param");
		paramMap.put("extra_common_param", extra_param.toString());

		log.info("final paramMap = {} ", paramMap);

		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(paramMap,"get","ok");

			// 			log.debug("sHtmlText = \n{}\n",sHtmlText);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
