package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.domain.Student;
import ec.edu.uce.web.api.infraestructure.StudentRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {
    // Inyeccion de dependencias
    @Inject
    StudentRepository studentRepository;

    // Metodos de negocio
    public Uni<List<Student>> findAll() {
        return this.studentRepository.listAll().map(items -> (List<Student>) items);
    }

}
