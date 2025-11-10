package com.example.uade.tpo.FelsaniMotors.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemReportDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String problemType;
    private String description;
    private Integer photoCount;  // Número de fotos subidas
    private LocalDateTime createdAt;
}