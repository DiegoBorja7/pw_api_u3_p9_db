package ec.edu.uce.web.api.domain;

import java.time.LocalDateTime;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@SequenceGenerator(name = "students_seq", sequenceName = "estudiante_secuencia", allocationSize = 1)
public class Student extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq")
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime birthDay;

}
