package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountUseCase;
import org.codeit.sb06.team03.mopl.account.application.in.ResetPasswordCommand;
import org.codeit.sb06.team03.mopl.account.application.in.ResetPasswordUseCase;
import org.codeit.sb06.team03.mopl.account.application.out.CreateUserPort;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.account.application.out.SaveAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.AccountService;
import org.codeit.sb06.team03.mopl.account.domain.exception.AccountRegistrationFailedException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressAlreadyExistsException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressNotFoundException;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountAppService implements RegisterAccountUseCase, ResetPasswordUseCase {

    private final AccountService accountService;
    private final LoadAccountPort loadAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveAccountPort saveAccountPort;

    @Override
    @Transactional
    public Account register(RegisterAccountCommand command) {
        final String name = command.name();
        final EmailAddress emailAddress = command.emailAddress();
        final String rawPassword = command.rawPassword();

        if (loadAccountPort.existsByEmailAddress(emailAddress)) {
            throw new EmailAddressAlreadyExistsException(emailAddress.value());
        }
        Account newAccount = accountService.create(emailAddress, rawPassword);
        createUserPort.create(name)
                .exceptionally(throwable -> {
                    throw new AccountRegistrationFailedException(throwable);
                })
                .join();

        saveAccountPort.save(newAccount);
        return newAccount;
    }

    @Override
    @Transactional
    public Account resetPassword(ResetPasswordCommand command) {
        final EmailAddress emailAddress = command.emailAddress();

        if (!loadAccountPort.existsByEmailAddress(emailAddress)) {
            throw new EmailAddressNotFoundException(emailAddress);
        }
        Account existAccount = loadAccountPort.findByEmailAddress(emailAddress);

        Account resetPasswordAccount = accountService.resetPassword(existAccount);
        // TODO : 이벤트 등록 ResetPasswordEvent

        saveAccountPort.save(resetPasswordAccount); // TODO : 이벤트 발행
        return resetPasswordAccount;
    }
}
