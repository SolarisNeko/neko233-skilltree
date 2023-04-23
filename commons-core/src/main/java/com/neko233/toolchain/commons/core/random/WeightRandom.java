package com.neko233.toolchain.commons.core.random;


import com.neko233.toolchain.commons.core.base.MapUtils233;
import com.neko233.toolchain.commons.core.base.RandomUtils233;

import java.io.Serializable;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 权重随机算法实现<br>
 * <p>
 * 平时，经常会遇到权重随机算法，从不同权重的N个元素中随机选择一个，并使得总体选择结果是按照权重分布的。<br>
 * 如: 广告投放、负载均衡、抽卡、等。
 * </p>
 * <p>
 * 如有4个元素A、B、C、D，权重分别为1、2、3、4，随机结果中A:B:C:D的比例要为1:2:3:4。<br>
 * </p>
 * 总体思路：累加每个元素的权重A(1)-B(3)-C(6)-D(10)，则4个元素的的权重管辖区间分别为[0,1)、[1,3)、[3,6)、[6,10)。<br>
 * 然后随机出一个[0,10)之间的随机数。落在哪个区间，则该区间之后的元素即为按权重命中的元素。<br>
 */
public class WeightRandom<T> implements Serializable {

    private static final long serialVersionUID = -8244697995702786499L;

    private final TreeMap<Double, T> weightMap;


    /**
     * 构造
     */
    public WeightRandom() {
        weightMap = new TreeMap<>();

    }

    // ---------------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     *
     * @param weightObject 带有权重的对象
     */
    public WeightRandom(WeightObject<T> weightObject) {
        this();
        if (null != weightObject) {
            add(weightObject);
        }
    }

    /**
     * 构造
     *
     * @param weightObjs 带有权重的对象
     */
    public WeightRandom(Iterable<WeightObject<T>> weightObjs) {
        this();
        if (weightObjs == null) {
            return;
        }
        for (WeightObject<T> weightObject : weightObjs) {
            add(weightObject);
        }

    }

    /**
     * 构造
     *
     * @param weightObjects 带有权重的对象
     */
    public WeightRandom(WeightObject<T>[] weightObjects) {
        this();
        for (WeightObject<T> weightObject : weightObjects) {
            add(weightObject);
        }
    }

    /**
     * 创建权重随机获取器
     *
     * @param <T> 权重随机获取的对象类型
     * @return {@link WeightRandom}
     */
    public static <T> WeightRandom<T> create() {
        return new WeightRandom<>();
    }
    // ---------------------------------------------------------------------------------- Constructor end

    /**
     * 增加对象
     *
     * @param obj    对象
     * @param weight 权重
     * @return this
     */
    public WeightRandom<T> add(T obj,
                               double weight) {
        return add(new WeightObject<>(obj, weight));
    }

    /**
     * 增加对象权重
     *
     * @param weightObject 权重对象
     * @return this
     */
    public WeightRandom<T> add(WeightObject<T> weightObject) {
        if (null != weightObject) {
            final double weight = weightObject.getWeight();
            if (weightObject.getWeight() > 0) {
                double lastWeight = (this.weightMap.size() == 0) ? 0 : this.weightMap.lastKey();
                this.weightMap.put(weight + lastWeight, weightObject.getObj());// 权重累加
            }
        }
        return this;
    }

    /**
     * 清空权重表
     *
     * @return this
     */
    public WeightRandom<T> clear() {
        if (null != this.weightMap) {
            this.weightMap.clear();
        }
        return this;
    }

    /**
     * 下一个随机对象
     *
     * @return 随机对象
     */
    public T next() {
        if (MapUtils233.isEmpty(this.weightMap)) {
            return null;
        }
        final Random random = RandomUtils233.getRandom();
        final double randomWeight = this.weightMap.lastKey() * random.nextDouble();
        final SortedMap<Double, T> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }

}