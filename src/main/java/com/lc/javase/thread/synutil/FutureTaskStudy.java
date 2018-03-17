package com.lc.javase.thread.synutil;

import com.lc.javase.thread.ThreadPoolExecutorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;



/**
 * FutureTask可用于异步获取执行结果或取消执行任务的场景。
 * @author LittleRW
 */
public class FutureTaskStudy {
    public void work() {
		ExpensiveOperation eo = new ExpensiveOperation();
        Memorizer4<String, Integer> m = new Memorizer4<>(eo);
		ExecutorService es = ThreadPoolExecutorUtil.getThreadPool();
		for (int i = 0; i < 10; i++) {
			es.execute(new FutureTaskThread(m, Integer.toString(1)));
		}
	}

    /**
     * 计算接口-模拟数据库操作
     * @author LittleRW
     * @param <K>
     * @param <V>
     */
    private interface Computable<K, V>{
        V compute(K k);
    }

    /**
     * 模拟数据库操作
     * @author LittleRW
     */
    private class ExpensiveOperation implements Computable<String, Integer>{
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

    private class FutureTaskThread implements Runnable {
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

    private class Memorizer1<K, V> implements Computable<K, V>{
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

    private class Memorizer2<K, V> implements Computable<K, V>{
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

    private class Memorizer3<K, V> implements Computable<K, V>{
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
            }else {
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

    private class Memorizer4<K, V> implements Computable<K, V>{
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
            }else {
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