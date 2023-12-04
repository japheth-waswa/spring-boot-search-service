package com.japethwaswa.springelasticsearch.redis.config;

import com.japethwaswa.springelasticsearch.ElasticSearchApplication;
import com.japethwaswa.springelasticsearch.redis.subscriber.ProcessedFileSubscriber;
import com.japethwaswa.springelasticsearch.service.FileService;
import com.japethwaswa.springelasticsearch.utils.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisLettuceConfiguration {

    @Autowired
    private FileService fileService;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(ElasticSearchApplication.dotenv != null ? ElasticSearchApplication.dotenv.get("REDIS_HOST") : "127.0.0.1");
        configuration.setPassword(ElasticSearchApplication.dotenv != null ? ElasticSearchApplication.dotenv.get("REDIS_PASSWORD") : "");
        configuration.setPort(ElasticSearchApplication.dotenv != null ? (ElasticSearchApplication.dotenv.get("REDIS_PORT") != null ? Integer.parseInt(ElasticSearchApplication.dotenv.get("REDIS_PORT")) : 6379) : 6379);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }

    //init listeners
    @Bean
    RedisMessageListenerContainer container() {
        final RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(lettuceConnectionFactory());
        redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(new ProcessedFileSubscriber(redisTemplate(), fileService)), new ChannelTopic(HelperUtil.FILE_SUBSCRIBER_TOPIC));
        return redisMessageListenerContainer;
    }
    //end listeners
}
