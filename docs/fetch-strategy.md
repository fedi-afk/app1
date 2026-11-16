# Stratégie de Fetch et de Cascade - AutoLoc

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat. Supprimer le contrat supprime ses paiements (composition). L'orphanRemoval nettoie les paiements retirés de la liste. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule peut être transféré ou survivre à la fermeture d'une agence. Pas de suppression en cascade. |
| Agence → Employe | LAZY | Aucune | Un employé existe indépendamment de l'agence. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements (GPS, siège bébé) sont partagés entre plusieurs véhicules. Utilisation d'un `Set` pour éviter les doublons. |
| Client → Reservation | LAZY | PERSIST | Sauvegarder un client avec ses nouvelles réservations doit les sauvegarder en cascade. Pas de REMOVE pour garder l'historique. |
| Reservation → Vehicule | LAZY | Aucune | Le véhicule existe indépendamment de la réservation. |
| Reservation ↔ Contrat | LAZY | ALL (côté Reservation) | Le contrat découle de la réservation. Il suit son cycle de vie. |
| Vehicule → Maintenance | LAZY | PERSIST | Une maintenance est liée à un véhicule, mais on ne supprime pas les maintenances si on supprime le véhicule. |