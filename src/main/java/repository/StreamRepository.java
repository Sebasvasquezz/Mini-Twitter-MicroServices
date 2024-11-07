package repository;

import entity.Stream;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing Stream entities.
 * Provides CRUD operations and query methods for Stream entities
 * using PanacheRepository, which simplifies data access in Quarkus.
 */
@ApplicationScoped
public class StreamRepository implements PanacheRepository<Stream> {
}
