package fr.laerce.cinema.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  22/03/2017.
 *
 * @author fred
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //TODO
        //PSEUDO CONTROLLEURS - a éviter
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
    }

}
