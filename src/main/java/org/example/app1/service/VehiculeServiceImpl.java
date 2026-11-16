package org.example.app1.service;

import lombok.RequiredArgsConstructor;
import org.example.app1.domain.Equipement;
import org.example.app1.domain.Vehicule;
import org.example.app1.exception.ResourceNotFoundException;
import org.example.app1.repository.IEquipementRepository;
import org.example.app1.repository.IVehiculeRepository;
import org.example.app1.web.dto.EquipementDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        if (vehicule.getTarifJournalier() == null || vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    // --- MÉTHODE POUR L'ATELIER 6 (Affectation d'un équipement) ---
    @Override
    public Vehicule affecterEquipement(EquipementDTO dto) {
        // 1. Vérifier que le véhicule existe
        Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));

        // 2. Vérifier que l'équipement existe
        Equipement equipement = equipementRepository.findById(dto.getIdEquipement())
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", dto.getIdEquipement()));

        // 3. Ajouter l'équipement à la liste du véhicule
        vehicule.getEquipements().add(equipement);

        // 4. Sauvegarder (le ManyToMany va insérer la ligne dans la table de jointure)
        return vehiculeRepository.save(vehicule);
    }
}