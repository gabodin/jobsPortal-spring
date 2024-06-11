package com.gb.jobPortal.services;

import com.gb.jobPortal.entity.JobSeekerProfile;
import com.gb.jobPortal.entity.RecruiterProfile;
import com.gb.jobPortal.entity.Users;
import com.gb.jobPortal.repository.JobSeekerProfileRepository;
import com.gb.jobPortal.repository.RecruiterProfileRepository;
import com.gb.jobPortal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository seekerProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = seekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Users addUser(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser = usersRepository.save(user);

        int userTypeId = savedUser.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }
}