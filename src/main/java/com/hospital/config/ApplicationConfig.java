/*
 *  Descripción:
 *  Esta clase configura el punto base de la aplicación REST
 *  utilizando Jakarta EE. La anotación @ApplicationPath define
 *  la URL raíz desde la cual se expondrán todos los servicios REST.
 *
 *  En este proyecto, todos los endpoints se publican bajo "/api".
 *  Ejemplo: http://localhost:8080/Hospital/api/pacientes
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 */

package com.hospital.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Clase principal de configuración para los servicios REST.
 * Extiende la clase Application sin necesidad de métodos adicionales,
 * ya que su única responsabilidad es establecer la ruta base.
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // No código adicional requerido
}
