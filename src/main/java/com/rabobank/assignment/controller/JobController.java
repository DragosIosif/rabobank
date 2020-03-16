package com.rabobank.assignment.controller;

import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/launchJob")
    public String launchJob(Model model) throws Exception {
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job, jobParameters);
        final List<FailedTransaction> failedTransactions = jdbcTemplate.query("SELECT * FROM FailedTransaction",
                (rs, row) -> new FailedTransaction(
                        rs.getLong(1),
                        rs.getString(2))
        );
        model.addAttribute("failedTransactions", failedTransactions);
        return "uploadForm";
    }
}