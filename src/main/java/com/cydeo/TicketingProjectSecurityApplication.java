package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication  //this includes @Configuration
public class TicketingProjectSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketingProjectSecurityApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean //1) encoded structure
    public PasswordEncoder passwordEncoder () { //this class is built in with ready methods inside. this helps us to encode our
        return new BCryptPasswordEncoder(); //BCryptPasswordEncoder is implementation and if someone asks why we used, we can simply say there are a bunch of implementations, but I know this one and prefer to use.
                                            //Responsibility of this implementation: it takes UI password and changes to encoded password, which goes to dataBase;
    }



}
