package com.gb.jobPortal.services;

import com.gb.jobPortal.entity.RecruiterProfile;
import com.gb.jobPortal.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer recruiterProfileId) {
        return recruiterProfileRepository.findById(recruiterProfileId);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return  recruiterProfileRepository.save(recruiterProfile);
    }
}
