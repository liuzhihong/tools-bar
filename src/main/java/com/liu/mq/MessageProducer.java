package com.liu.mq;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.liu.enums.MqSendType;
import com.liu.utils.JacksonMapper;

/**
 * 
* @ClassName: MessageProducer 
* @Description: MQ消息生成类
* @author 汪海霖  wanghl15@midea.com.cn
* @date 2015-4-29 上午8:44:55
 */

@Component(value="messageProducer")
public class MessageProducer {
	
	private static Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	@Autowired
	private DefaultMQProducer defaultMQProducer;
	
	@PostConstruct
	public void initMq(){
		try {
			defaultMQProducer.start();
		} catch (MQClientException e) {
		}
	}
	
	public boolean sendMessage(MqSendType mqType,String key,String body){
		if(StringUtils.isNotBlank(mqType.getPrefix())){
			key = mqType.getPrefix().concat(key);
		}
        try{
        	Long startTime = System.currentTimeMillis();
            Message msg = new Message(mqType.getTopic(), //topic
            		mqType.getTag(), //tag
            		key, //key
            		body.getBytes()); //body
            SendResult sendResult = defaultMQProducer.send(msg);
            if(System.currentTimeMillis() - startTime > 100){
            	StringBuilder strBuff = new StringBuilder("mq消息发送耗时过长;topic==");
            	strBuff.append(mqType.getTopic()).append(";tag==").append(mqType.getTag()).append(";key==")
            	       .append(key).append(";总耗时==").append(System.currentTimeMillis() - startTime);
            	logger.warn(strBuff.toString());
            }
            return sendResult.getSendStatus() == SendStatus.SEND_OK;
        } catch (Exception ex){
        	StringBuilder strBuff = new StringBuilder("mq消息发送异常;mqType==");
        	strBuff.append(JacksonMapper.beanToJson(mqType)).append(";body==")
        		   .append(body).append(";error_msg==").append(ex.getMessage());
        	logger.error(strBuff.toString());
        	return false;
        }
	}
}
