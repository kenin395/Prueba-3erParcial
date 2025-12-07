/*
 *  Clase: PacienteResource
 *  Descripción:
 *      Esta clase expone los servicios REST del módulo de pacientes mediante
 *      JAX-RS. Define los endpoints que permiten listar, crear, editar,
 *      cambiar el estado y eliminar pacientes dentro del sistema del hospital.
 *
 *      Funciona como puente entre el frontend y la base de datos, delegando
 *      la lógica de acceso a datos al DAO (PacienteDAO).
 *
 *  Autor: Kenin Cusme
 *  Fecha: 06/12/2025
 */

package com.hospital.resource;

import com.hospital.dao.PacienteDAO;
import com.hospital.model.Paciente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/pacientes") // Ruta base para acceder al recurso
@Produces(MediaType.APPLICATION_JSON) // Todas las respuestas serán JSON
@Consumes(MediaType.APPLICATION_JSON) // Todas las peticiones deben enviar JSON
public class PacienteResource {

    // DAO encargado de todas las operaciones con la BD
    private final PacienteDAO dao = new PacienteDAO();

    // ---------------------------------------------------------------------
    // GET /pacientes → LISTAR TODOS
    // ---------------------------------------------------------------------
    @GET
    public Response listar() {
        try {
            List<Paciente> lista = dao.findAll();
            return Response.ok(lista).build(); // 200 OK
        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Error al listar pacientes: " + e.getMessage())
                    .build();
        }
    }

    // ---------------------------------------------------------------------
    // POST /pacientes → CREAR PACIENTE
    // ---------------------------------------------------------------------
    @POST
    public Response crear(Paciente p) {
        try {
            // Validación mínima antes de registrar
            if (p.getNombre() == null || p.getCorreo() == null ||
                    p.getDireccion() == null || p.getCedula() == null ||
                    p.getEdad() == null || p.getEdad() <= 0) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos incompletos o inválidos")
                        .build();
            }

            Paciente creado = dao.create(p);
            return Response.ok(creado).build(); // 200 OK con el paciente creado

        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Error al crear paciente: " + e.getMessage())
                    .build();
        }
    }

    // ---------------------------------------------------------------------
    // POST /pacientes/editar → EDITAR PACIENTE
    // ---------------------------------------------------------------------
    @POST
    @Path("/editar")
    public Response editar(Paciente p) {
        try {
            // Se requiere el ID para editar
            if (p.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID es obligatorio para editar")
                        .build();
            }

            boolean actualizado = dao.update(p);

            if (!actualizado) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Paciente no existe")
                        .build();
            }

            return Response.ok(p).build(); // 200 OK con datos actualizados

        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Error al editar: " + e.getMessage())
                    .build();
        }
    }

    // ---------------------------------------------------------------------
    // POST /pacientes/estado → ACTIVAR / DESACTIVAR
    // ---------------------------------------------------------------------
    @POST
    @Path("/estado")
    public Response cambiarEstado(Paciente p) {
        try {
            boolean ok = dao.updateEstado(p.getId(), p.isActivo());

            if (!ok) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Paciente no existe")
                        .build();
            }

            return Response.ok()
                    .entity("Estado actualizado")
                    .build();

        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Error al cambiar estado: " + e.getMessage())
                    .build();
        }
    }

    // ---------------------------------------------------------------------
    // DELETE /pacientes/{id} → ELIMINAR PACIENTE
    // ---------------------------------------------------------------------
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            boolean eliminado = dao.delete(id);

            if (!eliminado) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Paciente no existe")
                        .build();
            }

            return Response.ok()
                    .entity("Paciente eliminado correctamente")
                    .build();

        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Error al eliminar: " + e.getMessage())
                    .build();
        }
    }
}
