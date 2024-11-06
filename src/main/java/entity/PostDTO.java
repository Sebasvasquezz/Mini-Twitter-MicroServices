package entity;

public class PostDTO {
    public String contenido;
    public String username;
    public String streamTitle;

    public PostDTO(String contenido, String username, String streamTitle) {
        this.contenido = contenido;
        this.username = username;
        this.streamTitle = streamTitle;
    }
}

