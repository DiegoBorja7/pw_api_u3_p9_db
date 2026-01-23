package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.SubjectService;
import ec.edu.uce.web.api.domain.Subject;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
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

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {
    @Inject
    private SubjectService subjectService;

    @GET
    public Uni<List<Subject>> getAllSubjects() {
        return subjectService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Subject> getSubjectById(@PathParam("id") Long id) {
        validateId(id);
        return subjectService.findById(id);
    }

    @POST
    public Uni<Subject> createSubject(Subject subject) {
        validateBody(subject);
        return subjectService.save(subject);
    }

    // DEBER MENSAJE 201 CREATED
    @POST
    @Path("/crear")
    public Uni<Response> createSubject1(Subject subject) {
        validateBody(subject);
        return subjectService.save(subject)
                .map(savedSubject -> Response.status(Response.Status.CREATED).entity(savedSubject).build());
    }

    @POST
    @Path("/guardarmultiplesmaterias")
    public Uni<List<Subject>> createMultipleSubjects(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            throw new BadRequestException("La lista de materias es requerida y no puede estar vacía");
        }
        return subjectService.saveAll(subjects);
    }

    @PUT
    @Path("/{id}")
    public Uni<Subject> updateSubject(@PathParam("id") Long id, Subject subject) {
        validateId(id);
        validateBody(subject);
        return subjectService.update(id, subject);
    }

    @PATCH
    @Path("/{id}")
    public Uni<Void> partialUpdateSubject(@PathParam("id") Long id, Subject subject) {
        validateId(id);
        validateBody(subject);
        return subjectService.partialUpdate(id, subject);
    }

    // DEBER
    @PATCH
    @Path("/actualizar/{id}")
    public Uni<Response> partialUpdateSubject1(@PathParam("id") Long id, Subject subject) {
        validateId(id);
        validateBody(subject);
        return subjectService.partialUpdate(id, subject)
                .map(updatedSubject -> Response.status(209).entity(updatedSubject).build());
    }

    @GET
    @Path("/semestre")
    public Uni<List<Subject>> getSubjectsBySemester(@QueryParam("semester") Integer semester) {
        // Service valida rango completo (1-10)
        return subjectService.findBySemester(semester);
    }

    @GET
    @Path("/aula")
    public Uni<List<Subject>> getSubjectsByClassroom(@QueryParam("classroom") String classroom) {
        // Service valida que no esté vacío
        return subjectService.findByClassroom(classroom);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteSubject(@PathParam("id") Long id) {
        validateId(id);
        return subjectService.delete(id);
    }

    /**
     * Validación HTTP: ID debe ser un número positivo
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("ID inválido: debe ser un número positivo");
        }
    }

    /**
     * Validación HTTP: Body no puede ser null
     */
    private void validateBody(Subject subject) {
        if (subject == null) {
            throw new BadRequestException("Datos de la materia son requeridos");
        }
    }
}
