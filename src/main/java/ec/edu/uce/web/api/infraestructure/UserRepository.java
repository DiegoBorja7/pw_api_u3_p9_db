package ec.edu.uce.web.api.infraestructure;

import ec.edu.uce.web.api.domain.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
