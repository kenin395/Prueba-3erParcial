/*
 *  Clase: PacienteDAO
 *  Descripción:
 *      Esta clase implementa el patrón DAO (Data Access Object) para
 *      gestionar todas las operaciones CRUD relacionadas con la tabla
 *      "paciente" en la base de datos.
 *
 *      Permite:
 *          ✔ Listar pacientes
 *          ✔ Buscar por ID
 *          ✔ Crear nuevos registros
 *          ✔ Editar información existente
 *          ✔ Activar / desactivar pacientes
 *          ✔ Eliminar registros
 *
 *      Utiliza conexiones JDBC por medio de DatabaseConnection.
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 *
 */

package com.hospital.dao;

import com.hospital.model.Paciente;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    /**
     * Método privado que convierte una fila de ResultSet en un objeto Paciente.
     *
     * @param rs Registro obtenido de la consulta SQL
     * @return Objeto Paciente mapeado con los datos provenientes de la BD
     * @throws SQLException si ocurre un error al leer los datos
     */
    private Paciente map(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setCorreo(rs.getString("correo"));
        p.setEdad(rs.getInt("edad"));
        p.setDireccion(rs.getString("direccion"));
        p.setCedula(rs.getString("cedula"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    // -------------------------------------------------------------------
    // LISTAR TODOS LOS PACIENTES
    // -------------------------------------------------------------------
    /**
     * Obtiene todos los registros de la tabla paciente.
     *
     * @return Lista de pacientes
     */
    public List<Paciente> findAll() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    // -------------------------------------------------------------------
    // BUSCAR POR ID
    // -------------------------------------------------------------------
    /**
     * Busca un paciente según su ID.
     *
     * @param id Identificador del paciente
     * @return Paciente si existe, null si no
     */
    public Paciente findById(Long id) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);
        }
        return null;
    }

    // -------------------------------------------------------------------
    // CREAR PACIENTE
    // -------------------------------------------------------------------
    /**
     * Inserta un nuevo registro en la base de datos.
     *
     * @param p Paciente a registrar
     * @return El mismo objeto paciente con su ID generado
     */
    public Paciente create(Paciente p) throws SQLException {
        String sql = "INSERT INTO paciente(nombre, correo, edad, direccion, cedula, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setInt(3, p.getEdad());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getCedula());
            ps.setBoolean(6, true); // Por defecto todo paciente se registra activo

            ps.executeUpdate();

            // Obtiene la llave primaria generada
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) p.setId(keys.getLong(1));
        }
        return p;
    }

    // -------------------------------------------------------------------
    // EDITAR PACIENTE
    // -------------------------------------------------------------------
    /**
     * Actualiza la información de un paciente.
     *
     * @param p Paciente con los nuevos datos
     * @return true si el registro fue actualizado, false si no existe
     */
    public boolean update(Paciente p) throws SQLException {
        String sql = """
            UPDATE paciente 
            SET nombre=?, correo=?, edad=?, direccion=?, cedula=? 
            WHERE id=?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setInt(3, p.getEdad());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getCedula());
            ps.setLong(6, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // -------------------------------------------------------------------
    // CAMBIAR ESTADO (ACTIVO / INACTIVO)
    // -------------------------------------------------------------------
    /**
     * Activa o desactiva un paciente en la base de datos.
     *
     * @param id ID del paciente
     * @param activo nuevo estado
     * @return true si se actualizó, false si no existe
     */
    public boolean updateEstado(Long id, boolean activo) throws SQLException {
        String sql = "UPDATE paciente SET activo=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setLong(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    // -------------------------------------------------------------------
    // ELIMINAR PACIENTE
    // -------------------------------------------------------------------
    /**
     * Elimina un registro de la tabla paciente.
     *
     * @param id ID del paciente
     * @return true si fue eliminado, false si no existe
     */
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
