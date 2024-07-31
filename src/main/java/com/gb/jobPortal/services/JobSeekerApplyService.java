package com.gb.jobPortal.services;

import com.gb.jobPortal.entity.JobPostActivity;
import com.gb.jobPortal.entity.JobSeekerApply;
import com.gb.jobPortal.entity.JobSeekerProfile;
import com.gb.jobPortal.repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidateApplies(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

    public JobSeekerApply addNew(JobSeekerApply jobSeekerApply) {
        return jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
