package org.springproject.springproject.util.service;

public interface DatabaseFillingService {

    void fillWithUsers(Integer numberOfUsers);

    void fillWithUsersWithCustomerAccount(Integer numberOfUsers);

    void fillWithEmployees(Integer numberOfEmployees);

    void fillWithRooms(Integer numberOfRooms);

}
