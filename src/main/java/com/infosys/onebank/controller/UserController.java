package com.infosys.onebank.controller;

import com.infosys.onebank.dto.UserDTO;
import com.infosys.onebank.resource.User;
import com.infosys.onebank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.infosys.onebank.OneBankConstants.API_ROOT;
import static com.infosys.onebank.OneBankConstants.API_VERSION;

/**
 * Created by chirag.ganatra on 9/19/2018.
 */
@RestController
@RequestMapping("/"+API_ROOT+"/"+API_VERSION)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/users", produces = "application/json", consumes = "application/json")
    public ResponseEntity createUser(@RequestBody User user) {
        UserDTO userDTO = userService.createUser(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }
}
