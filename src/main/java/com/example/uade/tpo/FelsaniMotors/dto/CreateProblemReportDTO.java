package com.example.uade.tpo.FelsaniMotors.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemReportDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "La problemática es obligatoria")
    private String problemType;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "Debe subir al menos una foto")
    private MultipartFile[] photos;  // Para múltiples archivos
}