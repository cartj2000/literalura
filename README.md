
<p align="center"> <strong>Desafío LiterAlura</strong>
<br> Proyecto usando: Java, Gutendex API, Jackson, Spring, Hibernate, PostgreSQL, Maven, validaciones, pruebas y arquitectura limpia. </p>

<h1 align="center">Hola , mi nombre es Carlos <img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="35"></h1>
<picture> <img align="right" src="https://github.com/7oSkaaa/7oSkaaa/blob/main/Images/Right_Side.gif?raw=true" width = 250px></picture>

## :triangular_ruler:
🛠 Funcionalidades del proyecto

✔ Menú a través de la consola

✔ Evita entrada de datos inválidos: entradas mal digitadas, repetición de libros, opciones del menú inconsistentes

✔ Resultado testeados y validados

✔ busqueda optimizada en listar autores por nombre para nombre ó apellido

✔ Inyección de dependencias (DI): AutorRepository y LibroRepository inyectados automáticamente -> LibroService (@Service) -> Principal como un bean de Spring (@Component)

✔ manejo de un libro con muchos autores y un autor con muchos libros usando @Transactional para mantener la sesión abierta al recorrer los autores

✔ Opciones extra y Estadística

## :pencil2:
📌 Operación del programa

Usuario ingresa un número entre 1 y 9:	Selecciona la opción:

Ingreso del número 1:	buscar libros por título

Ingreso del número 2:	listar libros registrados

Ingreso del número 3:	listar autores registrados

Ingreso del número 4:	listar autores vivos en un determinado año -> el año puede ser negativo (A.C.) 

Ingreso del número 5:	listar libros por idioma -> En la base de datos pueden existir todos, por ejemplo italiano, alemán, etc y se filtran solo 4: (fr - francés, pt - portugués, es - español, en - inglés). - Muestra estadisticas: Cantidad de libros del idioma

Ingreso del número 6:	top 10 libros más descargados - Muestra estadisticas: Media de descargas, Máximo de descargas, Mínimo de descargas, Cantidad de libros

Ingreso del número 7:	listar autores por nombre - encuentra autores tanto por nombre como por apellido

Ingreso del número 8:	listar autores por nacimiento -> el año puede ser negativo (A.C.)

Ingreso del número 9:	listar autores por nacimiento -> el año puede ser negativo (A.C.)

Ingreso del número 0:	Finaliza la aplicación


## :rocket:
🚀 Tecnologías usadas :rocket:

Java 17: Lógica principal del sistema

Gutendex API: Obtención de ebook

Jackson: Procesamiento de JSON

Spring: Framework para Inversión de Control (IoC), Programación Orientada a Aspectos (AOP) y Spring Boot para automatización.

Hibernate: Framework para mapeo objeto relacional e implementación de Java Persistence API (JPA)

PostgreSQL: Base de datos relacional

Maven: Gestión de librerias (dependencias)

SOLID / Arquitectura Limpia	Diseño desacoplado, extensible y testeable


## :key:
🧠 Principios aplicados


SRP — Single Responsibility Principle

Cada clase tiene una única responsabilidad.

OCP — Open/Closed Principle

Abierto a extensión | Cerrado a modificación


DIP — Dependency Inversion Principle

Módulos de alto nivel no dependen de módulos de bajo nivel.

Ambos deben depender de abstracciones (interfaces).

usando:

IoC — Inversión de Control

DI — Dependency Injection

La dependencia se suministra mediante el Service LibroService


## :clapper:
📌 Esto permite:

Desacoplamiento:	Módulos independientes y mantenibles

Extensión sin romper código


## :pushpin:
🏆 Buenas prácticas aplicadas


Constructor injection para dependencia obligatoria

LibroService

Manejo de errores en la entrada de datos

Clases pequeñas con responsabilidad única (SRP) para aplicar OCP

La clase Principal crea las dependencias (IoC)


## :key:
Estructura de paquetes:

src/
└── com/alura/literalura/

├── model/

│   ├── Autor.java

│   ├── Categoria.java

│   ├── Datos.java

│   ├── DatosAutor.java

│   ├── DatosLibro.java

│   └── Libro.java

├── repository/

│   ├── AutorRepository.java

│   ├── LibroRepository.java

├── service/

│   ├── ConsultaGemini.java

│   ├── ConsumoAPI.java

│   ├── ConvierteDatos.java

│   ├── IConvierteDatos.java

│   └── LibroService.java

└── principal/Principal.java


## :flashlight:
- Acceso al proyecto: a través de GitHub
- Estado del proyecto: funcional 100%
- Características de la aplicación: Desafio literalura Alura Latam
- Desarrolladores: Carlos Arturo Torres Jara
- Licencia: código abierto
- Github: cartj2000
- Linkedin: CARLOS ARTURO TORRES JARA


## :heavy_exclamation_mark:
Agradecimientos:

- 👉Alura Latam: Equipo docente
- 👉Oracle: programa One Oracle Next Education
- 👉Gutendex-API: API de ebook
