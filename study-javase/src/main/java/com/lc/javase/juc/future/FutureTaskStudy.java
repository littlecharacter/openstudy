package com.lc.javase.juc.future;

import com.lc.javase.juc.pool.ThreadPoolUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * FutureTask可用于异步获取执行结果或取消执行任务的场景。
 *
 * @author LittleRW
 */
public class FutureTaskStudy {
    public static void main(String[] args) throws InterruptedException {
        simpleTest();
        System.out.println("-----------------------simpleTest(↑)complexTest(↓)-----------------------");
        complexTest();
    }


    private static void simpleTest() {
        Callable<Integer> callableTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // Simulate a time-consuming task
                Thread.sleep(2000);
                return 42;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callableTask);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            Integer result = futureTask.get();
            System.out.println("Task result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void complexTest() throws InterruptedException {
        ExpensiveOperation operation = new ExpensiveOperation();
        Memorizer4<String, Integer> memorizer = new Memorizer4<>(operation);
        ExecutorService es = ThreadPoolUtil.getXxThreadPool();
        for (int i = 0; i < 10; i++) {
            es.execute(new FutureTaskThread(memorizer, Integer.toString(1)));
        }
        TimeUnit.MILLISECONDS.sleep(10000L);
        es.shutdown();
    }


    public void work() {

    }

    /**
     * 计算接口-模拟数据库操作
     *
     * @param <K>
     * @param <V>
     * @author LittleRW
     */
    private interface Computable<K, V> {
        V compute(K k);
    }

    /**
     * 耗时的操作：模拟数据库操作
     *
     * @author LittleRW
     */
    private static class ExpensiveOperation implements Computable<String, Integer> {
        @Override
        public Integer compute(String s) {
            try {
                System.out.println("很长时间的数据库操作！");
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Integer(s);
        }
    }

    private static class FutureTaskThread implements Runnable {
        private Computable<String, Integer> c;
        private String s;

        public FutureTaskThread(Computable<String, Integer> c, String s) {
            this.c = c;
            this.s = s;
        }

        @Override
        public void run() {
            c.compute(s);
        }
    }

    private static class Memorizer1<K, V> implements Computable<K, V> {
        private final Map<K, V> cache = new HashMap<K, V>();
        private final Computable<K, V> c;

        public Memorizer1(Computable<K, V> c) {
            this.c = c;
        }

        @Override
        public synchronized V compute(K k) {
            V result = cache.get(k);
            String flag = "从缓存中获取！";

            if (result == null) {
                result = c.compute(k);
                cache.put(k, result);
                flag = "从数据库中获取！";
            }

            System.out.println(flag);

            return result;
        }
    }

    private static class Memorizer2<K, V> implements Computable<K, V> {
        private final Map<K, V> cache = new ConcurrentHashMap<K, V>();
        private final Computable<K, V> c;

        public Memorizer2(Computable<K, V> c) {
            this.c = c;
        }

        @Override
        public V compute(K k) {
            V result = cache.get(k);
            String flag = "从缓存中获取！";

            if (result == null) {
                result = c.compute(k);
                cache.put(k, result);
                flag = "从数据库中获取！";
            }

            System.out.println(flag);

            return result;
        }
    }

    private static class Memorizer3<K, V> implements Computable<K, V> {
        private final Map<K, FutureTask<V>> cache = new ConcurrentHashMap<K, FutureTask<V>>();
        private final Computable<K, V> c;

        public Memorizer3(Computable<K, V> c) {
            this.c = c;
        }

        @Override
        public V compute(final K k) {
            FutureTask<V> result = cache.get(k);

            if (result == null) {
                FutureTask<V> newResult = new FutureTask<V>(new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        System.out.println("从数据库中获取！");
                        return c.compute(k);
                    }
                });
                cache.put(k, newResult);
                if (result == null) {
                    result = newResult;
                    result.run();
                }
            } else {
                System.out.println("从缓存中获取！");
            }

            V value = null;
            try {
                value = result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return value;
        }
    }

    private static class Memorizer4<K, V> implements Computable<K, V> {
        private final ConcurrentHashMap<K, FutureTask<V>> cache = new ConcurrentHashMap<K, FutureTask<V>>();
        private final Computable<K, V> c;

        public Memorizer4(Computable<K, V> c) {
            this.c = c;
        }

        @Override
        public V compute(final K k) {
            FutureTask<V> result = cache.get(k);

            if (result == null) {
                FutureTask<V> newResult = new FutureTask<V>(new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        System.out.println("从数据库中获取！");
                        return c.compute(k);
                    }
                });

                /**
                 * 这等价于：
                 * if (!map.containsKey(key))
                 * 		return map.put(key, value);
                 * else
                 *		return map.get(key);
                 * 注：putIfAbsent是原子操作！！！
                 */
                result = cache.putIfAbsent(k, newResult);
                if (result == null) {
                    result = newResult;
                    result.run();
                }
            } else {
                System.out.println("从缓存中获取！");
            }

            V value = null;
            try {
                value = result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return value;
        }
    }
}