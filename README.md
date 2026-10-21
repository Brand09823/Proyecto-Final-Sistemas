# Proyecto-Final-Sistemas-

Descripción general del Proyecto

Este proyecto desarrolla una API REST con Java y Spring Boot para apoyar la gestión de una granja, donde la información sobre animales, alimentos y nutrientes suele estar dispersa. El dominio del proyecto contempla tres entidades: Animal, para registrar especie, edad y peso; Alimento, para administrar los alimentos disponibles; y Nutriente, para describir sus aportes. La implementación actual del laboratorio Spring JPA se enfoca en la entidad Animal; su alcance está descrito más adelante.



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

## Laboratorio Spring JPA: Animal

Para el laboratorio se eligió `Animal` como la única entidad JPA en la carpeta
`models`. Spring Data JPA la persiste en una base H2 en memoria; los datos se
pierden al detener la aplicación. El controlador expone:

- `GET /animales`: consulta todos los animales.
- `POST /animales`: registra un animal. El cuerpo acepta `especie`, `edad` y
  `peso`; especie no puede estar vacía, y edad y peso deben ser mayores que
  cero.