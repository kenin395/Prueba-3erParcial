/*
 *  Clase: Paciente
 *  Descripción:
 *      Esta clase representa el modelo de datos para la entidad
 *      "Paciente" dentro del sistema del hospital. Sus atributos
 *      corresponden directamente a las columnas de la tabla
 *      "paciente" en la base de datos.
 *
 *      Es utilizada por:
 *          ✔ PacienteDAO para mapear datos de la BD
 *          ✔ PacienteResource para recibir y enviar JSON en la API
 *          ✔ El frontend para mostrar, registrar y editar pacientes
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 */

package com.hospital.model;

public class Paciente {

    // Identificador único del paciente (primary key)
    private Long id;

    // Nombre completo del paciente
    private String nombre;

    // Dirección de correo electrónico
    private String correo;

    // Edad del paciente
    private Integer edad;

    // Dirección de residencia
    private String direccion;

    // Número de cédula ecuatoriana
    private String cedula;

    // Indica si el paciente está activo o inactivo
    private boolean activo;

    /**
     * Constructor vacío requerido por frameworks y librerías
     * como Jackson para serialización/deserialización JSON.
     */
    public Paciente() {
    }

    // ------------------------- GETTERS & SETTERS -------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
