package com.shiro.example.shirodemo.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

    //加载shiroFilter权限控制规则（从数据库读取然后配置）
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/home/login");
        shiroFilterFactoryBean.setSuccessUrl("/home/index");
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // anon：它对应的过滤器里面是空的,什么都没做
        filterChainDefinitionMap.put("/home/detail", "authc");//需要验证才能访问
        filterChainDefinitionMap.put("/**", "anon");//不拦截，它对应的过滤器里是空的，什么都没做
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    /**
     * 权限管理
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm());

        return defaultWebSecurityManager;
    }

    /**
     * 注册shiro权限
     * @return
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行 (生命周期)
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
