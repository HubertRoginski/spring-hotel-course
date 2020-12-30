package org.springproject.springproject.controllerRest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserCustomerService;
import org.springproject.springproject.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final UserCustomerService userCustomerService;

    public UserRestController(UserService userService, UserCustomerService userCustomerService) {
        this.userService = userService;
        this.userCustomerService = userCustomerService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        return ResponseEntity.ok(userService.getAllUsers(page, size).getContent());
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User user,@AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        User createdUser= userService.createNewUser(user);
        if (Objects.isNull(createdUser)){
            return ResponseEntity.badRequest().build();
        }
        log.info("USERNAME: " +authenticationUser.getUsername());
        log.info("USER: " +authenticationUser.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNewBatchOfUsersWithCustomerAccount(@RequestBody List<User> users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userCustomerService.createBatchOfUsersWithCustomerAccount(users));
    }
}
