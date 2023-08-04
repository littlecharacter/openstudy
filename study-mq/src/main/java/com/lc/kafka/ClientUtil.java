package com.lc.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.*;

/**
 * 生产中，这里可以抽出一个 ClientFactory：每个 topic 一个 Producer 和 Consumer，Map
 *
 * @author gujixian
 * @since 2023/8/3
 */
public final class ClientUtil {
    private static final ExecutorService[] threadPools = new ExecutorService[Runtime.getRuntime().availableProcessors() + 1];
    // private static final BlockingQueue<Runnable>[] qs = new BlockingQueue[]{
    //         new LinkedBlockingQueue<>(),
    //         new LinkedBlockingQueue<>(),
    //         new LinkedBlockingQueue<>(),
    //         new LinkedBlockingQueue<>()
    // };

    private static final Properties pp = new Properties();
    private static final Properties cp = new Properties();

    static {
        // 初始化线程池 - 注意生产中一定要用自定义线程池（工作线程为 1 的线程池）
        for (int i = 0; i < threadPools.length; i++) {
            int finalI = i;
            threadPools[i] = new ThreadPoolExecutor(
                    1,
                    1,
                    0L, TimeUnit.MILLISECONDS,
                    new SynchronousQueue<>(),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setName("消息消费线程-" + finalI);
                            return t;
                        }
                    },
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
        }

        // new Thread(() -> {
        //     while (true) {
        //         for (int i = 0; i < qs.length; i++) {
        //             BlockingQueue<Runnable> q = qs[i];
        //             System.out.println("--------------------------------------------------------------------------------------------队列-" + i + "长度：" + q.size());
        //             try {
        //                 TimeUnit.SECONDS.sleep(1);
        //             } catch (InterruptedException e) {
        //                 throw new RuntimeException(e);
        //             }
        //         }
        //     }
        // }).start();

        // 生产端配置
        pp.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kfk:19092,kfk:29092,kfk:39092");
        // 序列化方式
        pp.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        pp.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // acks：0, 1, -1，默认是 1
        // 可以将 ack 设置成 -1，观察 ISR 中的一个副本故障时，生产者阻塞的现象（默认阻塞 10 秒，可配置）
        pp.setProperty(ProducerConfig.ACKS_CONFIG, "1");


        // 消费端配置
        cp.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kfk:19092,kfk:29092,kfk:39092");
        // 反序列化方式
        cp.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        cp.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 消费组
        cp.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "zs");
        // 拉取行为：从什么位置开始拉取消息（新加入消费端或者消费端重启但是offset丢失的情况）
        // earliest: automatically reset the offset to the earliest offset，最开始
        // latest: automatically reset the offset to the latest offset，最后，默认
        // none: throw exception to the consumer if no previous offset is found for the consumer's group
        // anything else: throw exception to the consumer
        // cp.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // 拉取行为：一次拉取多少条消息
        // cp.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "");
        // 拉取行为：拉取消息的时间间隔
        // cp.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "");
        // 是否自动提交，默认自动提交，注意自动提交时异步的，下面一个配置项是自动提交的间隔 -> 自动提交会导致“重复消费数据”和“丢数据”
        // cp.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 默认间隔是 5 秒
        // cp.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5");

    }

    private ClientUtil() {
    }

    public static ClientUtil instance() {
        return ClientUtilInner.instance;
    }

    public ExecutorService getThreadPool(int partition) {
        int index = partition % threadPools.length;
        return threadPools[index];
    }

    public <K, V> Producer<K, V> getProducer() {
        return new KafkaProducer<>(pp);
    }

    public <K, V> Consumer<K, V> getConsumer(String autoCommit) {
        cp.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        // cp.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10"); // 测试单线程和多线程效率是设置
        return new KafkaConsumer<>(cp);
    }

    private static class ClientUtilInner {
        private static ClientUtil instance = new ClientUtil();
    }
}
