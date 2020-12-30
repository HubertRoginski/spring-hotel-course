package org.springproject.springproject.util.Mocaroo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class MocarooApiServiceImpl implements MocarooAPIService{

    private final RestTemplate REST_TEMPLATE;
    @Value("${mocaroo.services.api-key}")
    private String mocarooApiKey;

    public MocarooApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        REST_TEMPLATE = restTemplateBuilder.build();
    }

    @Override
    public List<User> getUsersWithCustomerAccount(Integer numberOfUsers) {
        if (numberOfUsers<0){
            numberOfUsers=0;
        }
        String url="https://api.mockaroo.com/api/generate.json?schema=ZozaHotel-userWithCustomer&key="+mocarooApiKey+"&count="+numberOfUsers;
        return getUserList(url);
    }

    @Override
    public List<User> getUsers(Integer numberOfUsers) {
        if (numberOfUsers<0){
            numberOfUsers=0;
        }
        String url="https://api.mockaroo.com/api/generate.json?schema=ZozaHotel-users&key="+mocarooApiKey+"&count="+numberOfUsers;
        return getUserList(url);
    }

    private List<User> getUserList(String url) {
        String template = this.REST_TEMPLATE.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> userList = new ArrayList<>();
        try {
            userList = objectMapper.readValue(template, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<Room> getRooms(Integer numberOfRooms) {
        if (numberOfRooms<0){
            numberOfRooms=0;
        }
        String url="https://api.mockaroo.com/api/generate.json?schema=ZozaHotel-rooms&key="+mocarooApiKey+"&count="+numberOfRooms;
        String template = this.REST_TEMPLATE.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Room> roomList = new ArrayList<>();
        try {
            roomList = objectMapper.readValue(template, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    @Override
    public List<Employee> getEmployees(Integer numberOfEmployees) {
        if (numberOfEmployees<0){
            numberOfEmployees=0;
        }
        String url="https://api.mockaroo.com/api/generate.json?schema=ZozaHotel-employees&key="+mocarooApiKey+"&count="+numberOfEmployees;
        String template = this.REST_TEMPLATE.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employeeList = new ArrayList<>();
        try {
            employeeList = objectMapper.readValue(template, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
