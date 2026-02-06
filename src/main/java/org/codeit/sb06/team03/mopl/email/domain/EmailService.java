package org.codeit.sb06.team03.mopl.email.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.domain.policy.EmailSenderPolicy;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final EmailSenderPolicy emailSenderPolicy;

    public EmailDomain send(String emailAddress, String rawTempPassword, String expireDate) {
        var emailDomain = new EmailDomain(emailSenderPolicy.content(emailAddress, rawTempPassword, expireDate));
        emailDomain.send(emailAddress, rawTempPassword, expireDate);
        return emailDomain;
    }
}
