package org.codeit.sb06.team03.mopl.email.infra.in;

import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.domain.event.AccountEvent;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailCommand;
import org.codeit.sb06.team03.mopl.user.infra.in.UserCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetMapper {

    public SendEmailCommand toCommand(AccountEvent.PasswordResetedEvent event) {
        final String to = event.getEmailAddress();
        final String rawTempPassword = event.getRawTempPassword();
        final String expiresAt = event.getExpiresAt();
        return new SendEmailCommand(to, rawTempPassword, expiresAt);
    }
        public RegisterAccountCommand toCommand(UserCreateRequest request) {
            final String name = request.name();
            final EmailAddress emailAddress = new EmailAddress(request.email());
            final String rawPassword = request.password();
            return new RegisterAccountCommand(name, emailAddress, rawPassword);
        }
}
