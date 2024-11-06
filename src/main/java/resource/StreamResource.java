package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import repository.StreamRepository;
import entity.Stream;
import entity.StreamDTO;

@Path("/streams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamResource {

    @Inject
    StreamRepository streamRepository;

    @GET
    public List<StreamDTO> list() {
        return streamRepository.listAll().stream()
            .map(stream -> new StreamDTO(stream.getId(), stream.getTitulo()))
            .collect(Collectors.toList());
    }

    @POST
    public void create(Stream stream) {
        streamRepository.persist(stream);
    }
}

