package org.example.app1.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MaintenanceDTO {

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long idVehicule;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String description;
}