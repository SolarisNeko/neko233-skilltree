package com.neko233.skilltree.commons.actionChain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
@Slf4j
public class ActionChainTest {


    @Test
    public void testRecursiveRetry() throws Exception {
        log.info("----Demo--------");

        ActionChain.create()
                .success(() -> {
                    System.out.println("ok");

                    throw new RuntimeException("test exception retry");
                })
                .success(() -> {
                    System.out.println("----- handler 2 ---------");
                })
                .exception((e) -> {
                    log.error("error.", e);
                })
                .doFinally(() -> {
                    System.out.println("finally end.");
                })
                .retry(1, 1)
                .closable(null)
                .execute();
    }


}