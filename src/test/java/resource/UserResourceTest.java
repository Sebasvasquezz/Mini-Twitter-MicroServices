package resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
    }

    @Test
    public void testListUsers() {
        when(userRepository.listAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userResource.list();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setUsername("user3");
        newUser.setEmail("user3@example.com");

        userResource.create(newUser);

        verify(userRepository, times(1)).persist(newUser);
    }
}
