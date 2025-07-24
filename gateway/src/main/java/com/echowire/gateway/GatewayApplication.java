package com.echowire.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	ReactiveRedisTemplate<String, Long> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		StringRedisSerializer stringRedisSerializer = StringRedisSerializer.UTF_8;
		GenericToStringSerializer<Long> longToStringSerializer = new GenericToStringSerializer<>(Long.class);
		ReactiveRedisTemplate<String, Long> template = new ReactiveRedisTemplate<>(factory,
				RedisSerializationContext.<String, Long>newSerializationContext(jdkSerializationRedisSerializer)
						.key(stringRedisSerializer).value(longToStringSerializer).build());
		return template;
	}
}
