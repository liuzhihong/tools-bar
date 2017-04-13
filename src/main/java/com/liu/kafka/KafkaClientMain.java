package com.liu.kafka;

import com.liu.kafka.consumer.KafkaConsumer;
import com.liu.kafka.producer.KafkaProducer;

public class KafkaClientMain {

	public static void main(String[] args) {
		KafkaProducer producer = new KafkaProducer();
		producer.produce();
		
		/*KafkaConsumer consumer = new KafkaConsumer();
		consumer.consume();*/
	}
}
