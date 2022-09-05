package com.lattice.automation.service.impl;

import com.lattice.automation.constants.EndPoints;
import com.lattice.automation.model.PatientModel;
import com.lattice.automation.model.TestCaseResult;
import com.lattice.automation.model.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientTest {

    private List<TestReport> testReports;

    @Autowired
    private RestTemplate restTemplate;

    private List<TestCaseResult> testCaseResults;

    public PatientTest(List<TestReport> testReports) {
        this.testReports = testReports;
    }

    private void test1(){
        // name,city,email,phone,speciality,symptom
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("Abc")
                .email("abc@gmail.com")
                .phone(1234567891L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_DOCTOR_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.OK)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Successful test case").status("Success").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Successful test case").status("Failed").build());
    }

    private void test2(){
        // name,city,email,phone,speciality,symptom
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("AbD")
                .email("abc@gmail.com")
                .phone(1234567891L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_DOCTOR_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.CONFLICT)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient email uniqueness test case").status("Success").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient email uniqueness test case").status("Failed").build());
    }
}
