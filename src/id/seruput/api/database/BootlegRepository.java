package id.seruput.api.database;

import java.sql.Connection;
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
        try (Connection connection = database.connection();
             PreparedStatement statement = operationHelper.selectStatement(connection)) {
            operationHelper.setSelectPreparedStatement(statement, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(deserialize(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> find(T object) {
        return findById(object.primaryKey());
    }

    @Override
    public List<T> findAll() {
        try (Connection connection = database.connection();
             PreparedStatement statement = operationHelper.selectAll(connection)) {

            ResultSet rs = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(deserialize(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T insert(T object) {
        try (Connection connection = database.connection();
             PreparedStatement statement = operationHelper.insertStatement(connection)) {
            operationHelper.setInsertPreparedStatement(statement, object);
            statement.executeUpdate();
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T object) {
        try (Connection connection = database.connection();
             PreparedStatement statement = operationHelper.deleteStatement(connection)) {
            operationHelper.setDeletePreparedStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T object) {
        try (Connection connection = database.connection();
             PreparedStatement statement = operationHelper.updateStatement(connection)) {
            operationHelper.setUpdatePreparedStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected T deserialize(ResultSet resultSet) throws SQLException {
        return operationHelper.deserialize(resultSet);
    }

}
