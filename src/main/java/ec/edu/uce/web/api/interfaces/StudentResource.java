package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.domain.Student;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentService studentService;

    @GET
    public Uni<List<Student>> getAllStudents() {
        return studentService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Student> getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id);
    }

    @GET
    @Path("/provincia")
    public Uni<List<Student>> getStudentsByProvince(@QueryParam("province") String province) {
        return studentService.findByProvince(province);
    }

    @GET
    @Path("/provincia/genero")
    public Uni<List<Student>> getStudentsByProvinceAndGender(@QueryParam("province") String province,
            @QueryParam("gender") String gender) {
        return studentService.findByProvinceAndGender(province, gender);
    }

    @POST
    public Uni<Student> createStudent(Student student) {
        return studentService.save(student);
    }

    @PUT
    @Path("/{id}")
    public Uni<Student> updateStudent(@PathParam("id") Long id, Student student) {
        return studentService.update(id, student);
    }

    @PATCH
    @Path("/{id}")
    public Uni<Void> partialUpdateStudent(@PathParam("id") Long id, Student student) {
        return studentService.partialUpdate(id, student);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteStudent(@PathParam("id") Long id) {
        return studentService.delete(id);
    }

}