package com.lattice.automation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestCaseResult {
    private String testCaseName;
    private String status;
}
