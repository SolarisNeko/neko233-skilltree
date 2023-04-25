package com.neko233.toolchain.distribute_system.transaction_2pc;

import java.util.List;

/**
 * 两阶段提交 class
 *
 * @author SolarisNeko
 */
public class TwoPhaseCommit {

    private final List<TransactionParticipant> participants;

    public TwoPhaseCommit(List<TransactionParticipant> participants) {
        this.participants = participants;
    }

    /**
     * 提交
     */
    public boolean commitIn2PC(Transaction transaction) {
        try {
            // 第一阶段：准备阶段
            boolean allPrepared = true;
            for (TransactionParticipant participant : participants) {
                if (!participant.prepare(transaction)) {
                    allPrepared = false;
                    break;
                }
            }

            // 如果有一个参与者未能准备好，通知所有参与者回滚事务
            if (!allPrepared) {
                for (TransactionParticipant participant : participants) {
                    participant.rollback(transaction);
                }
                return false;
            }

            // 第二阶段：提交阶段
            boolean allCommitted = true;
            for (TransactionParticipant participant : participants) {
                if (!participant.commit(transaction)) {
                    allCommitted = false;
                    break;
                }
            }

            // 如果有一个参与者未能提交，通知所有参与者回滚事务
            if (!allCommitted) {
                for (TransactionParticipant participant : participants) {
                    participant.rollback(transaction);
                }
                return false;
            }

            return true;
        } catch (Exception e) {
            // 发生异常，通知所有参与者回滚事务
            for (TransactionParticipant participant : participants) {
                participant.rollback(transaction);
            }
            return false;
        }
    }
}
