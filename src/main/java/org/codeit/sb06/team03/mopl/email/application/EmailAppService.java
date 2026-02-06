package org.codeit.sb06.team03.mopl.email.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailCommand;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailUseCase;
import org.codeit.sb06.team03.mopl.email.domain.EmailDomain;
import org.codeit.sb06.team03.mopl.email.domain.EmailService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmailAppService implements SendEmailUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailService emailService;

    @Override
    @Transactional
    public void send(SendEmailCommand command) {
        final String email = command.email();
        final String rawTempPassword = command.rawTempPassword();
        final String expireDate = command.expireDate();

        EmailDomain emailDomain = emailService.send(email, rawTempPassword, expireDate);
        applicationEventPublisher.publishEvent(emailDomain.event());
        emailDomain.clearEvents();
    }
}