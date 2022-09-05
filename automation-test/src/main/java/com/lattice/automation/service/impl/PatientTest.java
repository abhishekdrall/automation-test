package com.lattice.automation.service.impl;

import com.lattice.automation.constants.EndPoints;
import com.lattice.automation.model.PatientModel;
import com.lattice.automation.model.TestCaseResult;
import com.lattice.automation.model.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PatientTest {

    private List<TestReport> testReports;

    @Autowired
    private RestTemplate restTemplate;

    private List<TestCaseResult> testCaseResults;

    public PatientTest(List<TestReport> testReports) {
        this.testReports = testReports;
        this.testCaseResults=testReports.get(0).getTestCaseResults();
    }

    public ResponseEntity<?> getTestCaseReport(){
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
        test13();
        return ResponseEntity.status(HttpStatus.OK).body(testReports);
    }

    /**
     * Patient record successfully inserted
     */
    private void test1(){
        // name,city,email,phone,speciality,symptom
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("Abc")
                .email("abc@gmail.com")
                .phone(1234567891L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.OK)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Successful test case").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Successful test case").status("Failed").build());
    }

    /**
     * Patient email uniqueness
     */
    private void test2(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("AbD")
                .email("abc@gmail.com")
                .phone(1234567891L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.CONFLICT)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient email uniqueness test case 1").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient email uniqueness test case 1").status("Failed").build());
    }

    /**
     * Patient email validation
     */
    private void test13(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("AbD")
                .email("@gmail.com")
                .phone(1234567891L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()!= HttpStatus.OK)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient valid email test case 2").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient valid email test case 2").status("Failed").build());
    }

    /**
     * phone validation 1
     */
    private void test3(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("AbD")
                .email("abcd@gmail.com")
                .phone(123456782363L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.NOT_ACCEPTABLE)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient phone test case 1").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient phone test case 1").status("Failed").build());
    }

    /**
     * phone validation 2
     */
    private void test4(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("AbD")
                .email("abcd@gmail.com")
                .phone(12345678L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.NOT_ACCEPTABLE)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient phone test case 2").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient phone test case 2").status("Failed").build());
    }

    /**
     * name validation 1
     */
    private void test5(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("   ")
                .email("abcd@gmail.com")
                .phone(1234567832L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.NOT_ACCEPTABLE)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 1").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 1").status("Failed").build());
    }

    /**
     * name validation 2
     */
    private void test6(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("we")
                .email("abcd@gmail.com")
                .phone(1234567832L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.NOT_ACCEPTABLE)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 2").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 2").status("Failed").build());
    }

    /**
     * name validation 2
     */
    private void test7(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(PatientModel.builder().name("we")
                .email("abcd@gmail.com")
                .phone(1234567832L)
                .city("Delhi")
                .symptom("Skin infection").build(),httpHeaders);
        var response= restTemplate.exchange(EndPoints.ADD_PATIENT_API, HttpMethod.POST,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.NOT_ACCEPTABLE)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 2").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patient name test case 2").status("Failed").build());
    }

    /**
     * get patients 1
     */
    private void test8(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(null,httpHeaders);
        var response= restTemplate.exchange(EndPoints.GET_ALL_PATIENTS_API, HttpMethod.GET,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.OK)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch test case 1").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch test case 1").status("Failed").build());
    }

    /**
     * get patients 2 all
     */
    private void test9(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(null,httpHeaders);
        var response= restTemplate.exchange(EndPoints.GET_ALL_PATIENTS_API, HttpMethod.GET,httpEntity,List.class);
        if(response.getStatusCode()== HttpStatus.OK){
            response.getBody().stream().forEach(patient->{
                var pat=(PatientModel) patient;
                if(pat.getEmail().equalsIgnoreCase("abc@gmail.com")){
                    testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch test case 2").status("Passed").build());
                }
            });
        }
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch test case 2").status("Failed").build());
    }

    /**
     * get patient by id 1
     */
    private void test10(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(null,httpHeaders);
        var response= restTemplate.exchange(EndPoints.GET_ALL_PATIENTS_API, HttpMethod.GET,httpEntity,List.class);
        if(response.getStatusCode()== HttpStatus.OK){
            AtomicInteger id= new AtomicInteger();
            response.getBody().stream().forEach(patient->id.set(((PatientModel) patient).getId()));
            var response1=restTemplate.exchange(EndPoints.GET_PATIENT_API+id.get(), HttpMethod.GET,httpEntity,PatientModel.class);
            if(response1.getStatusCode()==HttpStatus.OK)
                testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 1").status("Passed").build());
            else
                testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 1").status("Failed").build());

        }
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 1").status("Failed").build());
    }


    /**
     * get patient by id 2
     */
    private void test11(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(null,httpHeaders);
        var response= restTemplate.exchange(EndPoints.GET_ALL_PATIENTS_API, HttpMethod.GET,httpEntity,List.class);
        if(response.getStatusCode()== HttpStatus.OK){
            AtomicInteger id= new AtomicInteger();
            response.getBody().stream().forEach(patient->id.set(((PatientModel) patient).getId()));
            var response1=restTemplate.exchange(EndPoints.GET_PATIENT_API+id.get(), HttpMethod.GET,httpEntity,PatientModel.class);
            if(response1.getStatusCode()==HttpStatus.OK){
                if(response1.getBody().getId()==id.get())
                    testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 2").status("Passed").build());
                else
                    testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 2").status("Failed").build());
            }
            else
                testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 2").status("Failed").build());

        }
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 2").status("Failed").build());
    }


    /**
     * get patient by id 3
     */
    private void test12(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PatientModel> httpEntity=new HttpEntity<PatientModel>(null,httpHeaders);
        var response= restTemplate.exchange(EndPoints.GET_PATIENT_API+0, HttpMethod.GET,httpEntity,PatientModel.class);
        if(response.getStatusCode()== HttpStatus.OK)
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 3").status("Passed").build());
        else
            testCaseResults.add(TestCaseResult.builder().testCaseName("Patients fetch by id test case 3").status("Failed").build());
    }

}
