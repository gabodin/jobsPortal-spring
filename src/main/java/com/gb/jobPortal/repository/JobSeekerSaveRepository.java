package com.gb.jobPortal.repository;

import com.gb.jobPortal.entity.JobPostActivity;
import com.gb.jobPortal.entity.JobSeekerSave;
import com.gb.jobPortal.entity.JobSeekerProfile;
import com.gb.jobPortal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
