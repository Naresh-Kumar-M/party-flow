package com.example.party.service.notification;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.party.repository.entity.PartyRequestEntity;
import com.example.party.service.event.Event;
import com.example.party.service.event.PartyNotificationEvent;

@Service
public class PartyNotificationEventProducer {

  private final String notificationTopicName;
  private final KafkaTemplate<String, Event> template;
  private final DozerBeanMapper mapper;

  public PartyNotificationEventProducer(final KafkaTemplate<String, Event> template,
      @Value("${notification-service.topic-name}") String notificationTopicName,
      final DozerBeanMapper mapper) {
    this.template = template;
    this.notificationTopicName = notificationTopicName;
    this.mapper = mapper;
  }

  public void generateNotificationEvent(PartyRequestEntity request) {
    PartyNotificationEvent notification = mapper.map(request, PartyNotificationEvent.class);
    template.send(notificationTopicName, notification);
  }
}
