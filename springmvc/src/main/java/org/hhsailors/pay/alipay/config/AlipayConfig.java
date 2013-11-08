package org.hhsailors.pay.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static final String partner = "2088011793587764";
    // 商户的私钥
    public static final String key = "br66brcpma1fsh28aj5yyjx9aek6pvhy";

    //卖家支付宝帐户
    public static final String seller_email = "22578616@qq.com";

    //支付类型
    public static final String payment_type = "1";

    private static final String context = "http://megahh.no-ip.org:9080";

    // 支付宝服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
    // 必须保证其地址能够在互联网中访问的到
    public static final String notify_url = context + "/pay/alipay/notify";

    // 当前页面跳转后的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
    // 域名不能写成http://localhost/create_direct_pay_by_user_jsp_utf8/return_url.jsp ，否则会导致return_url执行无效
    public static final String return_url = context + "/pay/alipay/paymentreturn";

    // 退款通知URL
    public static final String refund_notify_url = context + "/pay/alipay/refund_notify";

    public static final String SESSION_KEY_PAYMENT_PARAM_MAP = "SESSION_KEY_PAYMENT_PARAM_MAP";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static final String input_charset = "utf-8";

    // 签名方式 不需修改
    public static final String sign_type = "MD5";

    // 支付宝的公钥，无需修改该值
    public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    // 商户（RSA）私钥
    public static final String rsa_pkcs8 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL6650O9QT5luZ3xWW6Nr93hjhmgNTb4J3rEnOFfqu/2wt2pJ5rcQHJDif/UAvIos6i1jOfXjY6IeIsFdpLS8Nrb9d4OdBcV/U35oj/RLxTqpdAH42woYeq5pQThamg6+FU9djPu3+RkVDicdFVjvpMGo00HOASz627Unbq9WqOZAgMBAAECgYEApIUuRaUzaTJwKUhtf0pioD8Wxw4FuRPXYjPEqzdNiM5PCssFqbRBNw8BM5bar63iQy3PjJDHv2ICsNCWllG0hWa7Ra6OOPW8cz+thKXWV1w8qYekXDYPE/yI/9t/Rg634uhmnVJMtuLujBeCjadU8FTR9ah2y+ZsBYyHttgHZG0CQQDtJT7a9udmTc71Ohz61dwifrGL2ejNPQSV+UoG8jeWZVUoov7UtwS8fklipD32FyF8q+XjTgQxR1jzqTNwzNjzAkEAzeTweFz8g7iaX4xXAicTszC5nZbX59IBOdn2x/qk6pgOSTHTsd0m5j7PRJ1qv58fJhEPJAnvbF/ujQOQ4MG0QwJAb1dXZZMbHgtWKgj45kRWsNNvJMxQ41vMtWVv/kbnFnPJQeUVZGXxkVZxuiCG/u3pSRGkQ86zZTesaep9Pm7PRQJBAIGxjpAi8qT+aa96NWyTtOMQ9DsCQ6oxLLiGRd8eUTe85HLSwiitx80ND36HMjDWSMavxDvNTlWT8C4/aslI+wUCQG8vdQkMBWrb6ypewjWXIsPKWcfyBtwbalAgnoYu5ELqf4kgFvO3do8NrYfWR8lsRNlLWnJ+UYXXf2+KnuZNTTg=";

}
