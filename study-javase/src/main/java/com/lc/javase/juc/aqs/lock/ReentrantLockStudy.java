package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockStudy {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        // 1.1 lock()
        lock.lock();
        try {
            // 1.2 lockInterruptibly()
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 1.3 tryLock(time)
            lock.tryLock(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2 condition1
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        lock.lock();
        try {
            try {
                condition1.await();
                condition2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
        condition1.signal();
        condition2.signal();

        // 3 ReentrantReadWriteLock
        ReadWriteLock rwLock = new ReentrantReadWriteLock(false);
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();
        readLock.lock();
        readLock.unlock();
        writeLock.lock();
        writeLock.unlock();
    }
}
