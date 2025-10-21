

# Ejecucion en terminal de test case 

### Windows 
```bash
# Ejecutar una clase de test completa
gradlew.bat test --tests "basicJunit.BasicJunit"

# Ejecutar un método de test específico
gradlew.bat test --tests "basicJunit.BasicJunit.verifySometing"

## Usar comodines (wildcards)
# Ejecutar todas las clases que terminen en ApiTest:
gradlew.bat test --tests "*BasicJunit"

# Ejecutar todos los métodos que contengan User en una clase:
gradlew.bat test --tests "*Basic*"
gradlew.bat test --tests "basicJunit.BasicJunit.verify*"

## Combinar con otras opciones de Gradle
# Por ejemplo, para ver logs en tiempo real:
gradlew.bat test --tests "basicJunit.BasicJunit" --info
# O para limpiar antes:
gradlew.bat clean test --tests "basicJunit.BasicJunit.verifySometing"

# Forzar re-ejecución (ignorar caché):
gradlew.bat test --tests "basicJunit.BasicJunit" --rerun-tasks

# Usar variables de ambiente en los test
gradle.bat test -Denvironment=qa --tests "basicRestAssured.CreateItemTestProperties" --info
```

### Linux / MacOs
```bash
# Ejecutar una clase de test completa
./gradlew test --tests "basicJunit.BasicJunit"

# Ejecutar un método de test específico
./gradlew test --tests "basicJunit.BasicJunit.verifySometing"

## Usar comodines (wildcards)
# Ejecutar todas las clases que contengan "BasicJunit"
./gradlew test --tests "*BasicJunit"

# Ejecutar cualquier clase o método que contenga "Basic"
./gradlew test --tests "*Basic*"

# Ejecutar todos los métodos que empiecen con "verify" en la clase
./gradlew test --tests "basicJunit.BasicJunit.verify*"

## Combinar con otras opciones de Gradle
# Ver logs en tiempo real
./gradlew test --tests "basicJunit.BasicJunit" --info

# Limpiar y luego ejecutar un método específico
./gradlew clean test --tests "basicJunit.BasicJunit.verifySometing"

# Forzar re-ejecución (ignorar caché)
./gradlew test --tests "basicJunit.BasicJunit" --rerun-tasks
```