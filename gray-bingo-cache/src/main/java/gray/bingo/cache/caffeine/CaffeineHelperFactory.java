package gray.bingo.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import gray.bingo.common.Enums.BingoHelperEnum;
import gray.bingo.common.config.BingoHelperFactory;
import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.constants.BingoCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * CaffeineHelper的建造者
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Slf4j
public class CaffeineHelperFactory extends BingoHelperFactory {

    /**
     * 通过添加如下配置开启组件
     * bingo:
     * helper:
     * caffeine: 'open'
     */
    public void build(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (BingoMeta.helperEnabled(BingoHelperEnum.CAFFEINE.getCode())) {
            this.initCaffeineClient();
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 成功！", helperName());
        } else {
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 失败，原因组件未启用，请检查配置项: [ {} ] ！", helperName(), BingoCst.CONF_HELPER_ENABLES);
        }
    }

    @Override
    public String helperName() {
        return BingoHelperEnum.CAFFEINE.getCode();
    }

    private void initCaffeineClient() {
        new CaffeineHelper().initCache(
                Caffeine.newBuilder()
                        // 设置软引用，当GC回收兵堆空间不足时，会释放缓存
                        .softValues()
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
