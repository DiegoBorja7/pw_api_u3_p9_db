package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.domain.Son;
import ec.edu.uce.web.api.infraestructure.SonRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SonService {
    @Inject
    SonRepository sonRepository;

    public Uni<List<Son>> findByStudentId(Long studentId) {
        return sonRepository.findByStudentId(studentId);
    }

}
