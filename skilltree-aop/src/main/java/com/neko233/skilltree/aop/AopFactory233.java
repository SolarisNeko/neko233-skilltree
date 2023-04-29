package com.neko233.skilltree.aop;

import com.neko233.skilltree.aop.proxy.Aop;
import com.neko233.skilltree.aop.proxy.impl.AopByCglib233;
import com.neko233.skilltree.aop.proxy.impl.AopByJdk233;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public interface AopFactory233 {

    Aop JDK = AopByJdk233.singleton;

    Aop CGLIB = AopByCglib233.singleton;


}
