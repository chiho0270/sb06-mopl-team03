package org.codeit.sb06.team03.mopl.account.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class AccountEvent {

    public static final class AccountRegisteredEvent extends AccountEvent {
    }

    @RequiredArgsConstructor
    @Getter // Account의 의존되는 이메일은 거의 종속되는 급이라 판단하였습니다. -> 비밀번호 초기화 이벤트 발행 -> (외부 인프라 In Bound)이메일 전송 -> (어플리케이션)이메일 서비스 -> (도메인)이메일 정책 -> (어플리케이션) 전송해! -> (인프라 Out Bound)이메일 보냄
    public static final class PasswordResetedEvent extends AccountEvent {
        private final String emailAddress;
        private final String rawTempPassword;
        private final String expiresAt;
    }

    @RequiredArgsConstructor
    public static final class RoleUpdatedEvent extends AccountEvent {
        private final Role role;
    }
}
