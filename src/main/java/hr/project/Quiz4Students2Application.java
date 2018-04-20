package hr.project;

import hr.project.util.ListeningGameQuestionRegistry;
import hr.project.util.WebAgentSessionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Quiz4Students2Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(Quiz4Students2Application.class, args);
    }

    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }

    @Bean
    public WebAgentSessionRegistry webAgentSessionRegistry(){
        return new WebAgentSessionRegistry();
    }

    @Bean
    public ListeningGameQuestionRegistry listeningGameQuestionRegistry(){
        return new ListeningGameQuestionRegistry();
    }
/*
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        return passwordEncoder;
    }

    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        UserRepository userRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(new SimpleUserDetailsServiceImpl(userRepository))
                    .passwordEncoder(passwordEncoder());
        }
    }

    @EnableWebSecurity
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    class MethodSecurityConfig extends WebSecurityConfigurerAdapter {

       @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().fullyAuthenticated().and().
                    httpBasic().and().
                    csrf().disable();
        }
    }*/
}

