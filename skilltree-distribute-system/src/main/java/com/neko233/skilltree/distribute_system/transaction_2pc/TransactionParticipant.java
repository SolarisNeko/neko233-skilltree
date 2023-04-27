package com.neko233.skilltree.distribute_system.transaction_2pc;

interface TransactionParticipant {
    /**
     * 准备
     *
     * @param transaction 事务
     * @return isOk
     */
    boolean prepare(Transaction transaction);

    /**
     * 提交
     *
     * @param transaction 事务
     * @return isOk
     */
    boolean commit(Transaction transaction);

    /**
     * 回滚
     *
     * @param transaction 事务
     * @return isOk
     */
    boolean rollback(Transaction transaction);
}
