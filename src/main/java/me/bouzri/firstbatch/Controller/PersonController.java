package me.bouzri.firstbatch.Controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonController {
    private final JobLauncher launcher;
    private final Job job;
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/launch")
    public ResponseEntity<String> launch(){

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        try {
            launcher.run(job, jobParameters);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            //e.printStackTrace();
            log.debug(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
