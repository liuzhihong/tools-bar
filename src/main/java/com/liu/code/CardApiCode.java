package com.liu.code;

import java.util.Map;

public class CardApiCode extends BaseApiCode {

	/**券号不存在**/
	public static final String CODE_NOT_EXIST = "1001";
	/**cardId不存在**/
	public static final String CARD_ID_NOT_EXIST = "1002";
	/**领券次数超过限制**/
	public static final String CARD_COUNT_LIMIT = "1003";
	/**优惠券已过期**/
	public static final String CARD_TIME_LIMIT = "1004";
	/**优惠券已被领完**/
	public static final String CARD_AMOUNT_LIMIT = "1005";
	/**优惠券已核销**/
	public static final String CARD_CONSUMED = "1006";
	/**该券已绑定过用户**/
	public static final String CARD_BINDED = "1007";
	/**订单金额不满足最小优惠金额**/
	public static final String CARD_MONEY_NOT_ALLOW = "1008";
	/**当前优惠券已达到使用上限**/
	public static final String CARD_ID_TIMLI = "1009";
	/**当前优惠券已达到单用户使用上限**/
	public static final String CARD_ID_LIMIT_PER_USER = "1010";
	/**当前优惠券没有可赠送的礼品**/
	public static final String CARD_NO_GIFT = "1011";
	/**未知的优惠券类型**/
	public static final String UNKNOW_CARD_TYPE = "1012";
	/**没有选中的礼品**/
	public static final String CARD_GIFT_EMPTY = "1013";
	/**订单金额不满足优惠券类型**/
	public static final String ORDER_MONEY_NOT_SATISFY = "1014";
	/**优惠券活动还未开始**/
	public static final String CARD_TIME_NOT_START = "1015";
	
	
	public static void initResponseCode() {
		Map<String, String> errorMsgMap = BaseApiCode.zhMsgMap;
		errorMsgMap.put(CARD_ID_NOT_EXIST, "cardId不存在");
		errorMsgMap.put(CODE_NOT_EXIST, "券号code不存在");
		errorMsgMap.put(CARD_COUNT_LIMIT, "领券次数超过限制");
		errorMsgMap.put(CARD_TIME_LIMIT, "优惠券已过期");
		errorMsgMap.put(CARD_AMOUNT_LIMIT, "优惠券已被领完");
		errorMsgMap.put(CARD_CONSUMED, "优惠券已核销");
		errorMsgMap.put(CARD_BINDED, "该券已绑定过用户");
		errorMsgMap.put(CARD_MONEY_NOT_ALLOW, "订单金额不满足最小优惠金额");
		errorMsgMap.put(CARD_ID_TIMLI, "当前优惠券已达到使用上限");
		errorMsgMap.put(CARD_ID_LIMIT_PER_USER, "当前优惠券已达到单用户使用上限");
		errorMsgMap.put(CARD_NO_GIFT, "当前优惠券没有可赠送的礼品");
		errorMsgMap.put(UNKNOW_CARD_TYPE, "未知的优惠券类型");
		errorMsgMap.put(CARD_GIFT_EMPTY, "没有选中的礼品");
		errorMsgMap.put(ORDER_MONEY_NOT_SATISFY, "订单金额不满足优惠券类型");
		errorMsgMap.put(CARD_TIME_NOT_START, "优惠券活动还未开始");
	}
}
