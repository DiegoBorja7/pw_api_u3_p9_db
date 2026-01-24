package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.application.representation.StudentRepresentation;
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
    public Uni<List<StudentRepresentation>> findAll() {
        return studentRepository.listAll()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    public Uni<StudentRepresentation> findById(Long id) {
        return studentRepository.findById(id)
                .map(this::mapperToStudent);
    }

    @WithTransaction
    public Uni<StudentRepresentation> save(StudentRepresentation studentRep) {
        Student student = mapperToStudentRepresentation(studentRep);
        return studentRepository.persist(student)
                .map(this::mapperToStudent);
    }

    @WithTransaction
    public Uni<StudentRepresentation> update(Long id, StudentRepresentation studentRep) {
        Student studentData = mapperToStudentRepresentation(studentRep);
        return studentRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new jakarta.ws.rs.NotFoundException("Student not found");
                    }
                    existing.name = studentData.name;
                    existing.lastName = studentData.lastName;
                    existing.email = studentData.email;
                    existing.birthDay = studentData.birthDay;
                    return existing;
                })
                .chain(studentRepository::persistAndFlush)
                .map(this::mapperToStudent);
    }

    @WithTransaction
    public Uni<Void> partialUpdate(Long id, StudentRepresentation studentRep) {
        Student studentData = mapperToStudentRepresentation(studentRep);
        return studentRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new jakarta.ws.rs.NotFoundException("Student not found");
                    }
                    if (studentData.name != null) {
                        existing.name = studentData.name;
                    }
                    if (studentData.lastName != null) {
                        existing.lastName = studentData.lastName;
                    }
                    if (studentData.email != null) {
                        existing.email = studentData.email;
                    }
                    if (studentData.birthDay != null) {
                        existing.birthDay = studentData.birthDay;
                    }
                    return existing;
                })
                .chain(studentRepository::persistAndFlush)
                .replaceWithVoid();
    }

    @WithTransaction
    public Uni<Boolean> delete(Long id) {
        return studentRepository.deleteById(id);
    }

    public Uni<List<StudentRepresentation>> findByProvince(String province) {
        return studentRepository.find("province", province).list()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    public Uni<List<StudentRepresentation>> findByProvinceAndGender(String province, String gender) {
        return studentRepository.find("province = ?1 and gender = ?2", province, gender).list()
                .map(students -> students.stream()
                        .map(this::mapperToStudent)
                        .toList());
    }

    private StudentRepresentation mapperToStudent(Student student) {
        StudentRepresentation studentRep = new StudentRepresentation();
        studentRep.id = student.id;
        studentRep.name = student.name;
        studentRep.lastName = student.lastName;
        studentRep.email = student.email;
        studentRep.birthDay = student.birthDay;
        studentRep.province = student.province;
        studentRep.gender = student.gender;
        return studentRep;
    }

    private Student mapperToStudentRepresentation(StudentRepresentation studentRep) {
        Student student = new Student();
        student.id = studentRep.id;
        student.name = studentRep.name;
        student.lastName = studentRep.lastName;
        student.email = studentRep.email;
        student.birthDay = studentRep.birthDay;
        student.province = studentRep.province;
        student.gender = studentRep.gender;
        return student;
    }
}
