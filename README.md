# Proyecto-Final-Sistemas-

Descripción general del Proyecto

Este proyecto consiste en el desarrollo de una API REST utilizando Java y el framework Spring Boot para la materia de Implementación de Sistemas .El sistema permite gestionar el catálogo de animales, alimentos y nutrientes de una granja, resolviendo la problemática actual donde la información está dispersa y no hay un control real sobre lo que se consume. A través de esta API, se registran los animales con su respectiva especie, los alimentos disponibles y los nutrientes que aporta cada uno. Esto sienta las bases operativas para controlar la alimentación del ganado y, en una etapa posterior, analizar qué dieta da mejores resultados sobre la producción y beneficio que se obtiene de los animales.   La API expone operaciones de altas, bajas, cambios y consultas (CRUD) sobre 3 entidades clave del modelo relacional:Nutriente: Catálogo de los nutrientes que componen la alimentación.   Alimento: Registro de los alimentos o productos alimenticios disponibles en la granja.   Animal: Gestión de los animales, validando datos críticos como su edad y peso.



## Integrantes de Equipo

- Cruz Fuentes Vianidxi Ximena

- Ojeda Guevara Brandon Adiel

- Sanchez Rodríguez Monica


Requerimientos

## Tabla de requerimientos 

| ID | Nombre del Requerimiento | Descripción del Sistema | Herramienta / Concepto |
|----|--------------------------|-------------------------|------------------------|
| REQ-01 | **Gestión de Entidades (CRUD)** | El sistema debe exponer endpoints REST (peticiones HTTP) que permitan crear, leer, actualizar y eliminar registros de los catálogos de Animales, Alimentos y Nutrientes. | *Spring Web* |
| REQ-02 | **Conexión a Base de Datos** | El sistema debe establecer una conexión segura con un motor de base de datos relacional para persistir la información y mapear automáticamente las tablas del diseño a clases de Java. | *Spring Data JPA* |
| REQ-03 | **Validación de Reglas de Negocio** | El sistema debe verificar que los datos ingresados (JSON) sean correctos antes de guardarlos, asegurando restricciones como que la edad y el peso del animal sean mayores a cero. | *Spring Boot Validation* |
| REQ-04 | **Documentación de la API** | El sistema debe autogenerar una interfaz gráfica interactiva donde los desarrolladores puedan consultar y probar los endpoints. | *Springdoc OpenAPI / Swagger* |
| REQ-05 | **Registro de Eventos (Logs)** | El sistema debe implementar un registro de actividad (logs) en la consola para monitorear las peticiones entrantes, rastrear las consultas SQL ejecutadas y capturar errores o excepciones en tiempo real. | *Spring Boot Logging / SLF4J* |




## Funcionalidades de Spring Boot

Para optimizar el desarrollo de este proyecto, nos apoyaremos en las siguientes dependencias y paquetes que proporciona el ecosistema de Spring Boot:

Conexión a Base de Datos (Spring Data JPA): Facilita el mapeo relacional de las entidades (como el uso de @ManyToOne para asociar el animal a su especie) y gestiona la persistencia de datos en nuestro motor relacional sin tener que escribir consultas SQL manuales para todo.

Creación de la API (Spring Web): Proporciona las herramientas necesarias para construir aplicaciones web, incluyendo RESTful applications, permitiendo crear los controladores y mapear las rutas web (GET, POST, PUT, DELETE).

Documentación Automática (Springdoc OpenAPI / Swagger): Genera una interfaz visual e interactiva que documenta automáticamente los endpoints creados en la API, facilitando las pruebas de los métodos CRUD.

Validación de Datos (Spring Boot Validation): Permite asegurar desde el backend que se cumplan las reglas de negocio, validando mediante anotaciones que los Payloads (JSON de entrada) sean correctos antes de enviarlos a la base de datos.

Reducción de Código Boilerplate (Lombok): Automatiza la creación de constructores, getters y setters en las clases de Java, manteniendo el código limpio y facilitando la implementación de patrones de diseño como el Builder para estructurar los DTOs.

## API de la granja

La API persiste sus entidades con Spring Data JPA y una base H2 en memoria. Los
datos se pierden al detener la aplicación.

| Recurso | Endpoints |
|---------|-----------|
| Animales | `GET/POST /animales`, `GET/PUT/DELETE /animales/{id}` |
| Alimentos | `GET/POST /alimentos`, `GET/PUT/DELETE /alimentos/{id}` |
| Nutrientes | `GET/POST /nutrientes`, `GET/PUT/DELETE /nutrientes/{id}` |

Animal registra `especie`, `edad` y `peso`; edad y peso deben ser mayores que
cero. Alimento registra `nombre`, `descripcion` y `nutrientes`, que contiene
los IDs de los nutrientes asociados. Nutriente registra `nombre` y
`descripcion`.