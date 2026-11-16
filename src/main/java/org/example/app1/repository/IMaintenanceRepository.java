package org.example.app1.repository;

import org.example.app1.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
}