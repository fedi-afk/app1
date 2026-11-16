# Atelier 4 - Notes sur la couche Service, IoC et DI

## A. Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ final possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui | Non (cachée dans l'attribut) |
| Utilisable hors conteneur (avec new) sans dépendance null ? | Oui | Non (la dépendance sera null) |
| Signalé par SonarQube for IDE ? | Non | Oui (règle S6813) |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | Injection explicite, champ final. |
| AgenceServiceImpl | IAgenceRepository | Constructeur | Injection explicite, champ final. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Injection explicite, champ final. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Injection explicite, champ final. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Injection explicite, champ final. |
| ClientServiceImpl | IClientRepository | Constructeur | Injection explicite, champ final. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Injection explicite, champ final. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Injection explicite, champ final. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Injection explicite, champ final (lecture seule). |

## C. Messages d'erreur du conteneur

**Message 1 :** `required a bean of type 'IContratService' that could not be found`
- **Cause :** La classe `ContratServiceImpl` n'est pas annotée `@Service` OU elle se trouve en dehors du package scanné (`org.example.app1`).
- **Correction :** Ajouter l'annotation `@Service` sur la classe et vérifier son emplacement.

**Message 2 :** `required a single bean, but 2 were found`
- **Cause :** Deux beans implémentent la même interface (ex: `EmailNotificateur` et `SmsNotificateur`).
- **Correction :** Mettre `@Primary` sur l'une des classes, ou `@Qualifier("nomDuBean")` sur le paramètre du constructeur pour spécifier lequel injecter.

**Message 3 :** `dependencies... form a cycle`
- **Cause :** `ClientServiceImpl` dépend de `ReservationServiceImpl` et inversement.
- **Correction :** Revoir la conception. Extraire la responsabilité commune dans un troisième service, ou casser la dépendance.

## D. Anomalies SonarQube for IDE

| Anomalie SonarQube | Règle / explication | Correction apportée |
|---|---|---|
| Injection par attribut dans ContratServiceImpl | S6813 : L'injection par attribut cache les dépendances et empêche l'utilisation de `final`. | Remplacement par une injection par constructeur (`@RequiredArgsConstructor`). |
| Exception générique (`RuntimeException`) dans findById | S112 : Il faut lever des exceptions métier spécifiques, pas des exceptions génériques. | Utilisation de `ResourceNotFoundException`. |
| Import inutilisé dans [Nom de votre classe] | S1128 : Un import déclaré n'est pas utilisé. | Suppression de l'import inutile. |

## E. Questions de compréhension

1. **Où est le new de ContratServiceImpl et qui l'exécute ?**
   Il n'y a pas de `new` écrit par le développeur. C'est le conteneur Spring (ApplicationContext) qui instancie le bean au démarrage grâce au scan des composants (`@Service`).

2. **Pourquoi un contrôleur dépendra-t-il de IContratService et non de ContratServiceImpl ?**
   Pour respecter le principe d'inversion de dépendance (SOLID). Le contrôleur dépend d'une abstraction (l'interface), ce qui permet de changer l'implémentation sans modifier le code du contrôleur.

3. **Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.**
   Parce que le conteneur crée une seule instance partagée par tous les threads (requêtes simultanées). Si un service a un attribut modifiable (ex: `private Contrat contratCourant`), deux clients pourraient écraser mutuellement leurs données.

4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` ?**
   Parce que passer un objet détaché directement à `save()` provoque un `merge` qui écrase toutes les colonnes. En chargeant l'entité, on ne modifie que les champs souhaités et on évite de vider la liste des paiements par erreur (à cause de `orphanRemoval`).

5. **Différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` n'expose-t-il ni création, ni modification, ni suppression ?**
    - `@Component` s'utilise sur une classe qu'on contrôle. `@Bean` s'utilise sur une méthode de configuration pour des classes externes.
    - `@Primary` définit le bean par défaut. `@Qualifier` précise le nom du bean à injecter.
    - `IPaiementService` est en lecture seule car un paiement ne peut être créé ou supprimé qu'à travers son `Contrat` (composition avec cascade). On ne veut pas modifier un paiement indépendamment.