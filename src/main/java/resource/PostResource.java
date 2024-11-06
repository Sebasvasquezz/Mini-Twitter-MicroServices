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

    // Clase auxiliar para recibir el payload con username y contenido
    public static class PostRequest {
        public String username;
        public String contenido;
        public String streamTitle;
    }

    @GET
    public List<PostDTO> list() {
        return postRepository.listAll().stream()
            .map(post -> new PostDTO(post.getContenido(), post.getUser().getUsername(), post.getStream().getTitulo()))
            .collect(Collectors.toList());
    }

    
    @POST
    @Transactional
    public Response create(PostRequest request) {
        if (request.contenido == null || request.contenido.isBlank()) {
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
        Stream stream = streamRepository.find("titulo", request.streamTitle).firstResult();
        if (stream == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El hilo no existe");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }


        Post post = new Post();
        post.setContenido(request.contenido);
        post.setUser(user);
        post.setStream(stream);
        post.setFechaCreacion(LocalDateTime.now());
        postRepository.persist(post);

        PostDTO responseDTO = new PostDTO(user.getUsername(), post.getContenido(), stream.getTitulo());

        System.out.println("Todo ok");
        return Response.status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }
}



