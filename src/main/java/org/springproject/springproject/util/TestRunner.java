package org.springproject.springproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springproject.springproject.config.HotelCustomerConfig;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.repository.OldPersonnelRepository;
import org.springproject.springproject.repository.PersonnelRepository;
import org.springproject.springproject.service.CustomerService;

import java.time.LocalDate;

@Component
@Slf4j
@Profile("dev-test")
public class TestRunner implements CommandLineRunner {

    private final OldPersonnelRepository oldPersonnelRepository;
    private final HotelCustomerConfig hotelCustomerConfig;
    private final CustomerService customerService;

    public TestRunner(OldPersonnelRepository oldPersonnelRepository, HotelCustomerConfig hotelCustomerConfig, CustomerService customerService) {
        this.oldPersonnelRepository = oldPersonnelRepository;
        this.hotelCustomerConfig = hotelCustomerConfig;
        this.customerService = customerService;
    }


    @Override
    public void run(String... args) throws Exception {

        Personnel personnel = Personnel.builder()
                .firstName("Lukasz")
                .lastName("Lukasz")
                .hireDate(LocalDate.parse("2000-11-11"))
                .position("Lezaca ustalona")
                .salary(1000.0)
                .sickLeave(true)
                .build();
        log.info("Nowy pracownik dodany do bazy danej {}", oldPersonnelRepository.create(personnel).toString());

        Customer customer = Customer.builder()
                .firstName(hotelCustomerConfig.getFirstName())
                .lastName(hotelCustomerConfig.getLastName())
                .startOfBooking(LocalDate.parse(hotelCustomerConfig.getStartOfBooking()))
                .endOfBooking(LocalDate.parse(hotelCustomerConfig.getEndOfBooking()))
                .roomNumber(hotelCustomerConfig.getRoomNumber())
                .build();

        customerService.createNewCustomer(customer);
        log.info("Nowy pracownik dodany do bazy danych -> "+customer.toString() );
    }

}
