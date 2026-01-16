package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.domain.Student;
import ec.edu.uce.web.api.infraestructure.StudentRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {
    @Inject
    StudentRepository studentRepository;

    @WithTransaction
    public Uni<List<Student>> findAll() {
        return studentRepository.listAll();
    }
}
