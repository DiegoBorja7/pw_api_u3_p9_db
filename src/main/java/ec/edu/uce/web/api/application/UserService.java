package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.application.representation.UserRepresentation;
import ec.edu.uce.web.api.domain.User;
import ec.edu.uce.web.api.infraestructure.UserRepository;
import io.smallrye.mutiny.Uni;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @WithSession
    public Uni<List<UserRepresentation>> findAll() {
        return userRepository.find("ORDER BY id ASC").list()
                .map(users -> users.stream()
                        .map(this::mapperToUser)
                        .toList());
    }

    @WithSession
    public Uni<UserRepresentation> validateUser(String username, String password) {
        return userRepository.find("username = ?1", username).firstResult()
                .map(user -> {
                    if (user != null && user.password.equals(password)) {
                        return mapperToUser(user);
                    }
                    return null;
                });
    }

    private UserRepresentation mapperToUser(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.id = user.id;
        userRepresentation.username = user.username;
        userRepresentation.password = user.password;
        userRepresentation.role = user.role;
        return userRepresentation;
    }

    private User mapperToUserRepresentation(UserRepresentation userRepresentation) {
        User user = new User();
        user.id = userRepresentation.id;
        user.username = userRepresentation.username;
        user.password = userRepresentation.password;
        user.role = userRepresentation.role;
        return user;
    }

}
