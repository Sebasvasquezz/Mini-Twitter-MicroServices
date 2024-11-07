package resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import entity.Stream;
import entity.StreamDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StreamRepository;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StreamResourceTest {

    @InjectMocks
    private StreamResource streamResource;

    @Mock
    private StreamRepository streamRepository;

    private Stream stream1;
    private Stream stream2;

    @BeforeEach
    void setUp() {
        stream1 = new Stream();
        stream1.setId(1L);
        stream1.setTitle("General");

        stream2 = new Stream();
        stream2.setId(2L);
        stream2.setTitle("Noticias");
    }

    @Test
    public void testListStreams() {
        when(streamRepository.listAll()).thenReturn(Arrays.asList(stream1, stream2));

        List<StreamDTO> streams = streamResource.list();

        assertEquals(2, streams.size());
        assertEquals("General", streams.get(0).getTitle());
        assertEquals("Noticias", streams.get(1).getTitle());
    }

    @Test
    public void testCreateStream() {
        Stream newStream = new Stream();
        newStream.setTitle("Deportes");

        streamResource.create(newStream);

        verify(streamRepository, times(1)).persist(newStream);
    }
}

