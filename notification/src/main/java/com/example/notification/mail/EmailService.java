package com.example.notification.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  public void sendEmail(String emailId, String subject, String content) throws MessagingException, IOException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());
    helper.setTo(emailId);
    helper.setText(content, true);
    helper.setSubject(subject);
    emailSender.send(message);
  }

}
