package com.neko233.toolchain.commons.core.base;




import com.neko233.toolchain.commons.core.annotation.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;

/**
 * 时间消耗工具
 *
 * @author SolarisNeko
 * Date on 2023-01-29
 */
public class TimeCostUtils233 {

    @NotNull
    public static long executeFunctionSpendMs(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    /**
     * 执行函数使用多久时间 (ms) ?
     *
     * @param consumerFunction 使用 data 作为参数的函数
     * @param data             data
     * @param <D>              Any
     * @return spend millisSecond
     */
    @NotNull
    public static <D> long executeFunctionSpendMs(Consumer<D> consumerFunction, D data) {
        long start = System.currentTimeMillis();
        consumerFunction.accept(data);
        return System.currentTimeMillis() - start;
    }


    public static <D> BigDecimal executeFunctionSpendMsPerCount(Consumer<D> consumerFunction, D data, Number count) {
        if (count == null || count.longValue() == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal allCost = new BigDecimal(String.valueOf(executeFunctionSpendMs(consumerFunction, data)));
        BigDecimal countBd = new BigDecimal(String.valueOf(count));
        return allCost.divide(countBd, 2, RoundingMode.HALF_UP);
    }

}
