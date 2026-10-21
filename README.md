# Proyecto Final de Sistemas

API REST desarrollada con Java 17 y Spring Boot para el proyecto de gestión de
una granja. El repositorio conserva el endpoint inicial `Hello World` y agrega
la implementación del laboratorio Spring Data JPA usando `Animal` como única
entidad.

## Integrantes

- Cruz Fuentes Vianidxi Ximena
- Ojeda Guevara Brandon Adiel
- Sanchez Rodríguez Monica

## Estructura

```text
.devcontainer/
  devcontainer.json
src/
  main/java/com/proyectofinal/sistemas/
    ProyectoFinalSistemasApplication.java
    HelloController.java
    controllers/AnimalController.java
    models/Animal.java
    repositories/AnimalRepository.java
  test/java/com/proyectofinal/sistemas/controllers/
    AnimalControllerTest.java
pom.xml
.gitignore
```

## Ejecutar

```bash
mvn spring-boot:run
```

- `GET /` responde `Hello World`.
- `GET /animales` consulta los animales registrados.
- `POST /animales` registra un animal con `especie`, `edad` y `peso`.

La edad y el peso deben ser mayores que cero y la especie no puede estar vacía.
Spring Data JPA usa H2 en memoria; los datos se borran al detener la aplicación.

## Probar

```bash
mvn test
```

El laboratorio se trabaja en la rama `feature/Animal`. La entidad, el
repositorio y el controlador están limitados a los requisitos del ejercicio:
una clase en `models`, un repositorio JPA y operaciones `GET` y `POST`.
