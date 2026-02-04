package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.UserService;
import ec.edu.uce.web.api.application.representation.UserRepresentation;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class UserResource {
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<UserRepresentation>> getAllUsers() {
        return userService.findAll();
    }

    @Path("/validar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> validateUser(UserRepresentation userlogin) {
        return userService.validateUser(userlogin.username, userlogin.password)
                .map(user -> {
                    if (user != null) {
                        return Response.ok(java.util.Map.of("valid", true, "rol", user.role)).build();
                    } else {
                        return Response.ok(java.util.Map.of("valid", false)).build();
                    }
                });
    }
}
