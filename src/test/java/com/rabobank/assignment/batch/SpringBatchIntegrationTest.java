package com.rabobank.assignment.batch;

import com.rabobank.assignment.batch.processor.CompositeProcessor;
import com.rabobank.assignment.batch.reader.CsvReaderConfiguration;
import com.rabobank.assignment.batch.reader.XmlReaderConfiguration;
import com.rabobank.assignment.batch.writer.FailedTransactionWriterConfiguration;
import com.rabobank.assignment.model.FailedTransaction;
import com.rabobank.assignment.service.FailedTransactionService;
import com.rabobank.assignment.service.FailedTransactionServiceImpl;
import com.rabobank.assignment.service.StorageService;
import com.rabobank.assignment.service.StorageServiceImpl;
import com.rabobank.assignment.model.StorageProperties;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { BatchConfiguration.class, CompositeProcessor.class, CsvReaderConfiguration.class, XmlReaderConfiguration.class, FailedTransactionWriterConfiguration.class, FailedTransactionServiceImpl.class, StorageServiceImpl.class, StorageProperties.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SpringBatchIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private FailedTransactionService failedTransactionService;

    @Autowired
    private StorageService storageService;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
        storageService.deleteAll();
    }

    @Before
    public void setUp() throws Exception {
        storageService.copyFile(new File(getClass().getResource("/records.csv").toURI()));
        storageService.copyFile(new File(getClass().getResource("/records.xml").toURI()));
    }

    @Test
    public void givenExpectedOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        final FailedTransaction[] expectedFailedTransactions = getExpectedFailedTransactions();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());

        // then
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        Assert.assertEquals("validateStatementsJob", actualJobInstance.getJobName());

        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        Assert.assertEquals(actualJobExitStatus.getExitCode(), "COMPLETED");

        final List<FailedTransaction> actual = failedTransactionService.findAll();
        Assert.assertEquals(10, actual.size());
        Assert.assertThat(actual, CoreMatchers.hasItems(expectedFailedTransactions));
    }

    @Test
    public void givenExpectedOutput_whenJobExecuted_thenSuccess2() throws Exception {
        // given
        final FailedTransaction[] expectedFailedTransactions = getExpectedFailedTransactions();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());

        // then
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        Assert.assertEquals("validateStatementsJob", actualJobInstance.getJobName());

        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        Assert.assertEquals(actualJobExitStatus.getExitCode(), "COMPLETED");

        final List<FailedTransaction> actual = failedTransactionService.findAll();
        Assert.assertEquals(10, actual.size());
        Assert.assertThat(actual, CoreMatchers.hasItems(expectedFailedTransactions));
    }

    private FailedTransaction[] getExpectedFailedTransactions(){
        return new FailedTransaction[] {
                new FailedTransaction(132843L, "Invalid Balance"),
                new FailedTransaction(107934L, "Invalid Balance"),
                new FailedTransaction(112806L, "Invalid Balance; Invalid reference"),
                new FailedTransaction(112806L, "Invalid reference"),
                new FailedTransaction(191476L, "Invalid Balance"),
                new FailedTransaction(132843L, "Invalid Balance"),
                new FailedTransaction(107934L, "Invalid Balance"),
                new FailedTransaction(112806L, "Invalid Balance; Invalid reference"),
                new FailedTransaction(112806L, "Invalid reference"),
                new FailedTransaction(191476L, "Invalid Balance"),
        };
    }
}