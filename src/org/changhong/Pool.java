package org.changhong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jchanghong on 14-1-13.
 */
public class Pool {
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    public static void exe(Runnable runnable) {
        pool.execute(runnable);
    }

    public static ExecutorService getPool() {
        return pool;
    }
}
