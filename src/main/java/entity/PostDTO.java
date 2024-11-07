package entity;

/**
 * Data Transfer Object (DTO) for transferring post data.
 * Contains simplified information about a post, including content, username of the post creator,
 * and the title of the associated stream, if any.
 */
public class PostDTO {
    public String content;
    public String username;
    public String streamTitle;

    /**
     * Constructs a PostDTO with specified content, username, and stream title.
     *
     * @param content the content of the post
     * @param username the username of the post creator
     * @param streamTitle the title of the associated stream, if any
     */
    public PostDTO(String content, String username, String streamTitle) {
        this.content = content;
        this.username = username;
        this.streamTitle = streamTitle;
    }

    // Getters y setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public void setStreamTitle(String streamTitle) {
        this.streamTitle = streamTitle;
    }
}

