package org.kiev.cinema.config;

import org.kiev.cinema.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private ShaPasswordEncoder shaPasswordEncoder;

        @Autowired
        @Qualifier("adminUserDetailsServiceImpl")
        private UserDetailsService userDetailsService;

        public App1ConfigurationAdapter() {
        }

        @Autowired
        public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(shaPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/admin/**").authorizeRequests().anyRequest().hasAuthority(UserRole.ADMIN.name())
                    .and()
                .formLogin()
                    .permitAll()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login_check")
                    .failureUrl("/admin/login?error")
                    .usernameParameter("admin_login")
                    .passwordParameter("admin_password")
                    .defaultSuccessUrl("/admin/home")
                    .and()
                .logout()
                    .logoutUrl("/admin/admin_logout")
                    .logoutSuccessUrl("/admin/login?logout")
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling().accessDeniedPage("/admin/403")
                    .and()
                .csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private ShaPasswordEncoder shaPasswordEncoder;

        @Autowired
        @Qualifier("clerkUserDetailsServiceImpl")
        private UserDetailsService userDetailsService;

        public App2ConfigurationAdapter() {
        }

        @Autowired
        public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(shaPasswordEncoder);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/clerk/**").authorizeRequests().anyRequest().hasAuthority(UserRole.CLERK.name())
                    .and()

                .formLogin()
                    .permitAll()
                    .loginPage("/clerk/login")
                    .loginProcessingUrl("/clerk/login_check")
                    .failureUrl("/clerk/login?error")
                    .usernameParameter("clerk_login")
                    .passwordParameter("clerk_password")
                    .defaultSuccessUrl("/clerk/search_page")
                    .and()

                .logout()
                    .logoutUrl("/clerk/clerk_logout")
                    .logoutSuccessUrl("/clerk/login?logout")
                    .invalidateHttpSession(true)
                    .and()

                .exceptionHandling().accessDeniedPage("/clerk/403")
                    .and()

                .csrf().disable();
        }
    }

    @Configuration
    @EnableWebSecurity
    @Order(3)
    public static class App3ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        public App3ConfigurationAdapter() {
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/", "/*", "/static/**", "/public/**").permitAll()
                    .and()
                .csrf().disable();
        }
    }

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder() {
        return new ShaPasswordEncoder();
    }
}
