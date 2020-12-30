package org.springproject.springproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;
import org.springproject.springproject.util.service.DatabaseFillingService;

@Component
@Slf4j
public class TestRunner implements CommandLineRunner {

    private final UserService userService;
    private final DatabaseFillingService databaseFillingService;

    public TestRunner(UserService userService, DatabaseFillingService databaseFillingService) {
        this.userService = userService;
        this.databaseFillingService = databaseFillingService;
    }


    @Override
    public void run(String... args) throws Exception {

//        userService.createNewUser(User.builder()
//                .username("stachu")
//                .password("japycz")
//                .email("stachu@gmail.com")
//                .role("ROLE_ADMIN")
//                .enabled(true)
//                .build());
//
//        userService.createNewUser(User.builder()
//                .username("piotrek")
//                .password("piotrek")
//                .email("piotrek@gmail.com")
//                .role("ROLE_MANAGER")
//                .enabled(true)
//                .build());
//
//        userService.createNewUser(User.builder()
//                .username("solejuk")
//                .password("solejuk")
//                .email("solejuk@gmail.com")
//                .role("ROLE_USER")
//                .enabled(true)
//                .build());
//
//        databaseFillingService.fillWithUsersWithCustomerAccount(30);
//        databaseFillingService.fillWithUsers(10);
//        databaseFillingService.fillWithEmployees(50);
//        databaseFillingService.fillWithRooms(50);

    }

}
