package org.springproject.springproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springproject.springproject.model.Personnel;

import java.time.LocalDate;

@Controller
@ResponseBody
public class TestController {

    @RequestMapping(method = RequestMethod.GET, path = "/api/hello/{name}/{surname}/{age}")
    public ResponseEntity<String> sayHello(@PathVariable String name, @PathVariable String surname, @PathVariable Integer age){
        return ResponseEntity.ok("Hello "+ name+" "+surname+" wiek: "+age);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/api/hello2")
    public String sayHello2(){
        return "Hello2";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/hello3")
    public Personnel sayHello3(){
        return new Personnel(100L,"Jacek","Warym","Parkingowy", LocalDate.parse("2020-11-15"),3322.20,false);
    }
}
