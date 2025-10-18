# Spring Hibernate Demo

Ce projet démontre l'intégration de Spring Framework avec Hibernate pour la gestion des entités persistantes et l'injection de dépendances.

## Objectifs pédagogiques

- Comprendre les bases de la gestion des dépendances avec Maven
- Configurer Spring pour gérer l'injection de dépendances et les transactions
- Intégrer Hibernate pour la gestion des entités persistantes
- Créer un DAO pour gérer les opérations CRUD
- Configurer une base de données MySQL pour l'interaction avec les entités

## Structure du projet

```
src/
└── main/
    ├── java/
    │   ├── dao/           # Interface générique IDao
    │   ├── entities/      # Entités JPA (Product)
    │   ├── metier/        # Implémentation des DAOs
    │   ├── util/          # Configuration Spring/Hibernate
    │   ├── TestHibernate.java    # Test de configuration
    │   └── Presentation2.java    # Démonstration du DAO
    └── resources/
        └── application.properties # Configuration de la base de données
```

## Prérequis

- Java 8 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## Configuration

### 1. Base de données MySQL

Créez une base de données MySQL nommée `base` :

```sql
CREATE DATABASE base;
```

### 2. Configuration de la base de données

Modifiez le fichier `src/main/resources/application.properties` selon votre configuration MySQL :

```properties
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

## Utilisation

### 1. Compilation du projet

```bash
mvn clean compile
```

### 2. Test de la configuration

Exécutez la classe `TestHibernate` pour vérifier que la configuration Spring/Hibernate est correcte :

```bash
mvn exec:java -Dexec.mainClass="TestHibernate"
```

### 3. Test du DAO

Exécutez la classe `Presentation2` pour tester les opérations CRUD :

```bash
mvn exec:java -Dexec.mainClass="Presentation2"
```

## Fonctionnalités

### Entité Product

- `id` : Identifiant unique (auto-généré)
- `name` : Nom du produit
- `price` : Prix du produit

### Opérations CRUD

- `create(Product)` : Créer un nouveau produit
- `findById(int)` : Rechercher un produit par ID
- `findAll()` : Récupérer tous les produits
- `update(Product)` : Mettre à jour un produit
- `delete(Product)` : Supprimer un produit

## Technologies utilisées

- **Spring Framework 5.3.22** : Injection de dépendances et gestion des transactions
- **Hibernate 5.6.12** : Mapping objet-relationnel (ORM)
- **MySQL Connector 8.0.29** : Connexion à la base de données MySQL
- **Maven** : Gestion des dépendances et build

## Tests

Le projet inclut une suite de tests complète :

### Tests unitaires
- **`ProductTest`** : Tests de l'entité Product (constructeurs, getters/setters, toString)
- **`ProductDaoImplTest`** : Tests du DAO avec des mocks (Mockito)
- **`HibernateConfigTest`** : Tests de la configuration Spring/Hibernate

### Tests d'intégration
- **`ProductIntegrationTest`** : Tests d'intégration complets avec base de données H2

### Exécution des tests

```bash
# Exécuter tous les tests
mvn test

# Exécuter un test spécifique
mvn test -Dtest=ProductTest

# Exécuter avec couverture de code
mvn clean test jacoco:report
```

### Script de test Windows
```bash
run-tests.bat
```

### Couverture de code
Le rapport de couverture est généré dans `target/site/jacoco/index.html`

## Extensions possibles

- Ajouter une entité Category avec une relation @ManyToOne
- Créer une interface web avec Spring MVC
- Ajouter la validation des données avec Bean Validation
- Implémenter la pagination et le tri des résultats
- Ajouter des tests de performance avec JMH
