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
import jakarta.ws.rs.core.MediaType;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {
    @Inject
    private SubjectService subjectService;

    @GET
    @Path("/todos")
    public Uni<List<Subject>> getAllSubjects() {
        return subjectService.findAll();
    }

    @GET
    @Path("/buscarporid/{id}")
    public Uni<Subject> getSubjectById(@PathParam("id") Long id) {
        validateId(id);
        return subjectService.findById(id);
    }

    @POST
    @Path("/guardar")
    public Uni<Subject> createSubject(Subject subject) {
        validateBody(subject);
        return subjectService.save(subject);
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
    @Path("/actualizar/{id}")
    public Uni<Subject> updateSubject(@PathParam("id") Long id, Subject subject) {
        validateId(id);
        validateBody(subject);
        return subjectService.update(id, subject);
    }

    @PATCH
    @Path("/actualizarparcial/{id}")
    public Uni<Void> partialUpdateSubject(@PathParam("id") Long id, Subject subject) {
        validateId(id);
        validateBody(subject);
        return subjectService.partialUpdate(id, subject);
    }

    @GET
    @Path("/buscarporsemestre/{semester}")
    public Uni<List<Subject>> getSubjectsBySemester(@PathParam("semester") Integer semester) {
        // Service valida rango completo (1-10)
        return subjectService.findBySemester(semester);
    }

    @GET
    @Path("/buscarporaula/{classroom}")
    public Uni<List<Subject>> getSubjectsByClassroom(@PathParam("classroom") String classroom) {
        // Service valida que no esté vacío
        return subjectService.findByClassroom(classroom);
    }

    @DELETE
    @Path("/eliminar/{id}")
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
