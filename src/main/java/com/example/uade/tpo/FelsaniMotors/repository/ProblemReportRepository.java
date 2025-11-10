package com.example.uade.tpo.FelsaniMotors.repository;

import com.example.uade.tpo.FelsaniMotors.entity.ProblemReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemReportRepository extends JpaRepository<ProblemReport, Long> {
    // Métodos personalizados si necesitas, e.g., buscar por usuario
    // List<ProblemReport> findByUsuario(Usuario usuario);
} 