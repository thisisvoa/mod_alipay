<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=utf8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
  收银台
</h1>


<h2 style="height: 25px; line-height: 25px">
<span style="font:bold 16px 微软雅黑">应付总额:<span style="font-size:16px; margin-left: 0px">
${total_fee}</span>RMB</span>
</h2>

<P> plz choose pay method (netbank / alipay)        ${invalid_param}</P>

<c:if test="${empty invalid_param}">
<form action="alipay/" method="get">
 <div id="choosePayTypeDiv">

        <div class="bank_info">
          <label class="bank_text">第三方支付：</label>

        <ul class="bank_list">
          <li>
                <label class="icon-box" for="bank21" >
                <span title="支付宝" class="bank_bg alipay">支付宝</span>
                </label>
                <input type="radio" id="bank0" name="bankCode" value="" />
            </li>
        </ul>
        </div>
        <div class="bank_info" style="padding:0 0 15px 0">
        <label class="bank_text">网银：</label>
        <ul class="bank_list">
          <li >
                <label class="icon-box" for="bank1" >
                <span title="中国工商银行" class="bank_bg ICBC">中国工商银行</span>
                </label>
              <input type="radio" id="bank1" name="bankCode" value="ICBCB2C" />
            </li>
          <li>
                 <label class="icon-box" for="bank2" >
                 <span title="招商银行" class="bank_bg CMB">招商银行</span>
                </label>
              <input type="radio" id="bank2" name="bankCode" value="CMB" />
            </li>
            <li>
                <label class="icon-box" for="bank3" >
                <span title="建设银行" class="bank_bg CCB">建设银行</span>
                </label>
              <input type="radio" id="bank3" name="bankCode" value="CCB" />
            </li>
            <li>
                <label class="icon-box" for="bank4" >
                <span title="中国银行" class="bank_bg BOC">中国银行</span>
                </label>
              <input type="radio" id="bank4" name="bankCode" value="BOCB2C" />
            </li>
            <li>
                <label class="icon-box" for="bank5" >
                <span title="中国农业银行" class="bank_bg ABC">中国农业银行</span>
                </label>
              <input type="radio" id="bank5" name="bankCode" value="ABC" />
            </li>
            <li>
                <label class="icon-box" for="bank6" >
                <span title="交通银行" class="bank_bg COMM">交通银行</span>
                </label>
              <input type="radio" id="bank6" name="bankCode" value="COMM" />
            </li>

             <li>
                <label class="icon-box" for="bank7" >
                <span title="浦发银行" class="bank_bg SPDB">浦发银行</span>
                </label>
              <input type="radio" id="bank7" name="bankCode" value="SPDB" />
            </li>
            <li>
                <label class="icon-box" for="bank8" >
                <span title="广发银行" class="bank_bg GDB">广发银行</span>
                </label>
                <input type="radio" id="bank8" name="bankCode" value="GDB" />
            </li>
            <li>
                <label class="icon-box" for="bank9" >
                <span title="中信银行" class="bank_bg CITIC">中信银行</span>
                </label>
              <input type="radio" id="bank9" name="bankCode" value="CITIC" />
            </li>
            <li>
              <label class="icon-box" for="bank10" >
                <span title="中国光大银行" class="bank_bg CEB">中国光大银行</span>
                </label>
              <input type="radio" id="bank10" name="bankCode" value="CEBBANK" />
            </li>
            <li>
              <label class="icon-box" for="bank11" >
                <span title="兴业银行" class="bank_bg CIB">兴业银行</span>
                </label>
              <input type="radio" id="bank11" name="bankCode" value="CIB" />
            </li>

            <li>
              <label class="icon-box" for="bank13" >
                <span title="中国民生银行" class="bank_bg CMBC">中国民生银行</span>
                </label>
              <input type="radio" id="bank13" name="bankCode" value="CMBC" />
            </li>
            <li>
              <label class="icon-box" for="bank14" >
                <span title="杭州银行" class="bank_bg HZCB">杭州银行</span>
                </label>
              <input type="radio" id="bank14" name="bankCode" value="HZCBB2C" />
            </li>
            <li>
              <label class="icon-box" for="bank15" >
                <span title="上海银行" class="bank_bg SHBANK">上海银行</span>
                </label>
              <input type="radio" id="bank15" name="bankCode" value="SHBANK" />
            </li>
            <li>
              <label class="icon-box" for="bank16" >
                <span title="宁波银行" class="bank_bg NBBANK">宁波银行</span>
                </label>
              <input type="radio" id="bank16" name="bankCode" value="NBBANK" />
            </li>

            <li>
              <label class="icon-box" for="bank17" >
                <span title="平安银行" class="bank_bg SPABANK">平安银行</span>
                </label>
              <input type="radio" id="bank17" name="bankCode" value="SPABANK" />
            </li>
            <li>
              <label class="icon-box" for="bank18" >
                <span title="北京农村商业银行" class="bank_bg BJRCB">北京农村商业银行</span>
                </label>
              <input type="radio" id="bank18" name="bankCode" value="BJRCB" />
            </li>
            <li>
              <label class="icon-box" for="bank19" >
                <span title="富滇银行" class="bank_bg FDB">富滇银行</span>
                </label>
              <input type="radio" id="bank19" name="bankCode" value="FDB" />
            </li>
            <li>
              <label class="icon-box" for="bank20" >
                <span title="中国邮局储蓄银行" class="bank_bg PSBC">中国邮局储蓄银行</span>
                </label>
              <input type="radio" id="bank20" name="bankCode" value="PSBC-DEBIT" />
            </li>

        </ul>
        </div>
    </div>
  </div>


<input type="submit"/>
</form>
</c:if>
<c:if test="${!empty invalid_param}">
请返回并刷新上一页,重新付款
</c:if>
</body>
</html>
