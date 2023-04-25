package com.neko233.toolchain.distribute_system.transaction_2pc;

import java.util.ArrayList;
import java.util.List;

class Transaction {

    private List<Operation> operations = new ArrayList<>();
    
    public void addOperation(Operation operation) {
        operations.add(operation);
    }
    
    public List<Operation> getOperations() {
        return operations;
    }

}