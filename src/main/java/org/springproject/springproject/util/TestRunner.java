package org.springproject.springproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springproject.springproject.config.HotelCustomerConfig;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.OldPersonnelRepository;
import org.springproject.springproject.repository.UserRepository;
import org.springproject.springproject.service.CustomerService;

import java.util.Objects;

@Component
@Slf4j
//@Profile("dev-test")
public class TestRunner implements CommandLineRunner {

    private final OldPersonnelRepository oldPersonnelRepository;
    private final HotelCustomerConfig hotelCustomerConfig;
    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TestRunner(OldPersonnelRepository oldPersonnelRepository, HotelCustomerConfig hotelCustomerConfig, CustomerService customerService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.oldPersonnelRepository = oldPersonnelRepository;
        this.hotelCustomerConfig = hotelCustomerConfig;
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

//        if (Objects.isNull(userRepository.findByUsername("stachu"))){
//            User user = User.builder()
//                    .username("stachu")
//                    .password(bCryptPasswordEncoder.encode("japycz"))
//                    .enabled(true)
//                    .role("ROLE_ADMIN")
//                    .build();
//            userRepository.save(user);
//        }

//        if (Objects.isNull(userRepository.findByUsername("stachu"))){
//            User user = User.builder()
//                    .username("stachu")
//                    .password(bCryptPasswordEncoder.encode("japycz"))
//                    .email("stachu@gmail.com")
//                    .enabled(true)
//                    .role("ROLE_ADMIN")
//                    .build();
//            userRepository.save(user);
//        }
//        if (Objects.isNull(userRepository.findByUsername("piotrek"))){
//            User user = User.builder()
//                    .username("piotrek")
//                    .password(bCryptPasswordEncoder.encode("piotrek"))
//                    .email("piotrek@gmail.com")
//                    .enabled(true)
//                    .role("ROLE_MANAGER")
//                    .build();
//            userRepository.save(user);
//        }
//        if (Objects.isNull(userRepository.findByUsername("solejuk"))){
//            User user = User.builder()
//                    .username("solejuk")
//                    .password(bCryptPasswordEncoder.encode("solejuk"))
//                    .email("solejuk@gmail.com")
//                    .enabled(true)
//                    .role("ROLE_USER")
//                    .build();
//            userRepository.save(user);
//        }

//        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("secret");
//        System.out.println("HASLO japycz : "+standardPasswordEncoder.encode("japycz"));
//        System.out.println("HASLO pietrek : "+standardPasswordEncoder.encode("pietrek"));



//        Personnel personnel = Personnel.builder()
//                .firstName("Lukasz")
//                .lastName("Lukasz")
//                .hireDate(LocalDate.parse("2000-11-11"))
//                .position("Lezaca ustalona")
//                .salary(1000.0)
//                .sickLeave(true)
//                .build();
//        log.info("Nowy pracownik dodany do bazy danej {}", oldPersonnelRepository.create(personnel).toString());
//
//        Customer customer = Customer.builder()
//                .firstName(hotelCustomerConfig.getFirstName())
//                .lastName(hotelCustomerConfig.getLastName())
//                .startOfBooking(LocalDate.parse(hotelCustomerConfig.getStartOfBooking()))
//                .endOfBooking(LocalDate.parse(hotelCustomerConfig.getEndOfBooking()))
//                .roomNumber(hotelCustomerConfig.getRoomNumber())
//                .build();
//
//        customerService.createNewCustomer(customer);
//        log.info("Nowy pracownik dodany do bazy danych -> "+customer.toString() );
    }

}
