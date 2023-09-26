# DSSD-Backend

## Requerimientos

Gradle (yo tengo gradle 8)
Java 17

## archivos

buld.gradle: Aca es donde especificamos las dependencias que necesita el proyecto como lo son por ejemplo
los paquetes de dependencias de spring u otras dependencias que se puedan encontrar en
mavenrepository

por ejemplo en ese archivo se puede ver que el proyecto ya viene con " runtimeOnly 'com.h2database:h2' " que es una base
de datos en memoria que sirve para hacer pruebas sin tener que instalar una base de datos sql.

src/main/resources/application.properties: Aca van propiedades para configurar aspectos especificos de la aplicación como la conexion a la base de datos, propiedades de las sesiones, cookies, etc. Aunque no se si lo vamos a necesitar.

## Iniciar aplicación

Desde terminal: gradle bootRun

Este comando no solo inicia la aplicación sino que tambien chequea si hay dependencias que deben instalarse para que pueda compilar el proyecto
