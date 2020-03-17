package com.rabobank.assignment.controller;

import com.rabobank.assignment.service.FailedTransactionService;
import com.rabobank.assignment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for job-related operations
 */
@Controller
public class JobController {
    private static final String UPLOAD_FORM = "uploadForm";

    @Autowired
    private JobService jobService;

    @Autowired
    private FailedTransactionService failedTransactionService;

    @RequestMapping("/launchJob")
    public String launchJob(Model model) throws Exception {
        jobService.runJob();
        model.addAttribute("failedTransactions", failedTransactionService.findAll());
        return UPLOAD_FORM;
    }

    @RequestMapping("/deleteRecords")
    public String deleteAll(){
        failedTransactionService.deleteAll();
        return UPLOAD_FORM;
    }
}