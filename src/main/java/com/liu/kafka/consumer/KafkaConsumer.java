package com.liu.kafka.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liu.kafka.producer.KafkaProducer;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

/**
 * 消息消费者
 * 
 * 1. 搭建Storm集群 http://blog.csdn.net/ch717828/article/details/50718783 
 * 2. kafka集群环境搭建 http://blog.csdn.net/ch717828/article/details/50748872
 * 3. storm+ kafka集成  http://blog.csdn.net/ch717828/article/details/50748912
 * @author liuzhihong
 * @date 2017年4月13日 上午11:33:11
 */
public class KafkaConsumer {

	private final ConsumerConnector consumer;

	public KafkaConsumer() {
		Properties props = new Properties();

		// zookeeper 配置
		props.put("zookeeper.connect", "10.16.72.63:2181");

		// 消费者所在组
		props.put("group.id", "kafka-group");

		// zk连接超时
		props.put("zookeeper.session.timeout.ms", "10000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "smallest");

		// 序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		ConsumerConfig config = new ConsumerConfig(props);

		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
	}
	
	public void consume(){
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(KafkaProducer.TOPIC, 1);
		
		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
		
		Map<String, List<KafkaStream<String, String>>> consumerMap = 
				consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		
		KafkaStream<String, String> stream = consumerMap.get(KafkaProducer.TOPIC).get(0);
		
		ConsumerIterator<String, String> it = stream.iterator();
		int messageCount = 0;
		
		while(it.hasNext()){
			System.out.println(it.next().message());
			messageCount++;  
            if(messageCount == 100){  
                System.out.println("Consumer端一共消费了" + messageCount + "条消息！");  
            }  
		}
		
	}
}
