package org.hhsailors.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifyHandler {

	private static final Logger logger = LoggerFactory.getLogger(NotifyHandler.class);
	public static final String REFUND = "refund";

	/**
	 * 用于异步通知,作后处理用
	 * @param handlerName
	 * @param outTradeNo
	 *          付款页面传入的参数 out_trade_no
	 * @return 处理成功与否
	 */
	public static boolean process(String handlerName, String outTradeNo) {
		logger.info("processing handler ={}, outTradeNo{}", handlerName, outTradeNo);
		// 简单处理了,用if-else代替接口,省得 new 一大堆handler实现了
		if ("/pay/demohandler".equals(handlerName)) {
			return demoHandler(outTradeNo);
//		} else if ("some_handler".equals(handlerName)) { // 加入你的handler
			// your_own_handler(outTradeNo);
	// } else if ("some_handler".equals(handlerName)) { // 加入你的handler
	// } else if ("some_handler".equals(handlerName)) { // 加入你的handler

		} else if ("refund".equals(handlerName)) { // 退款handler,不要删除
			return refundHandler(outTradeNo);
		} else {
			logger.error("unknowen handlerName:{}", handlerName);
			return false;
		}
	}

	/**
	 * 退款后处理
	 * @param outTradeNo
	 * @return
	 */
	private static boolean refundHandler(String outTradeNo) {
	  // TODO Auto-generated method stub
		throw new RuntimeException("not impl yet");
//	  return false;
  }

	/**
	 * 后处理示例
	 */
	private static boolean demoHandler(String outTradeNo) {
		// 这里写逻辑,更新数据等
		// DemoService.updateDb();
		logger.info("demoHandler called ...");
		return false;
	}
}
