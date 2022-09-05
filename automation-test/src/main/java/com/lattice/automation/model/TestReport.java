package com.lattice.automation.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TestReport {
    private String moduleName;
    private List<TestCaseResult> testCaseResults;
}
