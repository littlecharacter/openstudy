package com.lc.javase.grow.compare;

/**
 * @author gujixian
 * @since 2024/2/2
 */
public interface DataComparison<S, T> {
    boolean compare(S source, T target);
}
