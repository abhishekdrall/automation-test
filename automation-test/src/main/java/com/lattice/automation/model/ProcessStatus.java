package com.lattice.automation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessStatus {
    private Process process;
    private boolean isWantToTerminate;
}
