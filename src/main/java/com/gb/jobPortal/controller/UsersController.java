package com.gb.jobPortal.controller;

import com.gb.jobPortal.entity.Users;
import com.gb.jobPortal.entity.UsersType;
import com.gb.jobPortal.repository.UsersTypeRepository;
import com.gb.jobPortal.services.UsersTypeService;
import jakarta.servlet.http.PushBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService) {
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.findAll();

        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());

        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users) {
        System.out.println("User::" + users);
        return "dashboard";
    }

}
