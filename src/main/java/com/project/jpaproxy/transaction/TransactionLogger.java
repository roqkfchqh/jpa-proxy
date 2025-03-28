package com.project.jpaproxy.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class TransactionLogger {

    public static void logTransactionInfo(String label) {
        log.info("========== {} ==========", label);
        log.info("트랜잭션 활성화 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("현재 트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("현재 스레드 ID: {}", Thread.currentThread().getId());
        log.info("=================================");
    }

    public static void logTransactionLifecycle(String label) {
        log.info("=== 트랜잭션 리스너 등록됨: {} ===", label);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                log.info("커밋됨 → {}", label);
            }

            @Override
            public void afterCompletion(int status) {
                switch (status) {
                    case STATUS_COMMITTED:
                        log.info("커밋 완료 → {}", label);
                        break;
                    case STATUS_ROLLED_BACK:
                        log.info("롤백됨 → {}", label);
                        break;
                    default:
                        log.warn("이건뭐냐({}) → {}", status, label);
                }
            }
        });
    }
}
