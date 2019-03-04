package fr.laerce.cinema.configuration;

import fr.laerce.cinema.service.JpaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private JpaUserDetailsService jpaUserDetailsService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setUserDetailsService(JpaUserDetailsService jpaUserDetailsService){
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.authorizeRequests()
//                    .antMatchers("/**").permitAll()                                   //BOURRIN
                    .antMatchers("/", "/home").permitAll()                                   //BOURRIN
//                    .antMatchers("/**").hasAnyAuthority("ADMIN")                                   //BOURRIN
//                    .antMatchers("/", "/home","/login", "/userlist", "/newpass").permitAll()
//                    .antMatchers("/**").hasAnyAuthority("ADMIN")
//                    .antMatchers("/admin/**").hasRole("Admin")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()*/

                .authorizeRequests()
                .antMatchers("/", "/home")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/index", true)

                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable();
        ;
    }


    //Constructeur de gestion d'authentification
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("recup")
//                .withUser("Admin")
                .password(bCryptPasswordEncoder().encode("recup"))
//                .password("p@ssw0rd")
                .roles("ADMIN","USER")
                .authorities("WITHDRAW","DEPOSIT","ADMIN");
        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
