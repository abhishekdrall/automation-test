package com.lattice.automation.model;

import lombok.Data;

import java.util.List;

@Data
public class TestReport {
    private String moduleName;
    private List<TestCaseResult> testCaseResults;
}
