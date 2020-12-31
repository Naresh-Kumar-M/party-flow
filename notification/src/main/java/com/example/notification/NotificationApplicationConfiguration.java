package com.example.notification;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.example.notification.event.PartyNotificationEvent;

@Configuration
public class NotificationApplicationConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootStrapServer;

  @Value("${spring.kafka.consumer.group-id}")
  private String consumerGroupId;
  
  @Bean
  public ConsumerFactory<String, PartyNotificationEvent> notificationConsumerFactory() {
    JsonDeserializer<PartyNotificationEvent> deserializer = new JsonDeserializer<>(PartyNotificationEvent.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);
    
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
        deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PartyNotificationEvent> notificationKafkaListenerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PartyNotificationEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(notificationConsumerFactory());
    return factory;
  }
}
