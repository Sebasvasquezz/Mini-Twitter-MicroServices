package resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import entity.Post;
import entity.PostDTO;
import entity.Stream;
import entity.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.PostRepository;
import repository.StreamRepository;
import repository.UserRepository;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PostResourceTest {

    @InjectMocks
    private PostResource postResource;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StreamRepository streamRepository;

    @Mock
    private PanacheQuery<User> userQuery;

    @Mock
    private PanacheQuery<Stream> streamQuery;


    private User mockUser;
    private Stream mockStream;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUsername("user1");
        
        mockStream = new Stream();
        mockStream.setTitle("General");

        mockPost = new Post();
        mockPost.setContent("Hello World");
        mockPost.setUser(mockUser);
        mockPost.setStream(mockStream);
    }

    @Test
    public void testListPosts() {
        when(postRepository.listAll()).thenReturn(Collections.singletonList(mockPost));

        List<PostDTO> posts = postResource.list();
        
        assertThat(posts, hasSize(1));
        assertThat(posts.get(0).getContent(), is("Hello World"));
        assertThat(posts.get(0).getUsername(), is("user1"));
        assertThat(posts.get(0).getStreamTitle(), is("General"));
    }

    @Test
    public void testCreatePost_Success() {
        when(userRepository.find("username", "user1")).thenReturn(userQuery);
        when(userQuery.firstResult()).thenReturn(new User());

        when(streamRepository.find("titulo", "General")).thenReturn(streamQuery);
        when(streamQuery.firstResult()).thenReturn(new Stream());

        PostResource.PostRequest request = new PostResource.PostRequest();
        request.username = "user1";
        request.content = "Hello World";
        request.streamTitle = "General";

        Response response = postResource.create(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}