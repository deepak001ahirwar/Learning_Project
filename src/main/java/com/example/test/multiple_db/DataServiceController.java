package com.example.test.multiple_db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataServiceController {


    @Autowired
    DataService dataService;


    @GetMapping("/saveUser")
    public String saveUserWithAudit() {

        dataService.setUserANDLogAudit();

        return "User Saved Sucessfully ";
    }


}
