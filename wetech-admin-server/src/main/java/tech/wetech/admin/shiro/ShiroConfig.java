package tech.wetech.admin.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.SoftHashMap;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiro自定义过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new JwtFilter());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilters(filters);
        //配置记住我或认证通过可以访问的地址
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/", "anon");
        // begin: 此处仅仅演示模式使用，实际开发中请删除
        filterChainDefinitionMap.put("/datasource/initialize", "anon");
        // end: 此处仅仅演示模式使用，实际开发中请删除
        filterChainDefinitionMap.put("/auth/login", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/auth/logout", "anon");
        //过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        //swagger start
        filterChainDefinitionMap.put("/swagger-ui", "anon");
        filterChainDefinitionMap.put("/swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v3/api-docs", "anon");
        filterChainDefinitionMap.put("/csrf", "anon");
        //swagger end
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/loading/**", "anon");
        filterChainDefinitionMap.put("/avatar2.jpg", "anon");
        filterChainDefinitionMap.put("/index.html", "anon");
        filterChainDefinitionMap.put("/logo.png", "anon");
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 会话管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        defaultSessionManager.setSessionValidationSchedulerEnabled(false);
        return defaultSessionManager;
    }

    /**
     * Subject工厂
     *
     * @return
     */
    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new JwtSubjectFactory();
    }

    /**
     * 在生产环境中使用的基于简单内存的CacheManager @link CacheManager}实现。它不会导致内存泄漏，因为它会产生{@link Cache Cache}s，
     * 由{@link SoftHashMap SoftHashMap}s支持，
     * 后者根据运行时环境的内存*限制和垃圾收集行为自动调整大小。
     * 此处根据实际情况可以替换成ehcache、redis等实现
     *
     * @return
     */
    @Bean
    public MemoryConstrainedCacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setAuthenticationCachingEnabled(true);
        jwtRealm.setAuthorizationCachingEnabled(true);
        //token匹配
//        jwtRealm.setCredentialsMatcher(new JwtCredentialsMatcher());
        return jwtRealm;
    }

    /**
     * 安全管理器
     *
     * @param jwtRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(JwtRealm jwtRealm, SubjectFactory subjectFactory, SessionManager sessionManager, CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jwtRealm);

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     *
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager);
        return methodInvokingFactoryBean;
    }


}
