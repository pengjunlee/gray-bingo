package com.pengjunlee.bingo;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.pengjunlee.bingo.config.BingoHelperBuilder;
import com.pengjunlee.bingo.config.BingoMeta;
import com.pengjunlee.bingo.config.CacheDataWrapper;
import com.pengjunlee.bingo.config.CaffeineHelper;
import com.pengjunlee.bingo.constants.BingoStringCst;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class CaffeineHelperBuilder extends BingoHelperBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaffeineHelperBuilder.class);


    /**
     * 通过添加如下配置开启组件
     * bingo:
     * helper:
     * caffeine: 'open'
     */
    public void build() {
        if (BingoMeta.helperEnabled(BingoStringCst.HELPER_CAFFEINE)) {
            this.registerCaffeineClient();
        } else {
            LOGGER.warn("组件[ {} ]开关未打开，请检查配置项[ {} ]！", CaffeineHelperBuilder.class, BingoStringCst.HELPER_CAFFEINE);
        }
    }

    @Override
    public void remove() {

    }

    private void registerCaffeineClient() {
        new CaffeineHelper().initCache(
                Caffeine.newBuilder()
                        .softValues() // 设置软引用，当GC回收兵堆空间不足时，会释放缓存
                        .initialCapacity(1000)
                        .maximumSize(100000)
                        .expireAfter(new Expiry<Object, CacheDataWrapper>() {

                            @Override
                            public long expireAfterCreate(Object o, CacheDataWrapper cdw, long l) {
                                if (cdw.getTimeUnit() != null) {
                                    return cdw.getTimeUnit().toNanos(cdw.getDelay());
                                }
                                return l;
                            }

                            @Override
                            public long expireAfterUpdate(Object o, CacheDataWrapper cacheDataWrapper, long l, long l1) {
                                if (cacheDataWrapper.getTimeUnit() != null) {
                                    return cacheDataWrapper.getTimeUnit().toNanos(cacheDataWrapper.getDelay());
                                }
                                return 0;
                            }

                            @Override
                            public long expireAfterRead(Object o, CacheDataWrapper cacheDataWrapper, long l, long l1) {
                                return l1;
                            }
                        }).build()
        );
    }
}
