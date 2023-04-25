package cn.hutool;


import com.neko233.toolchain.aop.Aop233;
import com.neko233.toolchain.aop.AopApi;
import com.neko233.toolchain.commons.core.base.StringUtils233;

/**
 * @author SolarisNeko
 * Date on 2023-04-22
 */
public class AllDependencyTest {

    public static boolean testCommon(String name) {
        return StringUtils233.isBlank(name);
    }

    public static <T> T testAop(T target, AopApi aopApi) {
        return Aop233.proxy(target, aopApi);
    }

}
