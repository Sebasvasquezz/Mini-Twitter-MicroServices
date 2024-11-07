package repository;

import entity.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing Post entities.
 * Provides CRUD operations and query methods for Post entities
 * using PanacheRepository, which simplifies data access in Quarkus.
 */
@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
