# Proyecto-Final-Sistemas-

## Descripción general del proyecto

Este proyecto consiste en el desarrollo de una API REST utilizando Java y el
framework Spring Boot para la materia de Implementación de Sistemas. El sistema
permite gestionar el catálogo de animales, alimentos y nutrientes de una
granja, resolviendo la problemática actual donde la información está dispersa
y no hay un control real sobre lo que se consume. A través de esta API, se
registran los animales con su respectiva especie, los alimentos disponibles y
los nutrientes que aporta cada uno. Esto sienta las bases operativas para
controlar la alimentación del ganado y, en una etapa posterior, analizar qué
dieta da mejores resultados sobre la producción y beneficio que se obtiene de
los animales.

## Integrantes del equipo

- Cruz Fuentes Vianidxi Ximena
- Ojeda Guevara Brandon Adiel
- Sanchez Rodríguez Monica

## Tabla de requerimientos

| ID | Nombre del requerimiento | Descripción del sistema | Herramienta / concepto |
|----|--------------------------|-------------------------|------------------------|
| REQ-01 | **Gestión de entidades (CRUD)** | El sistema debe exponer endpoints REST que permitan crear, leer, actualizar y eliminar registros de los catálogos de Animales, Alimentos y Nutrientes. | Spring Web |
| REQ-02 | **Conexión a base de datos** | El sistema debe establecer una conexión con un motor de base de datos relacional para persistir la información y mapear las tablas a clases de Java. | Spring Data JPA |
| REQ-03 | **Validación de reglas de negocio** | El sistema debe verificar que los datos ingresados sean correctos antes de guardarlos, incluyendo que la edad y el peso del animal sean mayores a cero. | Spring Boot Validation |
| REQ-04 | **Documentación de la API** | El sistema debe generar una interfaz interactiva donde los desarrolladores puedan consultar y probar los endpoints. | Springdoc OpenAPI / Swagger |
| REQ-05 | **Registro de eventos (logs)** | El sistema debe registrar actividad en consola para monitorear peticiones, consultas SQL y errores o excepciones. | Spring Boot Logging / SLF4J |

## Funcionalidades de Spring Boot

Para optimizar el desarrollo del proyecto, se consideran las siguientes
dependencias y funcionalidades del ecosistema Spring Boot:

- **Conexión a base de datos (Spring Data JPA):** facilita el mapeo relacional
  de las entidades y gestiona la persistencia sin tener que escribir consultas
  SQL manuales para cada operación.
- **Creación de la API (Spring Web):** proporciona las herramientas para
  construir endpoints REST y mapear rutas HTTP como GET, POST, PUT y DELETE.
- **Documentación automática (Springdoc OpenAPI / Swagger):** genera una
  interfaz visual e interactiva para documentar y probar los endpoints.
- **Validación de datos (Spring Boot Validation):** permite comprobar que los
  datos JSON cumplan las reglas de negocio antes de persistirlos.
- **Reducción de código repetitivo (Lombok):** puede automatizar getters,
  setters y constructores para mantener clases más concisas.
- **Seguridad y autenticación (Spring Security):** puede proteger endpoints y
  gestionar autenticación y autorización cuando se incorpore ese requisito.
- **Registro de actividad (SLF4J / Logback):** permite registrar eventos,
  peticiones y errores para facilitar el monitoreo y diagnóstico.

## Laboratorio Spring JPA: Animal

Como práctica del laboratorio Spring JPA, se eligió `Animal` para adaptar el
ejemplo al dominio de la granja. El laboratorio contiene una sola clase en
`models`, un repositorio que extiende `JpaRepository` y un controlador con
operaciones GET y POST.

La entidad registra `especie`, `edad` y `peso`. La especie no puede estar vacía
y la edad y el peso deben ser mayores que cero. Spring Data JPA persiste los
datos en H2 en memoria, por lo que se pierden al detener la aplicación.

Endpoints implementados:

- `GET /animales`: consulta todos los animales.
- `POST /animales`: registra un animal.
- `GET /`: endpoint inicial que responde `Hello World`.

## Entorno de desarrollo

El repositorio incluye `.devcontainer/devcontainer.json`, `.devcontainer/Dockerfile`
y `.devcontainer/docker-compose.yml` para configurar el entorno con Java 17,
Maven y el puerto 8080. También conserva el archivo `.gitignore` para excluir
artefactos generados y archivos locales del IDE.

Para iniciar con Docker Compose desde la raíz del repositorio:

```bash
docker compose -f .devcontainer/docker-compose.yml up --build
```

Para ejecutar las pruebas:

```bash
mvn test
```

## Rama del laboratorio

El cambio del laboratorio se trabaja en la rama `feature/Animal`.
