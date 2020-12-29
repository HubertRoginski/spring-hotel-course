package org.springproject.springproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springproject.springproject.config.HotelCustomerConfig;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.UserRepository;
import org.springproject.springproject.service.CustomerService;

import java.util.Objects;

@Component
@Slf4j
//@Profile("dev-test")
public class TestRunner implements CommandLineRunner {

    private final HotelCustomerConfig hotelCustomerConfig;
    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TestRunner( HotelCustomerConfig hotelCustomerConfig, CustomerService customerService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.hotelCustomerConfig = hotelCustomerConfig;
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {


        if (Objects.isNull(userRepository.findByUsername("stachu"))){
            User user = User.builder()
                    .username("stachu")
                    .password(bCryptPasswordEncoder.encode("japycz"))
                    .email("stachu@gmail.com")
                    .enabled(true)
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(user);
        }
        if (Objects.isNull(userRepository.findByUsername("piotrek"))){
            User user = User.builder()
                    .username("piotrek")
                    .password(bCryptPasswordEncoder.encode("piotrek"))
                    .email("piotrek@gmail.com")
                    .enabled(true)
                    .role("ROLE_MANAGER")
                    .build();
            userRepository.save(user);
        }
        if (Objects.isNull(userRepository.findByUsername("solejuk"))){
            User user = User.builder()
                    .username("solejuk")
                    .password(bCryptPasswordEncoder.encode("solejuk"))
                    .email("solejuk@gmail.com")
                    .enabled(true)
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
        }


    }

}
