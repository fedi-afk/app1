package org.example.app1.service;

import lombok.RequiredArgsConstructor;
import org.example.app1.domain.Maintenance;
import org.example.app1.domain.Vehicule;
import org.example.app1.exception.ResourceNotFoundException;
import org.example.app1.repository.IMaintenanceRepository;
import org.example.app1.repository.IVehiculeRepository;
import org.example.app1.web.dto.MaintenanceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existant = findById(id);
        existant.setDateDebut(maintenance.getDateDebut());
        existant.setDateFin(maintenance.getDateFin());
        existant.setDescription(maintenance.getDescription());
        return maintenanceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    @Override
    public Maintenance affecterMaintenance(MaintenanceDTO dto) {
        // 1. On cherche le véhicule
        Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));

        // 2. On crée la maintenance
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(dto.getDateDebut());
        maintenance.setDateFin(dto.getDateFin());
        maintenance.setDescription(dto.getDescription());
        maintenance.setVehicule(vehicule);

        // 3. On sauvegarde
        return maintenanceRepository.save(maintenance);
    }
}