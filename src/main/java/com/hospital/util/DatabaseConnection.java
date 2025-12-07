/*
 *  Clase: DatabaseConnection
 *  Descripción:
 *      Esta clase administra la conexión a la base de datos MySQL mediante JDBC.
 *      Centraliza los parámetros de conexión y expone un único método para
 *      obtener conexiones reutilizables en toda la aplicación.
 *
 *      Implementa la carga del driver de MySQL en un bloque estático, lo cual
 *      garantiza que el controlador JDBC esté disponible antes de que se
 *      realice cualquier conexión.
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 */

package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // -------------------------------------------------------------
    // URL de conexión a MySQL usando el contenedor Docker
    // -------------------------------------------------------------
    private static final String URL =
            "jdbc:mysql://localhost:3307/hospitaldb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // Usuario y contraseña definidos en docker-compose
    private static final String USER = "hospitaluser";
    private static final String PASS = "hospitalpass";

    // -------------------------------------------------------------
    // BLOQUE ESTÁTICO: carga del driver JDBC de MySQL
    //
    // Se ejecuta UNA sola vez cuando la clase es cargada por la JVM.
    // Si el driver no se encuentra, la aplicación no podrá conectarse.
    // -------------------------------------------------------------
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar driver moderno
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Error cargando el driver de MySQL. Verifique dependencias.",
                    e
            );
        }
    }

    // -------------------------------------------------------------
    // Método público que devuelve una conexión válida.
    // Cada llamada crea una nueva conexión del pool JDBC.
    // -------------------------------------------------------------
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
