package com.example.notification.mail;

import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.notification.event.PartyNotificationEvent;
import com.example.notification.event.RequestStatus;

@Service
public class PartyNotificationEventConsumer {

  @Autowired
  private EmailService emailService;

  @KafkaListener(topics = "${notification-service.topic-name}",
      groupId = "${spring.kafka.consumer.group-id}",
      containerFactory = "notificationKafkaListenerFactory")
  public void consume(PartyNotificationEvent message) throws IOException, MessagingException {
    String subject = "Party Notification";
    String content = getContent(message);
    if (message.getStatus() == RequestStatus.APPROVAL_FAILED) {
      subject = "Party Creation Failed : Approval Denied";
    } else if (message.getStatus() == RequestStatus.SUCCESS) {
      subject = "Party Creation Success";
    }
    emailService.sendEmail(message.getEmail(), subject, content);
  }

  private String getContent(PartyNotificationEvent message) {
    String content = " FirstName : %s \r\n" + "LastName : %s \r\n" + "Address : %s \r\n" + "Email : %s \r\n"
        + "ContactNumber : %s\r\n";

    return String.format(content, message.getFirstName(), message.getLastName(),
        message.getAddress(), message.getEmail(), message.getContactNumber());

  }

}
