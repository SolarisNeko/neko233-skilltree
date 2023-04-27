package com.neko233.skilltree.commons.core.random;

/**
 * 带有权重的对象包装
 *
 * @param <T> 对象类型
 * @author looly
 */
public class WeightObject<T> {
    /**
     * 权重
     */
    private final double weight;
    /**
     * 对象
     */
    private T obj;

    /**
     * 构造
     *
     * @param obj    对象
     * @param weight 权重
     */
    public WeightObject(T obj, double weight) {
        this.obj = obj;
        this.weight = weight;
    }

    /**
     * 获取对象
     *
     * @return 对象
     */
    public T getObj() {
        return obj;
    }

    /**
     * 设置对象
     *
     * @param obj 对象
     */
    public void setObj(T obj) {
        this.obj = obj;
    }

    /**
     * 获取权重
     *
     * @return 权重
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((obj == null) ? 0 : obj.hashCode());
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WeightObject<?> other = (WeightObject<?>) obj;
        if (this.obj == null) {
            if (other.obj != null) {
                return false;
            }
        } else if (!this.obj.equals(other.obj)) {
            return false;
        }
        return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
    }
}
