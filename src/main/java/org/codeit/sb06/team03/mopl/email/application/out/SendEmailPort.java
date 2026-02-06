package org.codeit.sb06.team03.mopl.email.application.out;

public interface SendEmailPort{
    // TODO : 이메일 발송 포트
    void send(String to, String subject, String content);
}
