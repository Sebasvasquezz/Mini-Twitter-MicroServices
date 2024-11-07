package entity;

/**
 * Data Transfer Object (DTO) for transferring stream data.
 * Contains simplified information about a stream, including its ID and title.
 */
public class StreamDTO {
    private Long id;
    private String title;

    /**
     * Constructs a StreamDTO with specified ID and title.
     *
     * @param id    the unique identifier of the stream
     * @param title the title of the stream
     */
    public StreamDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }
}

