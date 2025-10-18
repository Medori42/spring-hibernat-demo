# Guide des Tests - Spring Hibernate Demo

Ce document décrit la stratégie de test et l'utilisation des différents types de tests implémentés dans le projet.

## Architecture des Tests

### Structure des dossiers de test
```
src/test/
├── java/
│   ├── entities/
│   │   └── ProductTest.java           # Tests unitaires de l'entité
│   ├── metier/
│   │   └── ProductDaoImplTest.java    # Tests unitaires du DAO avec mocks
│   ├── util/
│   │   ├── TestHibernateConfig.java   # Configuration de test
│   │   └── HibernateConfigTest.java   # Tests de configuration
│   └── ProductIntegrationTest.java    # Tests d'intégration
└── resources/
    └── application-test.properties    # Configuration de test (H2)
```

## Types de Tests

### 1. Tests Unitaires

#### ProductTest.java
**Objectif** : Tester l'entité Product de manière isolée

**Tests inclus** :
- Constructeurs (défaut et paramétré)
- Getters et setters
- Méthode toString()
- Cas limites (prix négatif, nom vide, etc.)

**Technologies** : JUnit 4, AssertJ

#### ProductDaoImplTest.java
**Objectif** : Tester le DAO avec des mocks pour isoler la logique métier

**Tests inclus** :
- Opérations CRUD (create, read, update, delete)
- Gestion des cas d'erreur
- Vérification des appels aux méthodes Hibernate

**Technologies** : JUnit 4, Mockito, AssertJ

#### HibernateConfigTest.java
**Objectif** : Vérifier la configuration Spring/Hibernate

**Tests inclus** :
- Configuration de la DataSource
- Configuration de SessionFactory
- Configuration du TransactionManager

**Technologies** : JUnit 4, Spring Test, AssertJ

### 2. Tests d'Intégration

#### ProductIntegrationTest.java
**Objectif** : Tester l'ensemble de l'application avec une vraie base de données

**Tests inclus** :
- Cycle de vie complet des entités
- Opérations CRUD en base de données
- Gestion des transactions
- Cas d'usage réels

**Technologies** : JUnit 4, Spring Test, H2 Database, AssertJ

## Configuration des Tests

### Base de données de test (H2)
- **Type** : Base de données en mémoire
- **Avantages** : Rapide, pas de configuration externe
- **Configuration** : `application-test.properties`

### Annotations importantes
- `@RunWith(SpringJUnit4ClassRunner.class)` : Intégration Spring Test
- `@ContextConfiguration` : Configuration des beans de test
- `@Transactional` : Gestion automatique des transactions
- `@Mock` : Création de mocks avec Mockito
- `@InjectMocks` : Injection des mocks dans les classes à tester

## Exécution des Tests

### Commandes Maven

```bash
# Tous les tests
mvn test

# Tests unitaires seulement
mvn test -Dtest="*Test"

# Tests d'intégration seulement
mvn test -Dtest="*IntegrationTest"

# Test spécifique
mvn test -Dtest=ProductTest

# Avec couverture de code
mvn clean test jacoco:report
```

### Script Windows
```bash
run-tests.bat
```

## Couverture de Code

### JaCoCo
- **Plugin** : jacoco-maven-plugin
- **Rapport** : `target/site/jacoco/index.html`
- **Objectif** : Mesurer la couverture de code des tests

### Métriques importantes
- **Couverture de lignes** : Pourcentage de lignes exécutées
- **Couverture de branches** : Pourcentage de branches testées
- **Couverture de méthodes** : Pourcentage de méthodes testées

## Bonnes Pratiques

### Tests Unitaires
1. **Isolation** : Un test ne doit pas dépendre d'autres tests
2. **Déterminisme** : Les tests doivent donner le même résultat à chaque exécution
3. **Rapidité** : Les tests unitaires doivent être très rapides
4. **Mocks** : Utiliser des mocks pour isoler les dépendances

### Tests d'Intégration
1. **Base de données** : Utiliser une base de données de test dédiée
2. **Nettoyage** : Nettoyer les données après chaque test
3. **Transactions** : Utiliser `@Transactional` pour l'isolation
4. **Données de test** : Créer des données de test réalistes

### Assertions
1. **AssertJ** : Utiliser des assertions fluides et expressives
2. **Messages d'erreur** : Inclure des messages explicites
3. **Cohérence** : Utiliser le même style d'assertions dans tout le projet

## Exemples de Tests

### Test unitaire simple
```java
@Test
public void testProductCreation() {
    // Given
    String name = "Test Product";
    double price = 99.99;
    
    // When
    Product product = new Product(name, price);
    
    // Then
    assertThat(product.getName()).isEqualTo(name);
    assertThat(product.getPrice()).isEqualTo(price);
}
```

### Test avec mock
```java
@Test
public void testCreateProduct() {
    // Given
    Product product = new Product("Test", 100.0);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    
    // When
    boolean result = productDao.create(product);
    
    // Then
    assertThat(result).isTrue();
    verify(session).save(product);
}
```

### Test d'intégration
```java
@Test
@Transactional
public void testCompleteCrudOperations() {
    // Create
    Product product = new Product("Test", 100.0);
    productDao.create(product);
    
    // Read
    Product found = productDao.findById(product.getId());
    assertThat(found).isNotNull();
    
    // Update
    found.setName("Updated");
    productDao.update(found);
    
    // Delete
    productDao.delete(found);
    assertThat(productDao.findById(product.getId())).isNull();
}
```

## Dépannage

### Problèmes courants
1. **Tests qui échouent** : Vérifier la configuration de la base de données
2. **Mocks non injectés** : Vérifier les annotations `@Mock` et `@InjectMocks`
3. **Transactions** : S'assurer que `@Transactional` est présent
4. **Configuration** : Vérifier que `TestHibernateConfig` est correctement configurée

### Logs de debug
- Activer les logs Hibernate : `logging.level.org.hibernate.SQL=DEBUG`
- Activer les logs Spring : `logging.level.org.springframework=DEBUG`
