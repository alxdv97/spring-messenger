package com.example.sweater.controllers;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String registration() {
        return "registration";
    }

    @PostMapping()
    public String addUser(User user, Map<String, Object> model) {
        User userFromDB = userRepository.findByUserName(user.getUserName());
        if (userFromDB != null) {
            model.put("message", "User exist!");
            return "registration";
        }
        user.setActivate(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
