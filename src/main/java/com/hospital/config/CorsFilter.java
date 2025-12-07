/*
 *  Descripción:
 *  Filtro CORS (Cross-Origin Resource Sharing) para permitir que
 *  el frontend –que puede estar en un dominio o puerto distinto–
 *  pueda consumir los servicios REST del backend sin restricciones.
 *
 *  Este filtro añade las cabeceras necesarias a todas las respuestas
 *  HTTP para habilitar solicitudes desde cualquier origen, así como
 *  permitir métodos y encabezados comunes.
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 */

package com.hospital.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * Filtro global encargado de agregar configuraciones CORS
 * a todas las respuestas generadas por la API REST.
 *
 * La anotación @Provider permite que Jakarta registre automáticamente
 * este filtro sin necesidad de configurarlo manualmente.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    /**
     * Método que se ejecuta en cada respuesta enviada desde la API.
     * Aquí se agregan cabeceras que permiten:
     *  - Aceptar solicitudes desde cualquier origen (*)
     *  - Permitir encabezados personalizados
     *  - Permitir métodos como GET, POST, PUT, DELETE, etc.
     *  - Autorizar envío de credenciales si fuera necesario
     *
     * @param request   Datos de la petición recibida
     * @param response  Respuesta generada por la API
     */
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
