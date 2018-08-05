package tech.wetech.admin.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import tech.wetech.admin.core.shiro.spring.SpringCacheManagerWrapper;

/**
 * @author cjbi
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

    /**
     * 缓存管理器
     *
     * @return
     */
    @Bean
    public SpringCacheManagerWrapper cacheManager(EhCacheCacheManager springCacheManager) {
        SpringCacheManagerWrapper cacheManager = new SpringCacheManagerWrapper();
        cacheManager.setCacheManager(springCacheManager);
        return cacheManager;
    }


    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheCacheManager springCacheManager(CacheManager cacheManager) {
        return new EhCacheCacheManager(cacheManager);
    }

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheManagerFactoryBean ehcacheManager() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource("ehcache.xml");
        ehCacheManagerFactoryBean.setConfigLocation(resource);
        return ehCacheManagerFactoryBean;
    }

}
