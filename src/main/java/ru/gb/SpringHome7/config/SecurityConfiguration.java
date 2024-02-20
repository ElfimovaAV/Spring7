package ru.gb.SpringHome7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Переопределение конфигурации для защиты конечных точек в зависимости от ролей
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/private-data").hasRole("ADMIN")
                    .antMatchers("/public-data").hasRole("USER")
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login.html");
    }

    /**
     * Определяем компонент UserDetailsService
     * @return manager пользовательских данных
     */
    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;

    }

}
