package com.example.party;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.example.party.service.event.AddressValidationResponseEvent;
import com.example.party.service.event.Event;

@Configuration
public class PartyServiceConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootStrapServer;

  @Value("${spring.kafka.consumer.group-id}")
  private String consumerGroupId;

  @Bean
  public DozerBeanMapper mapper() {
    return new DozerBeanMapper();
  }

  @Bean
  public ProducerFactory<String, Event> producerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean
  public KafkaTemplate<String, Event> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public ConsumerFactory<String, AddressValidationResponseEvent> AddressValidationConsumerFactory() {
    
    JsonDeserializer<AddressValidationResponseEvent> deserializer = new JsonDeserializer<>(AddressValidationResponseEvent.class);
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
  public ConcurrentKafkaListenerContainerFactory<String, AddressValidationResponseEvent> AddressValidationKafkaListenerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, AddressValidationResponseEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(AddressValidationConsumerFactory());
    return factory;
  }
}
