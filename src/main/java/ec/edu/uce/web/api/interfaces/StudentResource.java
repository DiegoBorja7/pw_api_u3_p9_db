package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.domain.Student;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentService studentService;

    @GET
    @Path("/todos")
    public Uni<List<Student>> getAllStudents() {
        return studentService.findAll();
    }
}
