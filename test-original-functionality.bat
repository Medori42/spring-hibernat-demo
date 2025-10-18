@echo off
echo ========================================
echo   Test des fonctionnalites originales
echo ========================================
echo.

echo Verification de la structure du projet...
echo.

echo 1. Verification des classes principales...
if exist "src\main\java\entities\Product.java" (
    echo [OK] Product.java existe
) else (
    echo [ERREUR] Product.java manquant
)

if exist "src\main\java\dao\IDao.java" (
    echo [OK] IDao.java existe
) else (
    echo [ERREUR] IDao.java manquant
)

if exist "src\main\java\metier\ProductDaoImpl.java" (
    echo [OK] ProductDaoImpl.java existe
) else (
    echo [ERREUR] ProductDaoImpl.java manquant
)

if exist "src\main\java\util\HibernateConfig.java" (
    echo [OK] HibernateConfig.java existe
) else (
    echo [ERREUR] HibernateConfig.java manquant
)

echo.
echo 2. Verification des fichiers de configuration...
if exist "src\main\resources\application.properties" (
    echo [OK] application.properties existe
) else (
    echo [ERREUR] application.properties manquant
)

if exist "pom.xml" (
    echo [OK] pom.xml existe
) else (
    echo [ERREUR] pom.xml manquant
)

echo.
echo 3. Verification des classes de test...
if exist "src\test\java\entities\ProductTest.java" (
    echo [OK] ProductTest.java existe
) else (
    echo [ERREUR] ProductTest.java manquant
)

if exist "src\test\java\metier\ProductDaoImplTest.java" (
    echo [OK] ProductDaoImplTest.java existe
) else (
    echo [ERREUR] ProductDaoImplTest.java manquant
)

if exist "src\test\java\ProductIntegrationTest.java" (
    echo [OK] ProductIntegrationTest.java existe
) else (
    echo [ERREUR] ProductIntegrationTest.java manquant
)

echo.
echo 4. Verification des dependances dans pom.xml...
findstr /C:"spring-context" pom.xml >nul
if %errorlevel% equ 0 (
    echo [OK] spring-context present
) else (
    echo [ERREUR] spring-context manquant
)

findstr /C:"hibernate-core" pom.xml >nul
if %errorlevel% equ 0 (
    echo [OK] hibernate-core present
) else (
    echo [ERREUR] hibernate-core manquant
)

findstr /C:"mysql-connector-java" pom.xml >nul
if %errorlevel% equ 0 (
    echo [OK] mysql-connector-java present
) else (
    echo [ERREUR] mysql-connector-java manquant
)

findstr /C:"junit" pom.xml >nul
if %errorlevel% equ 0 (
    echo [OK] junit present
) else (
    echo [ERREUR] junit manquant
)

echo.
echo 5. Verification des fichiers de documentation...
if exist "README.md" (
    echo [OK] README.md existe
) else (
    echo [ERREUR] README.md manquant
)

if exist "TESTING.md" (
    echo [OK] TESTING.md existe
) else (
    echo [ERREUR] TESTING.md manquant
)

echo.
echo ========================================
echo   Verification terminee
echo ========================================
echo.

echo Pour tester l'execution reelle, vous devez:
echo 1. Installer Maven
echo 2. Configurer MySQL
echo 3. Executer: mvn clean compile
echo 4. Executer: mvn exec:java -Dexec.mainClass="TestHibernate"
echo 5. Executer: mvn exec:java -Dexec.mainClass="Presentation2"
echo.

pause
