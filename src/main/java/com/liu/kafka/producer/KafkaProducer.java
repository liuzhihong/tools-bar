package com.liu.kafka.producer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
/**
 * kafka安装版本  kafka_2.10-0.8.2.2
 * 消息生产者
 * @author liuzhihong
 * @date 2017年4月13日  上午11:33:02
 */
public class KafkaProducer {

	public final static String TOPIC = "liu-topic-one";
	
	private Producer<String, String> producer;
	
	public KafkaProducer() {
		Properties properties = new Properties();
		 // 此处配置的是kafka的broker地址:端口列表  
		properties.put("metadata.broker.list", "10.16.72.57:9092");  
  
        //配置value的序列化类  
		properties.put("serializer.class", "kafka.serializer.StringEncoder");  
          
        //配置key的序列化类  
		properties.put("key.serializer.class", "kafka.serializer.StringEncoder");  
  
        //request.required.acks  
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).  
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).  
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.  
		properties.put("request.required.acks","1");  
		
		producer = new Producer<String, String>(new ProducerConfig(properties)); 
	}
	
	public void produce(){
		int messageNo = 1;
		final int COUNT = 100;
		int messageCount = 0;
		while(messageNo <= COUNT){
			String key = String.valueOf(messageNo);
			String data = "Hello , Kafka Message "+messageNo;
			producer.send(new KeyedMessage<String, String>(TOPIC, key, data));
			System.out.println(data);
			messageNo ++;
			messageCount++;
		}
		System.out.println("Priducer端一共产生了"+messageCount+"条消息!");
		producer.close();
	}
}
