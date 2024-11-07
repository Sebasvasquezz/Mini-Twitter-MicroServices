package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import entity.User;
import repository.UserRepository;

/**
 * REST API resource for managing users.
 * Provides endpoints for creating and retrieving users in the application.
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;

    /**
     * Retrieves a list of all users.
     *
     * @return a list of User entities
     */
    @GET
    public List<User> list() {
        return userRepository.listAll();
    }

    /**
     * Creates a new user based on the provided user data.
     *
     * @param user the User entity to persist in the database
     */
    @POST
    public void create(User user) {
        userRepository.persist(user);
    }
}

