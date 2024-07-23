package com.gb.jobPortal.controller;

import com.gb.jobPortal.entity.JobSeekerProfile;
import com.gb.jobPortal.entity.RecruiterProfile;
import com.gb.jobPortal.entity.Skills;
import com.gb.jobPortal.entity.Users;
import com.gb.jobPortal.repository.UsersRepository;
import com.gb.jobPortal.services.JobSeekerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private JobSeekerProfileService jobSeekerProfileService;

    private UsersRepository usersRepository;

    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String jobSeekerProfileForm(Model model) {
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException("User not found: " + username));
            Optional<JobSeekerProfile> seeker = jobSeekerProfileService.getOne(
                    user.getUserId());

            if (seeker.isPresent()) {
                jobSeekerProfile = seeker.get();
                if (jobSeekerProfile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }

            model.addAttribute("profile", jobSeekerProfile);
            model.addAttribute("skills", skills);
        }


        return "job-seeker-profile";
    }

    @GetMapping("/addNew")
    public String addJobSeekerProfile(JobSeekerProfile jobSeekerProfile,
                                      @RequestParam("image") MultipartFile file,
                                      @RequestParam("pdf") MultipartFile pdf,
                                      Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException("User not found " + username));
            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }

        model.addAttribute("profile", jobSeekerProfile);


        return "redirect:/dashboard/";
    }
}
