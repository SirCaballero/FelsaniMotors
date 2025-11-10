package com.example.uade.tpo.FelsaniMotors.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "problem_reports")
public class ProblemReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario (usuario autenticado obligatorio)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder 50 caracteres")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "La problemática es obligatoria")
    @Column(name = "problem_type", nullable = false)
    private String problemType;  // Podrías cambiar a un enum si tienes tipos fijos

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    // Lista de fotos como Blobs (inspirado en Foto, pero sin entidad separada)
    @ElementCollection
    @CollectionTable(name = "problem_report_photos", joinColumns = @JoinColumn(name = "problem_report_id"))
    @Column(name = "photo_blob", columnDefinition = "LONGBLOB")  // Para almacenar Blobs grandes
    private List<Blob> photos = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}