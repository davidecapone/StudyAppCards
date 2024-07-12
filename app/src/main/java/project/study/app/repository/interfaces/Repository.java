package project.study.app.repository.interfaces;

import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * Repository interface for managing entities.
 * This interface abstracts data operations and provides API for data access.
 *
 * @param <T> The type of the entity.
 * @param <K> The type of the key.
 */
public interface Repository<T, K> {

    /**
     * Insert a new entity into the database.
     *
     * @param entity The entity to be inserted.
     */
    void insert( T entity );

    /**
     * Get an entity by its key.
     *
     * @param name The name of the entity to be retrieved.
     * @return The entity with the given key.
     */
    LiveData<T> getEntityByName(K name);

    /**
     * Get all entities from the database.
     *
     * @return A list of all entities.
     */
    LiveData<List<T>> getAllEntities();

    /**
     * Update an existing entity in the database.
     *
     * @param entity The entity to be updated.
     */
    void update( T entity );

    /**
     * Delete an entity from the database.
     *
     * @param entity The entity to be deleted.
     */
    void delete( T entity );

    /**
     * Delete all entities from the database.
     */
    void deleteAll();
}
