package com.xwork.expense.configuration;

import com.xwork.expense.security.CustomerUserDetailsService;
import com.xwork.expense.security.FormLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * Spring Security 权限安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomerUserDetailsService userDetailsService;

    /**
     * 配置静态资源部被Security拦截
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**","/","/question/upload","/upload/multiUploadFile");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated()//所有以admin开头的请求 都需要进行认证（就是登陆之后才能访问）
                .and()
                .formLogin().loginPage("/toLogin").loginProcessingUrl("/login_check").permitAll()//登录页面的url是“/toLogin” 登陆请求执行的url是"/login_check"
                .successHandler(formLoginSuccessHandler())//配置了一个登陆成功后的Handler 这个Handler是自己定义的
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/toLogin")//登出的url是"/logout" 登出成功后访问"/toLogin"
                .and().csrf().disable().headers().cacheControl();

    }

    /**
     * 配置自定义的UserDetailsService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 密码加密工具
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler(){
        FormLoginSuccessHandler handler = new FormLoginSuccessHandler();
        handler.setDefaultTargetUrl("/admin/index");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }
}
