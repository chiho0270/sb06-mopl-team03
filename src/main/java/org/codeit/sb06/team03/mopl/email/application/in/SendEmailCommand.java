package org.codeit.sb06.team03.mopl.email.application.in;

public record SendEmailCommand(
        String to,
        String rawTempPassword,
        String expireDate
){
}
