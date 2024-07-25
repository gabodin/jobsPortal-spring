package com.gb.jobPortal.repository;

import com.gb.jobPortal.entity.JobPostActivity;
import com.gb.jobPortal.entity.JobSeekerApply;
import com.gb.jobPortal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}
