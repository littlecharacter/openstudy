package com.lc.javase.juc.pattern;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.*;

/**
 * 生产者-消费者模式
 */
public class ProducerConsumerPattern {
    public void workWithNotify() {
        Bread b = new Bread(100);

        ExecutorService es = Executors.newCachedThreadPool();
        Producer p = new Producer(b);
        Consumer c = new Consumer(b);
        es.execute(p);
        es.execute(p);
        es.execute(c);
        es.execute(c);
        es.shutdown();
    }

    private class Bread {
        private int max;
        private int count = 0;

        public Bread(int max) {
            super();
            this.max = max;
        }

        public synchronized void produce(){
            while(count == max){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count ++;
            System.out.println(Thread.currentThread() + "[生产面包]：" + count);
            this.notifyAll();
        }

        public synchronized void consume(){
            while(count == 0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread() + "[消费面包]：" + count);
            count --;
            this.notifyAll();
        }
    }

    private class Producer implements Runnable {
        private Bread b;

        Producer(Bread b) {
            this.b = b;
        }

        public void run() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b.produce();
            }
        }
    }

    private class Consumer implements Runnable {
        private Bread b;

        Consumer(Bread b) {
            this.b = b;
        }

        public void run() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b.consume();
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void workWithBlockingQueue() {
        BlockingQueue<Toast> toastQueue = new ArrayBlockingQueue<>(10);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Toaster(toastQueue));
        es.execute(new Eater(toastQueue));
        es.shutdown();
    }

    private class Toast {
        private String name;

        public Toast(String name) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class Toaster implements Runnable{
        private BlockingQueue<Toast> toastQueue;

        public Toaster(BlockingQueue<Toast> toastQueue) {
            super();
            this.toastQueue = toastQueue;
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    toastQueue.put(new Toast("面包[" + i +"]"));//阻塞式方法，队列尾部放入
                    System.out.println(Thread.currentThread() + "：生产面包[" + i +"]");
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Eater implements Runnable{
        private BlockingQueue<Toast> toastQueue;

        public Eater(BlockingQueue<Toast> toastQueue) {
            super();
            this.toastQueue = toastQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    Toast toast = toastQueue.take();//阻塞式方法，队列头部拿取
                    System.out.println(Thread.currentThread() + "：消费" + toast.getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void workWithPipedIO() {
        ExecutorService es = Executors.newCachedThreadPool();
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        es.execute(sender);
        es.execute(receiver);
        es.shutdown();
    }

    private class Sender implements Runnable{
        PipedWriter pw = new PipedWriter();
        @Override
        public void run() {
            for(char c = 'A'; c <= 'z'; c++){
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    pw.write(c);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Receiver implements Runnable{
        private Sender sender;
        PipedReader pr = null;

        public Receiver(Sender sender) {
            super();
            this.sender = sender;
        }

        @Override
        public void run() {
            try {
                if (pr == null) {
                    pr = new PipedReader(sender.pw);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                    System.out.println((char)pr.read());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
