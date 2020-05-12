package com.lc.javase.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockStudy {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        Condition condition1 = lock.newCondition();
        lock.lock();
        lock.tryLock();
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            lock.tryLock(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
        condition1.signal();
        Condition condition2 = lock.newCondition();
        try {
            condition2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        condition2.signal();

        ReadWriteLock rwLock = new ReentrantReadWriteLock(false);
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();
        readLock.lock();
        readLock.unlock();
        writeLock.lock();
        writeLock.unlock();
    }
}
