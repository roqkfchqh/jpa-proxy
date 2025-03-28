package com.project.jpaproxy.transaction;

import com.project.jpaproxy.common.User;
import com.project.jpaproxy.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OtherComponent {

    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inner(String name){
        TransactionLogger.logTransactionInfo("\uD83D\uDD34 component 시작");
        TransactionLogger.logTransactionLifecycle("component 트랜잭션");
        userRepository.save(new User(name));
    }
}
