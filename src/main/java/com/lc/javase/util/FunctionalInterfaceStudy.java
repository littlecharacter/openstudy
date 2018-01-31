package com.lc.javase.util;

public class FunctionalInterfaceStudy {
    //java8之前
    Runnable runnable;
    java.util.concurrent.Callable callable;
    java.security.PrivilegedAction privilegedAction;
    java.util.Comparator comparator;
    java.io.FileFilter fileFilter;
    java.nio.file.PathMatcher pathMatcher;
    java.lang.reflect.InvocationHandler invocationHandler;
    java.beans.PropertyChangeListener propertyChangeListener;
    java.awt.event.ActionListener actionListener;
    javax.swing.event.ChangeListener changeListener;
    //java8之后
    //通用
    java.util.function.Function function;
    java.util.function.IntFunction intFunction;
    java.util.function.LongFunction longFunction;
    java.util.function.DoubleFunction doubleFunction;
    java.util.function.ToIntFunction toIntFunction;
    java.util.function.ToLongFunction toLongFunction;
    java.util.function.ToDoubleFunction toDoubleFunction;
    java.util.function.IntToLongFunction intToLongFunction;
    java.util.function.IntToDoubleFunction intToDoubleFunction;
    java.util.function.LongToIntFunction longToIntFunction;
    java.util.function.LongToDoubleFunction longToDoubleFunction;
    java.util.function.DoubleToIntFunction doubleToIntFunction;
    java.util.function.DoubleToLongFunction doubleToLongFunction;
    java.util.function.BiFunction biFunction;
    java.util.function.ToIntBiFunction toIntBiFunction;
    java.util.function.ToLongBiFunction toLongBiFunction;
    java.util.function.ToDoubleBiFunction toDoubleBiFunction;
    //断言
    java.util.function.Predicate<?> predicate;
    java.util.function.IntPredicate intPredicate;
    java.util.function.LongPredicate longPredicate;
    java.util.function.DoublePredicate doublePredicate;
    java.util.function.BiPredicate<?, ?> biPredicate;
    //生产者
    java.util.function.Supplier supplier;
    java.util.function.BooleanSupplier booleanSupplier;
    java.util.function.IntSupplier intSupplier;
    java.util.function.LongSupplier longSupplier;
    java.util.function.DoubleSupplier doubleSupplier;
    //消费者
    java.util.function.Consumer consumer;
    java.util.function.IntConsumer intConsumer;
    java.util.function.LongConsumer longConsumer;
    java.util.function.DoubleConsumer doubleConsumer;
    java.util.function.ObjIntConsumer objIntConsumer;
    java.util.function.ObjLongConsumer objLongConsumer;
    java.util.function.ObjDoubleConsumer objDoubleConsumer;
    java.util.function.BiConsumer biConsumer;
    //一元运算
    java.util.function.UnaryOperator unaryOperator;
    java.util.function.LongUnaryOperator longUnaryOperator;
    java.util.function.IntUnaryOperator intUnaryOperator;
    java.util.function.DoubleUnaryOperator doubleUnaryOperator;
    //二元运算
    java.util.function.BinaryOperator binaryOperator;
    java.util.function.IntBinaryOperator intBinaryOperator;
    java.util.function.LongBinaryOperator longBinaryOperator;
    java.util.function.DoubleBinaryOperator doubleBinaryOperator;
}
