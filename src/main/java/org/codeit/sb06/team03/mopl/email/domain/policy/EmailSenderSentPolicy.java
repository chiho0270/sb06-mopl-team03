package org.codeit.sb06.team03.mopl.email.domain.policy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mopl.account.email-sender-policy", havingValue = "sent")
public class EmailSenderSentPolicy implements EmailSenderPolicy{

}
