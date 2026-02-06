package org.codeit.sb06.team03.mopl.email.infra.out;

import org.codeit.sb06.team03.mopl.email.application.out.SendEmailPort;
import org.springframework.stereotype.Component;

@Component
public class SendEmailAdapter implements SendEmailPort {
    @Override
    public void send(String to, String subject, String content) {

    }
}
