package org.example.app1.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.app1.domain.Maintenance;
import org.example.app1.service.IMaintenanceService;
import org.example.app1.web.dto.MaintenanceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @GetMapping
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    @GetMapping("/{id}")
    public Maintenance findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @PutMapping("/{id}")
    public Maintenance update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }

    // === ATELIER 6 : Route pour l'affectation ===
    @PostMapping("/affecter")
    public Maintenance affecter(@Valid @RequestBody MaintenanceDTO dto) {
        return maintenanceService.affecterMaintenance(dto);
    }
}