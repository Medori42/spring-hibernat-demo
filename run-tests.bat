@echo off
echo ========================================
echo    Spring Hibernate Demo - Tests
echo ========================================
echo.

echo Compilation du projet...
call mvn clean compile test-compile
if %errorlevel% neq 0 (
    echo Erreur lors de la compilation!
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Execution des tests unitaires
echo ========================================
echo.

echo Test de l'entite Product...
call mvn test -Dtest=ProductTest
if %errorlevel% neq 0 (
    echo Erreur dans les tests de Product!
    pause
    exit /b 1
)

echo.
echo Test du DAO ProductDaoImpl...
call mvn test -Dtest=ProductDaoImplTest
if %errorlevel% neq 0 (
    echo Erreur dans les tests de ProductDaoImpl!
    pause
    exit /b 1
)

echo.
echo Test de la configuration Hibernate...
call mvn test -Dtest=HibernateConfigTest
if %errorlevel% neq 0 (
    echo Erreur dans les tests de configuration!
    pause
    exit /b 1
)

echo.
echo Test d'integration complet...
call mvn test -Dtest=ProductIntegrationTest
if %errorlevel% neq 0 (
    echo Erreur dans les tests d'integration!
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Execution de tous les tests
echo ========================================
echo.

call mvn test
if %errorlevel% neq 0 (
    echo Certains tests ont echoue!
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Tous les tests sont passes avec succes!
echo ========================================
echo.

echo Generation du rapport de couverture...
call mvn jacoco:report

echo.
echo Tests termines avec succes!
pause