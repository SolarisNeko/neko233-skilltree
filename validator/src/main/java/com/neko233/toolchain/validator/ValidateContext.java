package com.neko233.toolchain.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateContext {

    private boolean isOk;
    private String reason;
    private Throwable throwable;

    public void consumeException(Consumer<Throwable> consumer) {
        consumer.accept(throwable);
    }

}
