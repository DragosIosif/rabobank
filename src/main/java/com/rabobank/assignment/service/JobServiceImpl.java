package com.rabobank.assignment.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default implementation of JobService
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Override
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = createDefaultJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    private JobParameters createDefaultJobParameters() {
        //add timestamp parameter to make sure the job will run every time
        return new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters();
    }

}
