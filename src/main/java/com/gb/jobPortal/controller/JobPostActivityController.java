package com.gb.jobPortal.controller;

import com.gb.jobPortal.entity.JobPostActivity;
import com.gb.jobPortal.entity.RecruiterJobsDto;
import com.gb.jobPortal.entity.RecruiterProfile;
import com.gb.jobPortal.entity.Users;
import com.gb.jobPortal.services.JobPostActivityService;
import com.gb.jobPortal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = (RecruiterProfile) currentUserProfile;
                List<RecruiterJobsDto> recruiterJobsDtoList = jobPostActivityService.getRecruiterJobs(
                        recruiterProfile.getUserAccountId());
                System.out.println(recruiterJobsDtoList);
                model.addAttribute("jobPost", recruiterJobsDtoList);
            }
        }

        model.addAttribute("user", currentUserProfile);

        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobForm(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNewJob(Model model, JobPostActivity jobPostActivity) {
        Users user = usersService.getCurrentUser();

        if(user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        model.addAttribute("jobPostActivity", jobPostActivity);

        JobPostActivity savedJob = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }

    @GetMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.findById(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "add-jobs";
    }

}
