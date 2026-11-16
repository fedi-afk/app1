package org.example.app1.web.dto;

import jakarta.validation.constraints.NotNull;

public class EquipementDTO {

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long idVehicule;

    @NotNull(message = "L'ID de l'équipement est obligatoire")
    private Long idEquipement;

    // Getters et Setters
    public Long getIdVehicule() { return idVehicule; }
    public void setIdVehicule(Long idVehicule) { this.idVehicule = idVehicule; }
    public Long getIdEquipement() { return idEquipement; }
    public void setIdEquipement(Long idEquipement) { this.idEquipement = idEquipement; }
}