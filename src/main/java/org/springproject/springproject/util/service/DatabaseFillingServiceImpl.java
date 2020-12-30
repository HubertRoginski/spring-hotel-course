package org.springproject.springproject.util.service;

import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.EmployeesService;
import org.springproject.springproject.service.RoomService;
import org.springproject.springproject.service.UserCustomerService;
import org.springproject.springproject.service.UserService;
import org.springproject.springproject.util.Mocaroo.service.MocarooAPIService;

import java.util.List;

@Service
public class DatabaseFillingServiceImpl implements DatabaseFillingService{

    private final UserService userService;
    private final UserCustomerService userCustomerService;
    private final RoomService roomService;
    private final EmployeesService employeesService;
    private final MocarooAPIService mocarooAPIService;

    public DatabaseFillingServiceImpl(UserService userService, UserCustomerService userCustomerService, RoomService roomService, EmployeesService employeesService, MocarooAPIService mocarooAPIService) {
        this.userService = userService;
        this.userCustomerService = userCustomerService;
        this.roomService = roomService;
        this.employeesService = employeesService;
        this.mocarooAPIService = mocarooAPIService;
    }

    @Override
    public void fillWithUsers(Integer numberOfUsers) {
        List<User> users = mocarooAPIService.getUsers(numberOfUsers);
        userService.createBatchOfUsers(users);
    }

    @Override
    public void fillWithUsersWithCustomerAccount(Integer numberOfUsers) {
        List<User> usersWithCustomerAccount = mocarooAPIService.getUsersWithCustomerAccount(numberOfUsers);
        userCustomerService.createBatchOfUsersWithCustomerAccount(usersWithCustomerAccount);
    }

    @Override
    public void fillWithEmployees(Integer numberOfEmployees) {
        List<Employee> employeeList = mocarooAPIService.getEmployees(numberOfEmployees);
        employeesService.createBatchOfEmployees(employeeList);
    }

    @Override
    public void fillWithRooms(Integer numberOfRooms) {
        List<Room> roomList = mocarooAPIService.getRooms(numberOfRooms);
        roomService.addBatchOfRooms(roomList);
    }
}
