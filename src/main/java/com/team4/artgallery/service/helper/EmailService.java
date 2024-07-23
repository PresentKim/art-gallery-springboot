package com.team4.artgallery.service.helper;

import com.team4.artgallery.dto.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SessionProvider sessionProvider;

    private final String from;

    public EmailService(
            @Value("${spring.mail.username}") String from,
            JavaMailSender javaMailSender,
            SessionProvider sessionProvider
    ) {
        this.from = from;
        this.javaMailSender = javaMailSender;
        this.sessionProvider = sessionProvider;
    }

    public void sendMail(EmailMessage emailMessage) throws MessagingException {
        String authCode = createCode();

        // 전송될 이메일 내용 설정
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(emailMessage.to());
        mimeMessageHelper.setSubject(emailMessage.subject());
        mimeMessageHelper.setText(emailMessage.message() + authCode); // 메일 내용

        // 메일 전송
        javaMailSender.send(mimeMessage);

        // 세션에 인증번호 저장
        sessionProvider.getSession().setAttribute("EmailAuth-" + emailMessage.to(), authCode);
    }

    public void checkAuthCode(String email, String authCode) {
        String sessionAuthCode = (String) sessionProvider.getSession().getAttribute("EmailAuth-" + email);
        if (!authCode.equals(sessionAuthCode)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
    }

    /**
     * 인증번호 및 임시 비밀번호 생성 메서드
     *
     * @implNote HEX 코드 8자리 문자열을 생성하여 반환
     */
    public String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            key.append(Integer.toHexString(random.nextInt(16)));
        }

        return key.toString();
    }

}