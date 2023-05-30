package com.neko233.skilltree.idGenerator;


import com.neko233.skilltree.commons.core.base.StringUtils233;

/**
 * 循环 ID 生成器 <br>
 * [___环状部分___][________________________][__待回收部分____] <br>
 * 环状部分 > 待回收部分 <br>
 * 对比有效范围 = currentId - ${自定义的环形范围}   <br>
 * 仅适用于内存状态. <br>
 */
public class CircularIdGenerator {

    public static final int CIRCULAR_MULTIPLIER = 3;


    private long nextId;
    private final long maxId;
    private final long minId;
    private final long circularLength;


    public CircularIdGenerator(long minId, long maxId, long circularLength) {
        if (minId <= 0 || maxId <= 0 || circularLength <= 0) {
            throw new IllegalArgumentException("you can not give < 0 value to minId, maxId, circularLength");
        }
        if (maxId < minId) {
            throw new IllegalArgumentException(StringUtils233.format("why your minId > maxId ? minId = {}, maxId = {} ", minId, maxId));
        }
        long circularMultiplierLength = circularLength * CIRCULAR_MULTIPLIER;
        if (circularMultiplierLength > (maxId - minId)) {
            throw new IllegalArgumentException("[minId, maxId] is too small. please give them length > " + circularMultiplierLength);
        }
        this.maxId = maxId;
        this.minId = minId;
        this.circularLength = circularLength;

        this.nextId = minId;
    }

    public synchronized long getNextId() {
        long currentId = nextId;

        this.nextId = calculateCircularId();
        return currentId;
    }

    private long calculateCircularId() {
        if (nextId >= maxId) {
            return minId;
        }
        return nextId + 1;
    }

    public boolean isGreaterThan(long currentId, long otherId) {
        if (otherId == currentId) {
            return false;
        }

        // 先比较
        return isCircularGreaterThanOther(currentId, otherId);
    }

    /**
     * 是否在循环的范围内
     *
     * @param currentId 当前Id
     * @param otherId   其他 ID
     * @return currentId > other
     */
    private boolean isCircularGreaterThanOther(long currentId, long otherId) {
        if (otherId > maxId || otherId < minId) {
            return false;
        }

        long tempMinId = currentId - circularLength;
        // 环状内
        if (minId > tempMinId) {
            long circularLength = minId - tempMinId;
            // [max-circularLength, max]
            long maxStart = maxId - circularLength;
            long maxEnd = maxId;
            boolean isInRangeHigh = inInRange(otherId, maxStart, maxEnd);
            if (isInRangeHigh) {
                return true;
            }
            boolean isInRangeLow = inInRange(otherId, minId, currentId);
            if (isInRangeLow) {
                return true;
            }
            return false;
        }

        // 正常范围内
        return inInRange(otherId, tempMinId, currentId);
    }

    private static boolean inInRange(long otherId, long maxStart, long maxEnd) {
        return maxStart <= otherId && otherId <= maxEnd;
    }
}