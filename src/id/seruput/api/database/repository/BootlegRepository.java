package id.seruput.api.database.repository;

import id.seruput.api.database.Database;
import id.seruput.api.database.Entity;
import id.seruput.api.database.OperationHelper;
import id.seruput.api.database.pool.PooledConnection;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BootlegRepository<T extends Entity<K>, K> implements Repository<T, K> {

    protected final Database database;
    protected final OperationHelper<T, K> operationHelper;

    public BootlegRepository(Database database, OperationHelper<T, K> operationHelper) {
        this.database = database;
        this.operationHelper = operationHelper;
    }

    /**
     * Find an object by its primary key
     *
     * @param id the primary key
     * @return {@link Optional} of {@link T}
     */
    @Override
    public Optional<T> findById(K id) {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.selectStatement(pool.connection())) {
            operationHelper.setSelectPreparedStatement(statement, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(deserialize(rs));
            }
            return Optional.empty();
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> find(T object) {
        return findById(object.primaryKey());
    }

    @Override
    public List<T> findAll() {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.selectAll(pool.connection())) {

            ResultSet rs = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(deserialize(rs));
            }
            return result;
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T insert(T object) {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.insertStatement(pool.connection())) {
            operationHelper.setInsertPreparedStatement(statement, object);
            statement.executeUpdate();
            return object;
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T object) {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.deleteStatement(pool.connection())) {
            operationHelper.setDeletePreparedStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T object) {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.updateStatement(pool.connection())) {
            operationHelper.setUpdatePreparedStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T save(T object) {
        try (PooledConnection pool = database.fromPool();
             PreparedStatement statement = operationHelper.updateOrInsertStatement(pool.connection())) {
            operationHelper.setUpdateOrInsertPreparedStatement(statement, object);
            statement.executeUpdate();
            return object;
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected T deserialize(ResultSet resultSet) throws SQLException {
        return operationHelper.deserialize(resultSet);
    }

}
