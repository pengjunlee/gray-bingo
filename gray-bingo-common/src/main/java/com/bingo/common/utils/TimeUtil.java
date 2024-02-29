package com.bingo.common.utils;

import java.util.concurrent.TimeUnit;

/**
 * Provides millisecond-level time of OS.
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public final class TimeUtil {

    private static volatile long currentTimeMillis;

    static {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(() -> {
            while (true) {
                currentTimeMillis = System.currentTimeMillis();
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (Throwable ignored) {

                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("bingo-time-tick-thread");
        daemon.start();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}
