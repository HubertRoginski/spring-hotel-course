package org.springproject.springproject.controllerRest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User user,@AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        User createdUser= userService.createNewUser(user);
        if (Objects.isNull(createdUser)){
            return ResponseEntity.badRequest().build();
        }
        log.info("rola " +authenticationUser.getUsername());
        log.info("to " +authenticationUser.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
