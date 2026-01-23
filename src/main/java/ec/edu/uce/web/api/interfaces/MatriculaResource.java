package ec.edu.uce.web.api.interfaces;

import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.domain.Student;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoint para matriculación de estudiantes
 * Ruta: /api/v1/matricula/estudiantes/actualizar/{id}
 */
@Path("/api/v1/matricula/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class MatriculaResource {
    @Inject
    private StudentService studentService;

    @PUT
    @Path("/actualizar/{id}")
    public Uni<Student> actualizarEstudiante(@PathParam("id") Long id, Student student) {
        return studentService.update(id, student);
    }

}
