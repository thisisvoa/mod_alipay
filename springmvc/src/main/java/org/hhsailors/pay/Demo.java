package org.hhsailors.pay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.hhsailors.pay.alipay.config.AlipayConfig;

/**
 * receivers of payment module
 *
 * @author hh
 */
@Controller
public class Demo {

	private static final Logger logger = LoggerFactory.getLogger(Demo.class);


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String demo(Model model,HttpSession session) {

		////////////// 修改这里  /////////////////////////////////////////////
		String out_trade_no = "testorder_00001_6";
		String subject = "subject";
		String body = "description_blahblahblah";
		String total_fee = "0.01";
		String show_url = "http://www.quanhouse.com/?order=123";
		String handler_name = "/pay/demohandler";

		////////////////////////////////////////////////////////////////////

		////////////// 不用修改  /////////////////////////////////////////////
		// use session or cache to keep payment info.
		Map<String, String> paymentParam = new HashMap<String, String>();
		paymentParam.put("out_trade_no", out_trade_no);
		paymentParam.put("subject", subject);
		paymentParam.put("body", body);
		paymentParam.put("total_fee", total_fee);
		paymentParam.put("show_url", show_url);
		paymentParam.put("handler_name", handler_name);
		session.setAttribute(AlipayConfig.SESSION_KEY_PAYMENT_PARAM_MAP, paymentParam);
		model.addAllAttributes(paymentParam);
		////////////// 不用修改  /////////////////////////////////////////////



		return "/pay/alipaydemo";
	}

	/**
	 * payment return
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/demohandler")
	public String handler(Model model, HttpSession session) throws Exception {

		logger.info("/pay/demohandler called ");

		return "/pay/demohandler";
	}





	// 退款
	// test failed via:
	// curl "http://localhost:8090/quanhousework/pay/alipay/refund?trade_no=2013100259955699&total_money=0.01&out_trade_no=testorder_00001_6&description=description_blahblahblah"





}
