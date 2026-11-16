package org.example.app1.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.app1.domain.Vehicule;
import org.example.app1.service.IVehiculeService;
import org.example.app1.web.dto.EquipementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    // === ATELIER 6 : Route pour l'affectation ===
    @PostMapping("/affecter-equipement")
    public Vehicule affecterEquipement(@Valid @RequestBody EquipementDTO dto) {
        return vehiculeService.affecterEquipement(dto);
    }
}