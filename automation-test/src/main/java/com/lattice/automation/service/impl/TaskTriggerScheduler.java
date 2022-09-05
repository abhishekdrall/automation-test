package com.lattice.automation.service.impl;

import com.lattice.automation.model.ProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class TaskTriggerScheduler{

    private Map<String,ProcessStatus> processStatusMap;
    public  TaskTriggerScheduler(Map<String,ProcessStatus> processStatusMap){
        this.processStatusMap=processStatusMap;
    }

    public ResponseEntity<?> testAllAPis() {
        // run the testing code server has started
        log.info("Server has started- ");
        processStatusMap.values().stream().forEach(processStatus -> System.out.println("PID-"+processStatus.getProcess().pid()));
        log.info("killing processes");
//        processStatusMap.values().stream().forEach(processStatus -> processStatus.getProcess().destroyForcibly());
        log.info("Process killed");
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
