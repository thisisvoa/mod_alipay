package org.hhsailors.pay;

import org.hhsailors.pay.alipay.config.AlipayConfig;
import org.hhsailors.pay.alipay.util.AlipayNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * payment module
 *
 * @author hh
 */
@Controller
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    /**
     * Display a payment summary, and allows users to choose a payment method.
     */
    @RequestMapping(value = "/pay/")
    public String paying(Model model, HttpSession session) {

        @SuppressWarnings("unchecked")
        Map<String, String> paramMap = (Map<String, String>) session
                .getAttribute(AlipayConfig.SESSION_KEY_PAYMENT_PARAM_MAP);
        if (paramMap == null) {
            model.addAttribute("invalid_param", "invalid_param");
            // return "/please-goback-and-refresh";
        } else {
            String total_fee = paramMap.get("total_fee");
            model.addAttribute("total_fee", total_fee);
        }
        logger.info("allows users to choose a payment method. param:{}", paramMap);
        return "/pay/pay";
    }

    /**
     * relocate to alipay.com
     */
    @RequestMapping(value = "/pay/alipay/")
    public String toalipay(@RequestParam String bankCode, Model model) {

        // if (StringUtils.hasLength(bankCode) && ) {
        // model.addAttribute("WIDpay_method", "bankPay");
        // } else {
        // model.addAttribute("WIDpay_method", "directPay");
        // }
        // new RedirectView("https://mapi.alipay.com/gateway.do?_input_charset=" +
        // AlipayConfig.input_charset);
        return "/pay/toalipay";
    }

    /**
     * payment return
     *
     * @throws Exception
     */
    @RequestMapping(value = "/pay/alipay/paymentreturn")
    public String paymentReturn(HttpServletRequest request, RedirectAttributes ra) throws Exception {

        Map<String, String> params = extractParams(request);

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        String trade_status = request.getParameter("trade_status"); // 交易状态
        String trade_no = request.getParameter("trade_no"); // 支付宝交易号
        String aliCommonParams = request.getParameter("extra_common_param");
        if (aliCommonParams == null) {
            throw new Exception("支付宝同步通信参数为null");
        }
        String[] paramsArray = aliCommonParams.split(",");
        String handler_name = paramsArray[0];

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        if (AlipayNotify.verifyByMD5(params)) { // 验证成功
            logger.info("AlipayNotify.verify success trade_no:{}", trade_no);
            if ("TRADE_SUCCESS".equals(trade_status)) {
                ra.addAttribute("trade_no", trade_no);
                logger.info("redirecting to handler:{}", handler_name);
                return "redirect:" + handler_name;
            } else if ("TRADE_FINISHED".equals(trade_status)) {
                logger.info("trade finished, trade_no:{}", trade_no);
            } else {
                logger.error("alipay did not return TRADE_SUCCESS but {}", trade_status);
            }
        } else {
            logger.info("支付宝同步通信校验失败!\nparams={}", params);
        }
        return "/error";
    }

    /**
     * sign a string with rsa key.
     */
    @RequestMapping(value = "/pay/alipay/rsa_sign")
    @ResponseBody
    public String signByRSA(HttpServletRequest request) {
        String raw = request.getParameter("raw");
        if (raw != null) {
            return AlipayNotify.signByRSA(raw);
        } else {
            logger.info("parameter missing");
            return null;
        }
    }

    /**
     * receives notify from alipay.
     */
    @RequestMapping(value = "/pay/alipay/notify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) {
        String trade_status = request.getParameter("trade_status"); // 交易状态
        String trade_no = request.getParameter("trade_no"); // 支付宝交易号
        String out_trade_no = request.getParameter("out_trade_no"); // 支付宝交易号
        String extra_common_param = request.getParameter("extra_common_param");
//	public String alipayNotify(@RequestParam String trade_status, @RequestParam String trade_no,
//	    @RequestParam String out_trade_no, @RequestParam String extra_common_param, HttpServletRequest request) {

        logger.info(MessageFormat.format("trade_status={0} trade_no={1} out_trade_no={2} extra_common_param={3}", trade_status, trade_no,
                out_trade_no, extra_common_param));
        Map<String, String> params = extractParams(request);

        if (AlipayNotify.verifyByMD5(params)) {// 验证成功
            logger.info("notify trade_status={}", trade_status);
            if ("TRADE_FINISHED".equals(trade_status)) {
                logger.info("trade {} FINISHED", trade_no);
                return "success";
            } else if ("TRADE_SUCCESS".equals(trade_status)) {
                if (extra_common_param == null) {
                    logger.warn("no extra_common_param", new Exception("notified extra_common_param = null"));
                    return "fail";
                }
                try {
                    // 传回的公共参数 格式参考发送时:
                    String[] paramsArray = extra_common_param.split(",");
                    String handler_name = paramsArray[0];
                    // 根据订单作处理
                    NotifyHandler.process(handler_name, out_trade_no);
                } catch (Exception e) {
                    logger.error("严重错误,订单更新失败,可能需要手动退款:trade_no=" + trade_no + "  extra_common_param:" + extra_common_param, e);
                    return "exception";
                }
                logger.info("trade {} SUCCEEDED", trade_no);
                return "success";
            } else {
                logger.error("unknown trade_status({}) for trade:{}", trade_status, trade_no);
                return "fail";
            }
        } else {// 验证失败
            logger.error("严重错误,安全验证失败 params={}", params);
            return "verify sign failed";
        }
    }

    /**
     * receives notify from alipay.
     */
    @RequestMapping(value = "/pay/alipay/m/notify")
    @ResponseBody
    public String AlipayMobileNotify(HttpServletRequest request) {
        String trade_status = request.getParameter("trade_status"); // 交易状态
        String trade_no = request.getParameter("trade_no"); // 支付宝交易号
        String out_trade_no = request.getParameter("out_trade_no"); // 支付宝交易号
        String extra_common_param = request.getParameter("extra_common_param");
//	public String alipayNotify(@RequestParam String trade_status, @RequestParam String trade_no,
//	    @RequestParam String out_trade_no, @RequestParam String extra_common_param, HttpServletRequest request) {

        logger.info(MessageFormat.format("trade_status={0} trade_no={1} out_trade_no={2} extra_common_param={3}", trade_status, trade_no,
                out_trade_no, extra_common_param));
        Map<String, String> params = extractParams(request);

        if (AlipayNotify.verifyByRSA(params)) {// 验证成功
            logger.info("notify trade_status={}", trade_status);
            if ("TRADE_FINISHED".equals(trade_status)) {
                logger.info("trade {} FINISHED", trade_no);
                return "success";
            } else if ("TRADE_SUCCESS".equals(trade_status)) {
                if (extra_common_param == null) {
                    logger.warn("no extra_common_param", new Exception("notified extra_common_param = null"));
                    return "fail";
                }
                try {
                    // 传回的公共参数 格式参考发送时:
                    String[] paramsArray = extra_common_param.split(",");
                    String handler_name = paramsArray[0];
                    // 根据订单作处理
                    NotifyHandler.process(handler_name, out_trade_no);
                } catch (Exception e) {
                    logger.error("严重错误,订单更新失败,可能需要手动退款:trade_no=" + trade_no + "  extra_common_param:" + extra_common_param, e);
                    return "exception";
                }
                logger.info("trade {} SUCCEEDED", trade_no);
                return "success";
            } else {
                logger.error("unknown trade_status({}) for trade:{}", trade_status, trade_no);
                return "fail";
            }
        } else {// 验证失败
            logger.error("严重错误,安全验证失败 params={}", params);
            return "verify sign failed";
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> extractParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
