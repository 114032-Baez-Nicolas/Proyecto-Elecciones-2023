# Proyecto-Elecciones-2023
Este proyecto implementa un sistema de gestión y consulta de resultados electorales. Proporciona información a nivel nacional y distrital, integrando servicios externos y manejando resiliencia ante fallos.

## **Características**
- **Resumen Nacional**: Total de votos por agrupación política, votos en blanco, nulos, impugnados y recurridos.
- **Resultados por Distrito**: Incluye detalles como secciones, votos escrutados, porcentaje del padrón nacional y agrupación ganadora.
- **Manejo de Resiliencia**: Circuit Breaker para garantizar la disponibilidad del sistema ante fallos externos.
- **Documentación Interactiva**: Uso de Swagger para probar y documentar los endpoints.
- **Base de Datos en Memoria**: Persistencia de datos con H2 Database.

---

## **Tecnologías Utilizadas**
- **Java 17**: Lenguaje principal.
- **Spring Boot 3.0**: Framework para construir el backend.
- **RestTemplate**: Para consumir APIs externas.
- **Resilience4j**: Implementación de patrones de resiliencia (Circuit Breaker).
- **H2 Database**: Base de datos en memoria.
- **Swagger/OpenAPI**: Documentación interactiva.
- **Lombok**: Reducción de código repetitivo mediante anotaciones.

---

## **Requisitos Previos**
1. **Java 17+**
2. **Maven 3+**

---

## **Cómo Ejecutar**
1. Clonar este repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-elecciones-2023.git
   cd proyecto-elecciones-2023
