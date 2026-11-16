package org.example.app1.service;

import org.example.app1.domain.Vehicule;
import org.example.app1.web.dto.EquipementDTO;

import java.util.List;

public interface IVehiculeService {

    // Méthodes CRUD classiques
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);

    // Nouvelle méthode pour l'affectation d'un équipement
    Vehicule affecterEquipement(EquipementDTO dto);
}