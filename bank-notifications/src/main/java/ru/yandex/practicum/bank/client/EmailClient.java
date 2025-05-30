package ru.yandex.practicum.bank.client;

import jakarta.annotation.PostConstruct;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.config.EmailClientConfig;
import ru.yandex.practicum.bank.exception.EmailSendException;
import ru.yandex.practicum.bank.model.EmailNotification;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailClient {

    private final EmailClientConfig emailClientConfig;
    private Session session;

    @PostConstruct
    private void initSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailClientConfig.getHost());
        props.put("mail.smtp.port", String.valueOf(emailClientConfig.getPort()));
        props.put("mail.smtp.ssl.trust", emailClientConfig.getHost());

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailClientConfig.getUsername(),
                        emailClientConfig.getPassword()
                );
            }
        });
    }

    public Mono<EmailNotification> sendMessage(EmailNotification emailNotification) {
        return generateMessage(emailNotification)
                .doOnNext(message -> {
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        throw new EmailSendException(e.getMessage());
                    }
                }).map(m -> emailNotification);
    }

    private Mono<Message> generateMessage(EmailNotification emailNotification) {
        return Mono.just((Message) new MimeMessage(session))
                .map(message -> {
                    try {
                        message.setFrom(new InternetAddress("no-reply@localhost"));

                        message.setRecipients(
                                Message.RecipientType.TO, InternetAddress.parse(emailNotification.getRecipient()));
                        message.setSubject(emailNotification.getSubject());

                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        mimeBodyPart.setContent(emailNotification.getMessage(), "text/html; charset=utf-8");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(mimeBodyPart);

                        message.setContent(multipart);
                        return message;
                    } catch (MessagingException e) {
                        throw new EmailSendException(e.getMessage());
                    }
                });
    }

}
