package pl.swietek.springbootapi.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.models.Role;


import java.util.ArrayList;

@Configuration
public class UserConfig {

//        @Bean
//        CommandLineRunner commandLineRunnerUser(UserService service) {
//            return args -> {
//                // service.saveRole(new Role(null,"ROLE_USER"));
//                // service.saveRole(new Role(null,"ROLE_MANAGER"));
//                // service.saveRole(new Role(null,"ROLE_ADMIN"));
//
//                //service.saveUser(new User(null, "Andrzej", "username1","andrzej@swietek.pl", "zaq1@wsx", new ArrayList<>()));
//                //service.saveUser(new User(null, "Andrzej", "username2","dominik@swietek.pl", "zaq1@wsx", new ArrayList<>()));
//                //service.saveUser(new User(null, "Andrzej", "username3", "mikołaj@swietek.pl","zaq1@wsx", new ArrayList<>()));
//
//                //service.addRuleToUser("username1", "ROLE_USER");
//                //service.addRuleToUser("username2", "ROLE_MANAGER");
//                //service.addRuleToUser("username3", "ROLE_ADMIN");
//
//            };
//        }

}
