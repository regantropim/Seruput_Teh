package id.seruput.api.database;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

    Optional<T> findById(K id);

    Optional<T> find(T object);

    List<T> findAll();

    T insert(T object);

    void delete(T object);

    void update(T object);
}
