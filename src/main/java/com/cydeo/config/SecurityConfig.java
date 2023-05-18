package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration //we want to create bean from this class.
public class SecurityConfig {




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
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .authorizeRequests() //everything in this application needs to be authorized, related with roles
//                .antMatchers("/user/**").hasRole("ADMIN")// when we say "user" here, we give it access to everything under user controller ("save", "create" etc)
//                .antMatchers("project/**").hasRole("MANAGER") //giving access to manager role
//                .antMatchers("/task/employee/**").hasRole("EMPLOYEE")
//                .antMatchers("/task/**").hasRole("MANAGER")
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("MANAGER", "EMPLOYEE") //when we want to give access to more than one role;
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE") //hasAuthority is same, but here we need to use format ROLE_EMPLOYEE.
                .antMatchers("/", "/login", "/fragments/**", "/assets/**", "/images/**")
                .permitAll() //anybody can access
                .anyRequest().authenticated()//anything beside "/", "/login" and ect needs to be authenticated.
                .and()
//                .httpBasic() //as we see different sign in page, means spring uses some http behind the scene. We need to use our own form. Pop-up box is basically this httpBasic.
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome") //whenever we successfully landed, we'll be landed on welcome page
                .failureUrl("/login?error=true")
                .permitAll()
                .and().build();
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
