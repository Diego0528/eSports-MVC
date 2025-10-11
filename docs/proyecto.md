cat > docs/proyecto.md <<EOF
# Proyecto: Sistema de Torneos de eSports (MVC)

**Autor:** Diego  
**Versión:** 1.0 — Borrador  
**Lenguaje:** Java  
**Framework:** Swing (Interfaz Gráfica)  
**Base de Datos:** SQL Server  
**Fecha:** $(date +%Y-%m-%d)

---

## 1. Narrativa
La idea nace de la necesidad de organizar torneos de eSports de manera sencilla, transparente y accesible.  
Actualmente, muchos torneos se gestionan de forma manual o con plataformas poco flexibles.  
Este proyecto busca centralizar la gestión en un sistema profesional que permita registrar jugadores, crear torneos, llevar puntajes y mostrar resultados de manera clara.

---

## 2. Objetivo General
Desarrollar un sistema de escritorio bajo el patrón **MVC** que gestione torneos de eSports, desde la inscripción de jugadores hasta la publicación de resultados.

---

## 3. Alcance (Versión 1.0)
- Registro de jugadores y equipos.  
- Creación de torneos (fechas, modalidad, reglas).  
- Asignación de enfrentamientos y seguimiento de partidas.  
- Registro de resultados.  
- Visualización de clasificaciones y estadísticas.  
- Interfaz gráfica con Java Swing.

---

## 4. Requerimientos

### Funcionales
- **RF1:** El sistema permitirá registrar jugadores y equipos.  
- **RF2:** El sistema permitirá crear torneos con parámetros configurables.  
- **RF3:** El sistema permitirá gestionar enfrentamientos y resultados.  
- **RF4:** El sistema mostrará clasificaciones y estadísticas actualizadas.  

### No Funcionales
- **RNF1:** El sistema estará desarrollado bajo el patrón MVC.  
- **RNF2:** La base de datos será relacional (SQL Server).  
- **RNF3:** El sistema será una aplicación de escritorio en Java Swing.  
- **RNF4:** Se mantendrá un repositorio con control de versiones en Git.

---

## 5. Tecnologías (tentativas)
- Lenguaje: **Java**  
- Interfaz: **Swing**  
- Base de Datos: **SQL Server**  
- Control de versiones: **Git + GitHub**

---

## 6. Futuras Extensiones
- Soporte para diferentes formatos de torneo (round-robin, eliminación directa, etc.).  
- Sistema de notificaciones.  
- Integración con servicios en línea o API.  
- Generación de reportes y estadísticas gráficas.
