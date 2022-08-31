package com.lattice.automation.controller;

import com.lattice.automation.exception.GenericException;
import com.lattice.automation.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/project")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    @PostMapping(value = "/automate",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadZipProjectFile(@RequestParam("file") final MultipartFile multipartFile) throws IOException, GenericException {
        return automationService.automateProjectFile(multipartFile);
    }
}
