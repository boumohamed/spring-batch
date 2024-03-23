package me.bouzri.firstbatch.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    private String industry;
    private String industryCode;
    private String industryName;
    private String units;
    private String variableCode;
    private String variableName;
    private String variableCategory;
    private String value;
    private String industryCodeAn;
}
