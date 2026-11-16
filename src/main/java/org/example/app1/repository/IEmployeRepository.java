package org.example.app1.repository;

import org.example.app1.domain.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}