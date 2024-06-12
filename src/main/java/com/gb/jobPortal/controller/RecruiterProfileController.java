package com.gb.jobPortal.controller;

import com.gb.jobPortal.entity.RecruiterProfile;
import com.gb.jobPortal.entity.Users;
import com.gb.jobPortal.repository.UsersRepository;
import com.gb.jobPortal.services.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();

            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() ->
                    new UsernameNotFoundException("User not found " + currentUsername));

            Optional<RecruiterProfile> recruiterProfile =  recruiterProfileService.getOne(user.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }

        return "recruiter-profile";
    }


    public String addNew(RecruiterProfile recruiterProfile,
                         @RequestParam("image") MultipartFile file,
                         Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() ->
                    new UsernameNotFoundException("User not found " + currentUsername));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());

        }
        model.addAttribute("profile", recruiterProfile);
        String filename = "";
        if(!(Objects.equals(file.getOriginalFilename(), ""))) {
            filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(filename);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();

        return null;
    }
}
