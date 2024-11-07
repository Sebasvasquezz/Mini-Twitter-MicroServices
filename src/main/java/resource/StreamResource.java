package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import repository.StreamRepository;
import entity.Stream;
import entity.StreamDTO;

/**
 * REST API resource for managing streams.
 * Provides endpoints for creating and retrieving streams in the application.
 */
@Path("/streams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamResource {

    @Inject
    StreamRepository streamRepository;

    /**
     * Retrieves a list of all streams, returning a simplified view with StreamDTO objects.
     *
     * @return a list of StreamDTOs containing the ID and title for each stream
     */
    @GET
    public List<StreamDTO> list() {
        return streamRepository.listAll().stream()
            .map(stream -> new StreamDTO(stream.getId(), stream.getTitle()))
            .collect(Collectors.toList());
    }

    /**
     * Creates a new stream based on the provided stream data.
     * 
     * @param stream the Stream entity to persist in the database
     */
    @POST
    public void create(Stream stream) {
        streamRepository.persist(stream);
    }
}

