package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.SonService;
import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.application.representation.StudentRepresentation;
import ec.edu.uce.web.api.domain.Son;
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

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentService studentService;

    @Inject
    private SonService sonService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<StudentRepresentation>> getAllStudents() {
        return studentService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<StudentRepresentation> getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Uni<StudentRepresentation> getStudentById1(@PathParam("id") Long id) {
        return studentService.findById(id);
    }

    @GET
    @Path("/provincia")
    public Uni<List<StudentRepresentation>> getStudentsByProvince(@QueryParam("province") String province) {
        return studentService.findByProvince(province);
    }

    @GET
    @Path("/provincia/genero")
    public Uni<List<StudentRepresentation>> getStudentsByProvinceAndGender(@QueryParam("province") String province,
            @QueryParam("gender") String gender) {
        return studentService.findByProvinceAndGender(province, gender);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<StudentRepresentation> createStudent(StudentRepresentation student) {
        return studentService.save(student);
    }

    // Hacer una versión alternativa del método createStudent que devuelva un
    // Response con el código de estado 201 Created
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createStudent1(StudentRepresentation student) {
        return studentService.save(student)
                .map(savedStudent -> Response.status(Response.Status.CREATED).entity(savedStudent).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<StudentRepresentation> updateStudent(@PathParam("id") Long id, StudentRepresentation student) {
        return studentService.update(id, student);
    }

    @PUT
    @Path("actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateStudent1(@PathParam("id") Long id, StudentRepresentation student) {
        return studentService.update(id, student)
                .map(updatedStudent -> Response.status(209).entity(updatedStudent).build());
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> partialUpdateStudent(@PathParam("id") Long id, StudentRepresentation student) {
        return studentService.partialUpdate(id, student);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteStudent(@PathParam("id") Long id) {
        return studentService.delete(id);
    }

    @GET
    @Path("/{studentId}/hijos")
    public Uni<List<Son>> find(@PathParam("studentId") Long studentId) {
        return sonService.findByStudentId(studentId);
    }
}