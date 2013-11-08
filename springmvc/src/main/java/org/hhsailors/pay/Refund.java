package org.hhsailors.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.hhsailors.pay.alipay.util.AlipayNotify;

/**
 * refund module
 *
 * @author hh
 */
@Controller
public class Refund {

	private static final Logger log = LoggerFactory.getLogger(Refund.class);

	@RequestMapping(value = "/pay/alipay/refund")
	public String refund(@RequestParam String trade_no, @RequestParam double total_money,
	    @RequestParam String out_trade_no, @RequestParam String description, HttpServletRequest request) {

		Date now = new Date();
		request.setAttribute("WIDbatch_no", new SimpleDateFormat("yyyyMMdd").format(now) + out_trade_no.replace("_", ""));
		request.setAttribute("WIDrefund_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		request.setAttribute("WIDbatch_num", "1");
		StringBuilder sb = new StringBuilder(trade_no).append("^").append(total_money).append("^").append(description);
		request.setAttribute("WIDdetail_data", sb.toString());

		return "/pay/refund";

	}

//	private static void sendRefundRequest(String trade_no, double total_money, String out_trade_no, String description) {
//		Date now = new Date();
//		// 把请求参数打包成数组
//		Map<String, String> refundParam = new HashMap<String, String>();
//		refundParam.put("seller_email", AlipayConfig.seller_email);
//		refundParam.put("partner", AlipayConfig.partner);
//		// 格式:8位退款日期+ out_trade_no
//		refundParam.put("batch_no", new SimpleDateFormat("yyyyMMdd").format(now) + out_trade_no);
//		refundParam.put("refund_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
//		refundParam.put("batch_num", "1");
//		StringBuilder sb = new StringBuilder(trade_no).append("^").append(total_money).append("^").append(description);
//		refundParam.put("detail_data", sb.toString());
//		// 构造函数，生成请求URL
//		try {
//			String result = AlipaySubmit.refund_fastpay_by_platform_nopwd(refundParam);
//			log.debug(String.format("params:trade_no=%s,total_money=%s,out_trade_no=%s,description=%s", trade_no, total_money,
//			    out_trade_no, description));
//			log.debug("refund_result:" + result);
//		} catch (UnsupportedEncodingException e) {
//			log.error("UnsupportedEncodingException", e);
//		}
//	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay/alipay/refund_notify")
	@ResponseBody
	public String refundNotify(HttpServletRequest request) {

    String batch_no = request.getParameter("batch_no");
    String success_num = request.getParameter("success_num");
    String result_details = request.getParameter("result_details");
//    public String refundNotify(@RequestParam String batch_no, @RequestParam String success_num,
//    		@RequestParam String result_details, HttpServletRequest request) {
    log.info("refunding with params batch_no={}, result_details={} ", batch_no, result_details);
		Map<String, String> params = extractParams(request);

		try {
	    String out_trade_no = batch_no.substring(8); // 去掉前8位日期

	    if (AlipayNotify.verifyByMD5(params)) {// 验证成功
	    	String[] arr = result_details.split("\\$")[0].split("\\^");
	    	String tradeNo = arr[0];
	    	String tradeStatus = arr[2];
	    	if ("SUCCESS".equals(tradeStatus)) {
	    		log.info("支付宝退款成功,交易号:{}", tradeNo);
	    		NotifyHandler.process(NotifyHandler.REFUND, tradeNo);
	    	} else if ("TRADE_HAS_CLOSED".equals(tradeStatus)) {
	    		log.warn("支付宝返回码TRADE_HAS_CLOSED被我们看作退款成功,可能是因为重复退款,交易号:{}", tradeNo);
	    	} else {
	    		log.error("unknown tradeStatus : {}", tradeStatus);
	    	}

	    	return "success";
	    	// ////////////////////////////////////////////////////////////////////////////////////////
	    }
    } catch (Exception e) {
			log.debug("ex in refundNotify", e);
    }
		return "fail";
	}

	private Map<String, String> extractParams(HttpServletRequest request) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}
}
