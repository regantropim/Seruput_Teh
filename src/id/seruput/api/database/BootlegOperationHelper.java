package id.seruput.api.database;

import id.seruput.api.util.FakeOption;

import java.util.ArrayList;
import java.util.List;

public abstract class BootlegOperationHelper<T extends Entity<K>, K> implements OperationHelper<T, K> {

    private final List<T> tCache = new ArrayList<>();

    protected final FakeOption<T> findInCache(K id) {
        for (T object : tCache) {
            if (object.primaryKey().equals(id)) {
                return FakeOption.of(object);
            }
        }

        return FakeOption.empty();
    }

    protected final T cacheOrCreate(T t) {
        FakeOption<T> cached = findInCache(t.primaryKey());
        if (cached.isPresent()) {
            return cached.get();
        } else {
            cache(t);
            return t;
        }
    }

    protected final void cache(T t) {
        tCache.add(t);
    }

}
