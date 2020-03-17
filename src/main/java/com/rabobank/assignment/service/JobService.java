package com.rabobank.assignment.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * Service for controlling jobs
 */
public interface JobService {

    /**
     * Run the default job
     * @throws JobParametersInvalidException if the parameters are invalid
     * @throws JobExecutionAlreadyRunningException if the job is currently running
     * @throws JobRestartException if a restart is attempted but the job is not restartable
     * @throws JobInstanceAlreadyCompleteException if the same job with the same parameters is already complete
     */
    void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;
}
