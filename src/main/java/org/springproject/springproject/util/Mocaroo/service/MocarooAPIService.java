package org.springproject.springproject.util.Mocaroo.service;

import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;

import java.util.List;

public interface MocarooAPIService {

    List<User> getUsersWithCustomerAccount(Integer numberOfUsers);

    List<User> getUsers(Integer numberOfUsers);

    List<Room> getRooms(Integer numberOfRooms);

    List<Employee> getEmployees(Integer numberOfEmployees);
}
