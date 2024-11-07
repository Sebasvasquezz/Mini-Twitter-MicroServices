package repository;

import entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing User entities.
 * Provides CRUD operations and query methods for User entities
 * using PanacheRepository, which simplifies data access in Quarkus.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}

