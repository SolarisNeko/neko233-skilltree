package com.neko233.skilltree.commons.core.number;

public class NumberRange<T extends Comparable<T>> {

    private final T start;
    private final T end;

    private NumberRange(T start,
                        T end) {
        this.start = start;
        this.end = end;
    }

    public static <T extends Comparable<T>> NumberRange<T> between(T startInclude,
                                                                   T endInclude) {
        return new NumberRange<>(startInclude, endInclude);
    }

    public boolean contains(T number) {
        int isGte = number.compareTo(start);
        int isLte = number.compareTo(end);
        return isGte >= 0 && isLte <= 0;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }
}
