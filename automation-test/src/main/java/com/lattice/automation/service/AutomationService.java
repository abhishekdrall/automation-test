package com.lattice.automation.service;

import com.lattice.automation.exception.GenericException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface AutomationService {
    ResponseEntity<?> automateProjectFile(final MultipartFile multipartFile) throws IOException, GenericException, InterruptedException;
    File uploadZipProjectFile(final MultipartFile multipartFile) throws GenericException, IOException;
    File unzipProjectFile(final File zipFilePath) throws IOException;
    File runProjectFile(final File destinationProjectDir) throws IOException, InterruptedException, GenericException;
}
