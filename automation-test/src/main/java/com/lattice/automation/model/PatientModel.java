package com.lattice.automation.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PatientModel {
    private int id;
    private String name;
    private String city;
    private String email;
    private long phone;
    private String symptom;
}
