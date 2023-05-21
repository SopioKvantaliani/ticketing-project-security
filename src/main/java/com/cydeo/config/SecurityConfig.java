package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //we want to create bean from this class.
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }


//    @Bean
//    public UserDetailsService userDetailsService (PasswordEncoder encoder){ //UserDetailsService is interface
//
//        //step one:  I don't want to use user spring provides, I want to introduce my user or users.
//        List<UserDetails> userList = new ArrayList<>();
//
//        userList.add(new User ("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add( new User ("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//        return new InMemoryUserDetailsManager(userList); //saves in memory.
//
///*
//Here we created hardcoded users, introduced the spring to pass the security check and to enter the system. But it shouldn't be hardcoded and we'll change this part.
//Authority name should be created as is  "ROLE_ADMIN", using underscore;
//*/
//
//    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception { //we use Security FilterChain to introduce our own form.
        return http
                .authorizeRequests() //everything in this application needs to be authorized, related with role
                .antMatchers("/user/**").hasAuthority("Admin") //it is method, which spring security is giving to describe the roles. ** means everything under user controller. if we want only create e.g. instead of ** we put "/user/create"
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee") //whatever we describe as role, it needs to be matched as database;
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("MANAGER", "EMPLOYEE") //when we want to give access to more than one role;
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE") //hasAuthority is same, but here we need to use format ROLE_EMPLOYEE.
                .antMatchers("/", "/login", "/fragments/**", "/assets/**", "/images/**")
                .permitAll() //anybody can access
                .anyRequest().authenticated()//anything beside "/", "/login" and ect needs to be authenticated.
                .and()
//                .httpBasic() //as we see different sign in page, means spring uses some http behind the scene. We need to use our own form. Pop-up box is basically this httpBasic.
                .formLogin()//I used formLogin to introduce my validation form.
                .loginPage("/login")// we need login view. This is representation of my Login page
//                .defaultSuccessUrl("/welcome") //whenever user authenticated with the correct username and password, we land welcome page view.
                .failureUrl("/login?error=true")//if user put wrong information, we want to navigate to this url.
                .successHandler(authSuccessHandler)//when we want to customize which page to land which role.
                .permitAll()//this changes should be accessible for everyone.
                .and().logout() //managing logOut
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(120)
                    .key("cydeo")
                    .userDetailsService(securityService)
                .and()
                .build();
    }
    //when we say "/", "/login", and ect. we tell spring security do not ask security check specifically here and that's why it does not require username/password
    //


}

/*
Authentication is the process of verifying the identity of a user, device, or system before
granting access to a resource or service. Authentication answers the question, "Who are you?"

Authorization, on the other hand, is the process of granting or denying access to a
resource or service based on the authenticated identity of the user or system.
Authorization answers the question, "What are you allowed to do?"
 */


/*Why we created securityConfig as a class?
When we write big concept, as configurations, we never put it in runner class.
If we create only one method for having Bean, we can put it in runner class as we did mapper and passwordEncoder.
We might have different configuration classes in security, api and so on.

*/
