package com.liu.enums;

/**
 * 
* @ClassName: MqSendType 
* @Description: MQ发送类型
* @author 汪海霖  wanghl15@midea.com.cn
* @date 2015-4-4 上午11:16:13
 */
public enum MqSendType {
	
	TRADE_GATEWAY_RCV("trade-gateway", "gatewayMsgRcvStatus", null),//接入结果回写
	PACKING_SELF_SEND("packing_order", "send", "pack"),//配货单发送安得
	PACKING_3W_SEND("packing_order_3w", "send", "3w"),//3W配货单发送
	TRADE_ACCESS_NOTIFY("trade-event-notify", "tradeAccess", "tAccess"),//订单接入
	TRADE_STATUS_NOTIFY("trade-event-notify", "tradeStatusChange", "tStatus"),//订单状态变更
	TRADE_CONTENT_NOTIFY("trade-event-notify", "tradeContentChange", "tContent"),//订单内容发送变更
	ORDERITEM_STATUS_NOTIFY("trade-event-notify", "orderItemStatusChange","iStatus"),//订单商品状态变更
	REFUND_ACCESS_NOTIFY("trade-event-notify", "refundAccess", "rAccess"),//售后申请单接入
	REFUND_STATUS_NOTIFY("trade-event-notify", "refundStatusChange", "rStatus"),//售后申请单状态变更
	REFUND_CONTENT_NOTIFY("trade-event-notify", "refundContentChange", "rContent"),//申请单内容发送变更
	REFUND_PAY_ACCESS_NOTIFY("trade-event-notify", "refundPayAccess", "rpAccess"),//退款单接入
	REFUND_PAY_STATUS_NOTIFY("trade-event-notify", "refundPayStatusChange", "rpStatus"),//退款单状态变更
	REFUND_PAY_CONTENT_NOTIFY("trade-event-notify", "refundPayContentChange", "rpContent"),//退款单内容发送变更
	SERVICE_FAIL_NOTICE("fault_reporter", "core_notice", "notice"),//异常预警
	JDS_TRADE_TRACES("trade-traces", "jdsTradeTrace", "trade_traces"),//全链路 正向
	IMS_ORDER_CREATE("ims_order", "imsOrderCreate", "imsOrder");//IMS销售单
	
	private String topic;
	
	private String tag;
	
	private String prefix;
	
	private MqSendType(String topic,String tag,String prefix){
		this.topic = topic;
		this.tag = tag;
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
