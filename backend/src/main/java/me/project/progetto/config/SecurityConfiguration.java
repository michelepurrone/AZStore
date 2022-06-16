package me.project.progetto.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Proteggiamo gli endpoints /api/orders
        http.authorizeRequests()
                .antMatchers("/api/orders/**")
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        //Filtro CORS
        http.cors();

        //Risposta non-vuota forzata per il 401 per rendere la risposta più user-friendly
        Okta.configureResourceServer401ResponseBody(http);

        //Disabilitiamo il CSRF dato che non usiamo cookies
        http.csrf().disable();
    }

    //http.csrf().disable()
    //                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //

}