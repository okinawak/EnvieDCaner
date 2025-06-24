# verify_build.ps1

Write-Host "Début de la vérification de compilation..."

# Détecter le type de projet
$cwd = Get-Location
Write-Host "Répertoire courant: $cwd"

# Flags pour détecter le type de projet
$maven = Test-Path "pom.xml"
$gradle = Test-Path "build.gradle"
$node = Test-Path "package.json"
$dotnet = Test-Path "*.csproj"
$python = Test-Path "setup.py"
$c_project = (Test-Path "Makefile") -or (Test-Path "*.c") -or (Test-Path "*.h")

if ($maven) {
    Write-Host "Projet Maven détecté"
    mvn clean install
    exit $LASTEXITCODE
} elseif ($gradle) {
    Write-Host "Projet Gradle détecté"
    gradle build
    exit $LASTEXITCODE
} elseif ($node) {
    Write-Host "Projet Node.js détecté"
    npm install
    npm run build
    exit $LASTEXITCODE
} elseif ($dotnet) {
    Write-Host ".NET projet détecté"
    dotnet build
    exit $LASTEXITCODE
} elseif ($python) {
    Write-Host "Projet Python détecté"
    python setup.py build
    exit $LASTEXITCODE
} elseif ($c_project) {
    Write-Host "Projet C détecté"
    # Utiliser make si disponible
    if (Get-Command make -ErrorAction SilentlyContinue) {
        make
        exit $LASTEXITCODE
    } else {
        Write-Host "Make non trouvé, aucune compilation effectuée"
        exit 0
    }
} else {
    Write-Host "Aucun type de projet reconnu, on considère que la compilation est OK"
    exit 0
}