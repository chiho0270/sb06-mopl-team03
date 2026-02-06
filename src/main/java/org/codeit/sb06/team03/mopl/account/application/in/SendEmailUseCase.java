package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.account.domain.event.AccountEvent.PasswordResetedEvent;

public interface SendEmailUseCase {
    void handleResetPasswordEvent(PasswordResetedEvent event);
}
