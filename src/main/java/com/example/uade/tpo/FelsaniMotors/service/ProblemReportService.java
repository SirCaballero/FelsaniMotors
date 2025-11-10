package com.example.uade.tpo.FelsaniMotors.service;

import com.example.uade.tpo.FelsaniMotors.dto.CreateProblemReportDTO;
import com.example.uade.tpo.FelsaniMotors.dto.ProblemReportDTO;
import com.example.uade.tpo.FelsaniMotors.entity.ProblemReport;
import com.example.uade.tpo.FelsaniMotors.entity.Usuario;
import com.example.uade.tpo.FelsaniMotors.repository.ProblemReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemReportService {

    private final ProblemReportRepository repository;

    public ProblemReportDTO createReport(CreateProblemReportDTO dto) throws IOException, SQLException {
        // Obtener usuario autenticado
        Usuario currentUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Validación adicional: Al menos una foto
        if (dto.getPhotos() == null || dto.getPhotos().length == 0) {
            throw new IllegalArgumentException("Debe subir al menos una foto.");
        }

        // Procesar fotos: Convertir MultipartFile a Blob
        List<Blob> photos = new ArrayList<>();
        for (MultipartFile file : dto.getPhotos()) {
            if (!file.isEmpty()) {
                Blob blob = new SerialBlob(file.getBytes());  // Convertir a Blob
                photos.add(blob);
            }
        }

        // Crear entidad ProblemReport
        ProblemReport report = ProblemReport.builder()
            .usuario(currentUser)
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .problemType(dto.getProblemType())
            .description(dto.getDescription())
            .photos(photos)
            .build();

        // Guardar
        ProblemReport saved = repository.save(report);

        // Retornar DTO
        return ProblemReportDTO.builder()
            .id(saved.getId())
            .firstName(saved.getFirstName())
            .lastName(saved.getLastName())
            .problemType(saved.getProblemType())
            .description(saved.getDescription())
            .photoCount(saved.getPhotos().size())
            .createdAt(saved.getCreatedAt())
            .build();
    }
}