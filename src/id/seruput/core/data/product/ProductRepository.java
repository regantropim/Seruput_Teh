package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.database.Database;
import id.seruput.api.database.OperationHelper;
import id.seruput.api.database.pool.PooledConnection;
import id.seruput.api.database.repository.BootlegRepository;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductRepository extends BootlegRepository<Product, ProductId> {

    public ProductRepository(Database database, OperationHelper<Product, ProductId> operationHelper) {
        super(database, operationHelper);
    }

    public Optional<Product> findByName(String name) {
        try (PooledConnection c = database.fromPool();
             PreparedStatement statement = c.connection().prepareStatement(
                     "SELECT * FROM product WHERE product_name = ?"
             )) {
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(operationHelper.deserialize(rs));
            }
            return Optional.empty();
        } catch (SQLException | EmptyConnectionPoolException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
