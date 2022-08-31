package com.lattice.automation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPAddressBlocker {
    private int hitsCounter;
    private long startTimeInMs;
}
