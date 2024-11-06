package repository;

import entity.Stream;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StreamRepository implements PanacheRepository<Stream> {
}
