package resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.PostRepository;
import repository.StreamRepository;
import repository.UserRepository;

import java.time.LocalDateTime;

import entity.Post;
import entity.PostDTO;
import entity.Stream;
import entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST API resource for managing posts.
 * Provides endpoints for creating and retrieving posts in the application.
 */
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    PostRepository postRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    StreamRepository streamRepository;

    /**
     * Auxiliary class for handling post creation requests.
     * Contains fields for username, content, and stream title.
     */
    public static class PostRequest {
        public String username;
        public String content;
        public String streamTitle;
    }

    /**
     * Retrieves a list of all posts, returning a simplified view with PostDTO objects.
     *
     * @return a list of PostDTOs containing the content, username, and stream title for each post
     */
    @GET
    public List<PostDTO> list() {
        return postRepository.listAll().stream()
            .map(post -> new PostDTO(post.getContent(), post.getUser().getUsername(), post.getStream().getTitle()))
            .collect(Collectors.toList());
    }

    /**
     * Creates a new post based on the provided request data.
     * Validates the presence of content and stream title, and checks if the user and stream exist.
     *
     * @param request the request containing username, content, and stream title
     * @return a Response indicating success or failure of post creation
     */
    @POST
    @Transactional
    public Response create(PostRequest request) {
        if (request.content == null || request.content.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El contenido del post no puede estar vacío")
                    .build();
        }

        if (request.streamTitle == null || request.streamTitle.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El streams del post no puede estar vacío")
                    .build();
        }

        User user = userRepository.find("username", request.username).firstResult();
        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El usuario no se encuentra registrado");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
        Stream stream = streamRepository.find("title", request.streamTitle).firstResult();
        if (stream == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El hilo no existe");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }


        Post post = new Post();
        post.setContent(request.content);
        post.setUser(user);
        post.setStream(stream);
        post.setCreationDate(LocalDateTime.now());
        postRepository.persist(post);

        PostDTO responseDTO = new PostDTO(user.getUsername(), post.getContent(), stream.getTitle());

        return Response.status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }
}



