package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.domain.Student;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentService studentService;

    @GET
    public Uni<List<Student>> getAllStudents() {
        return studentService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
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
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Student>> getStudentsByProvinceAndGender(@QueryParam("province") String province,
            @QueryParam("gender") String gender) {
        return studentService.findByProvinceAndGender(province, gender);
    }

    @POST
    public Uni<Student> createStudent(Student student) {
        return studentService.save(student);
    }

    // Hacer una versión alternativa del método createStudent que devuelva un
    // Response con el código de estado 201 Created
    @POST
    @Path("/crear")
    public Response createStudent1(Student student) {
        this.studentService.save(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @PUT
    @Path("/{id}")
    public Uni<Student> updateStudent(@PathParam("id") Long id, Student student) {
        return studentService.update(id, student);
    }

    @PUT
    @Path("actualizar/{id}")
    public Response updateStudent1(@PathParam("id") Long id, Student student) {
        this.studentService.update(id, student);
        return Response.status(209).entity(student).build();
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