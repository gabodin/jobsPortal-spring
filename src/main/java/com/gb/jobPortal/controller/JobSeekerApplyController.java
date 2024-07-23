package com.gb.jobPortal.controller;

import com.gb.jobPortal.entity.JobPostActivity;
import com.gb.jobPortal.services.JobPostActivityService;
import com.gb.jobPortal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;

    @Autowired
    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService usersService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
    }

    @GetMapping("/job-details-apply/{id}")
    public String displayJob(@PathVariable("id") int id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.findById(id);

        String formattedDate = jobDetails.getPostedDate().withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "job-details";
    }

}
