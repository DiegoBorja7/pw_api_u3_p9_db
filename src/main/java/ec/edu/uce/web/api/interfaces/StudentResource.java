package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.StudentService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/estudiantes")
public class StudentResource {
    // Inyeccion de dependencias
    @Inject
    private StudentService studentService;

    @GET
    @Path("/todos")
    @WithTransaction
    public Uni<List<?>> getAllStudents() {
        return this.studentService.findAll().cast(List.class);
    }
}
