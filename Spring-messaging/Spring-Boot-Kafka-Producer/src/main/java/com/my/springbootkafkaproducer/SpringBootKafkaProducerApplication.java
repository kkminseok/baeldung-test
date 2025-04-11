package com.my.springbootkafkaproducer;

import com.my.springbootkafkaproducer.mapper.DynamicMapper;
import com.my.springbootkafkaproducer.model.Bar2;
import com.my.springbootkafkaproducer.model.Foo2;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.backoff.FixedBackOff;

import java.beans.BeanProperty;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class SpringBootKafkaProducerApplication {

	private final ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaProducerApplication.class, args).close();
	}

	@Bean
	public CommonErrorHandler commonErrorHandler(KafkaOperations<String, Object> template) {
		return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));

	}
	@KafkaListener(id = "fooGroup", topics = "topic1")
	public void listen(Foo2 foo) {
		log.info("Receive message from topic {}", foo);
		if(foo.foo().startsWith("fail")) {
			log.error("error message {}", foo.foo());
			throw new RuntimeException("fail");
		}
		exec.execute(() -> log.info("Hit Enter to terminate"));
	}

	@Bean
	public RecordMessageConverter converter() {
		JsonMessageConverter converter = new JsonMessageConverter();
		DynamicMapper typeMapper = new DynamicMapper();
		typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
		typeMapper.addTrustedPackages("com.common");
		converter.setTypeMapper(typeMapper);
		return converter;
	}


	//내부적으로 ${base}.DLT로 보냄.
	@KafkaListener(id = "dltGroup", topics = "topic1-dlt")
	public void dltListen(byte[] in) {
		log.info("Received from DLT: {}", new String(in));
		exec.execute(() -> log.info("Hit Enter to terminate"));
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("topic1", 1, (short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("topic1.DLT", 1, (short) 1);
	}

	@Bean
	public NewTopic foos() {
		return new NewTopic("foos", 1, (short) 1);
	}

	@Bean
	public NewTopic bars() {
		return new NewTopic("bars", 1, (short) 1);
	}

	@Bean
	@Profile("default")
	public ApplicationRunner runner() {
		return args -> {
			System.out.printf("Hit enter to terminate the application...");
			System.in.read();
		};
	}

}
