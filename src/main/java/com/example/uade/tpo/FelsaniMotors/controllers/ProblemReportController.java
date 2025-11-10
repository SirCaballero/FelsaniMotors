package com.example.uade.tpo.FelsaniMotors.controllers;

import com.example.uade.tpo.FelsaniMotors.dto.CreateProblemReportDTO;
import com.example.uade.tpo.FelsaniMotors.dto.ProblemReportDTO;
import com.example.uade.tpo.FelsaniMotors.entity.ProblemReport;
import com.example.uade.tpo.FelsaniMotors.service.ProblemReportService;
import com.example.uade.tpo.FelsaniMotors.repository.ProblemReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/problem-reports")
@RequiredArgsConstructor
public class ProblemReportController {

    private final ProblemReportService service;
    private final ProblemReportRepository repository;  // Agregado para acceder directamente a la entidad en los GET

    // POST para crear reporte (ya existente)
    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createReport(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("problemType") String problemType,
            @RequestParam("description") String description,
            @RequestParam("photos") MultipartFile[] photos
    ) throws IOException, SQLException {
        CreateProblemReportDTO dto = CreateProblemReportDTO.builder()
            .firstName(firstName)
            .lastName(lastName)
            .problemType(problemType)
            .description(description)
            .photos(photos)
            .build();

        ProblemReportDTO result = service.createReport(dto);
        return ResponseEntity.ok(Map.of("message", "Problema registrado correctamente", "report", result));
    }

    // GET para obtener un reporte por ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProblemReportDTO> getReport(@PathVariable Long id) {
        Optional<ProblemReport> reportOpt = repository.findById(id);
        if (reportOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProblemReport report = reportOpt.get();
        ProblemReportDTO dto = ProblemReportDTO.builder()
            .id(report.getId())
            .firstName(report.getFirstName())
            .lastName(report.getLastName())
            .problemType(report.getProblemType())
            .description(report.getDescription())
            .photoCount(report.getPhotos().size())
            .createdAt(report.getCreatedAt())
            .build();
        return ResponseEntity.ok(dto);
    }

    // GET para obtener una foto específica del reporte
    @GetMapping("/{id}/photos/{index}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id, @PathVariable int index) throws SQLException {
        Optional<ProblemReport> reportOpt = repository.findById(id);
        if (reportOpt.isEmpty() || index < 0 || index >= reportOpt.get().getPhotos().size()) {
            return ResponseEntity.notFound().build();
        }
        Blob photoBlob = reportOpt.get().getPhotos().get(index);
        byte[] photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());  // Convertir Blob a byte[]
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);  // Ajusta si tus fotos no son JPEG (e.g., PNG)
        return ResponseEntity.ok().headers(headers).body(photoBytes);
    }
}