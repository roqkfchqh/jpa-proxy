package com.project.jpaproxy.transaction;

import com.project.jpaproxy.common.User;
import com.project.jpaproxy.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final OtherComponent otherComponent;

    public void updateUser(Long id, String name){
        update(id, name);
    }

    public void update(Long id, String name){
        User user = userRepository.findById(id).orElseThrow();
        user.updateName(name);
    }

    @Transactional
    public void outer(String name){
        TransactionLogger.logTransactionInfo("\uD83D\uDFE2 outer 시작");
        TransactionLogger.logTransactionLifecycle("outer 트랜잭션");
        try {
            for(int i = 0; i < 10; i++){
                otherComponent.inner(name + i);
                inner(name + i);
                if(i == 5) throw new RuntimeException("exception");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
//        for(int i = 0; i < 10; i++){
//            otherComponent.inner(name + i);
//            inner(name + i);
//            if(i == 5) throw new RuntimeException("exception");
//        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void inner(String name){
        TransactionLogger.logTransactionInfo("\uD83D\uDD35 inner 시작");
        TransactionLogger.logTransactionLifecycle("inner 트랜잭션");
        userRepository.save(new User(name));
    }
}
