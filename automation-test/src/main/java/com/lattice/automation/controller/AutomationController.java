package com.lattice.automation.controller;

import com.lattice.automation.exception.GenericException;
import com.lattice.automation.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class AutomationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AutomationService automationService;

    @PostMapping(value = "/automate",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadZipProjectFile(@RequestParam("file") final MultipartFile multipartFile) throws IOException, GenericException, InterruptedException, SQLException {
        return automationService.automateProjectFile(multipartFile);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> test() throws IOException, GenericException, InterruptedException {
        Map<String,Object> map=new HashMap<>();
        map.put("data","Shiv");
        map.put("secretKey","Mohan");
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String,Object>> httpEntity=new HttpEntity<Map<String,Object>>(map,httpHeaders);
        var response= restTemplate.exchange("http://134.209.154.238:8090/trf/security/encrypt/raw/data", HttpMethod.POST,httpEntity,String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        return response;
    }
}
