package cn.birdstudio.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import cn.birdstudio.jms.TransactionMessage;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	@Bean
	public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(
			ProducerFactory<String, Map<String, Object>> producerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, TransactionMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.getContainerProperties().setPollTimeout(3000);
		factory.getContainerProperties().setTransactionManager(new KafkaTransactionManager<>(producerFactory));
		return factory;
	}

	@Bean
	public ConsumerFactory<String, TransactionMessage> consumerFactory() {
		JsonDeserializer<TransactionMessage> jd = new JsonDeserializer<>(TransactionMessage.class);
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), jd);
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.1.168:9092");
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return propsMap;
	}
}
