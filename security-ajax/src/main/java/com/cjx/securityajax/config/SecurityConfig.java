package com.cjx.securityajax.config;

import com.cjx.securityajax.handle.*;
import com.cjx.securityajax.util.SelfAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //  未登陆时返回 JSON 格式的数据给前端（否则为 html）
    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    // 登录成功返回的 JSON 格式数据给前端（否则为 html）
    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    //  登录失败返回的 JSON 格式数据给前端（否则为 html）
    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;

    // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;

    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;

    // 自定义安全认证
    @Autowired
    SelfAuthenticationProvider provider;

    //安全认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //关闭跨域拦截
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/","/user/register").permitAll()
                /*.antMatchers("/user/manager").hasAnyRole("MANAGER","ROOT")
                将授权操作分配root权限
                .antMatchers("/user/root","/role/grant").hasRole("ROOT")*/
                //根据角色动态分配路由
                .withObjectPostProcessor(new DefinedObjectPostProcessor())
                //配置决策方式
                .accessDecisionManager(accessDecisionManager())


                .and()
                .exceptionHandling()
                //未登录拦截
                .authenticationEntryPoint(authenticationEntryPoint)
                //无权访问拦截
                .accessDeniedHandler(accessDeniedHandler)

                //登录
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()

                //注销
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }


    /**
     * AffirmativeBased – 任何一个AccessDecisionVoter返回同意则允许访问
     * ConsensusBased – 同意投票多于拒绝投票（忽略弃权回答）则允许访问
     * UnanimousBased – 每个投票者选择弃权或同意则允许访问
     *
     * 决策管理
     */
    private AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new AuthenticatedVoter());
    /*    decisionVoters.add(new RoleVoter());*/
        /* 路由权限管理 */
        decisionVoters.add(new UrlRoleAuthHandler());
        return new UnanimousBased(decisionVoters);
    }

    @Autowired
    private UrlRolesFilterHandler urlRolesFilterHandler;


    class DefinedObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setSecurityMetadataSource(urlRolesFilterHandler);
            return object;
        }
    }
}
