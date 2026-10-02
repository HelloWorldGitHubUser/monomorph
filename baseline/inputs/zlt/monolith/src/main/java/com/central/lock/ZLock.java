package com.central.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁对象抽象
 *
 * @author zlt
 * @date 2020/7/28
 */
@AllArgsConstructor
public class ZLock implements AutoCloseable {
    @Getter
    private final Object lock;

    private final KeyedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}





