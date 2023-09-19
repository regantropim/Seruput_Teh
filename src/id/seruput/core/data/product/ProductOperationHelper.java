package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.database.BootlegOperationHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductOperationHelper extends BootlegOperationHelper<Product, ProductId> {

    @Override
    public Product update(ResultSet rs, Product product) throws SQLException {
        return product
                .name(rs.getString("product_name"))
                .description(rs.getString("product_des"))
                .price(rs.getLong("product_price"));
    }

    @Override
    public Product deserialize(ResultSet rs) throws SQLException {
        return ProductImpl.builder()
                .productId(ProductId.of(rs.getString("productID")))
                .productName(rs.getString("product_name"))
                .productPrice(rs.getLong("product_price"))
                .productDescription(rs.getString("product_des"))
                .build();
    }

    @Override
    public PreparedStatement selectAll(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM product"
        );
    }

    @Override
    public PreparedStatement selectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM product WHERE productID = ?"
        );
    }

    @Override
    public PreparedStatement insertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO product (productID, product_name, product_des, product_price) VALUE (?, ?, ?, ?)"
        );
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "DELETE FROM product WHERE productID = ?"
        );
    }

    @Override
    public PreparedStatement updateStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "UPDATE product SET product_name = ?, product_des = ?, product_price = ? WHERE productID = ?"
        );
    }

    @Override
    public PreparedStatement updateOrInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO product (productID, product_name, product_des, product_price) VALUE (?, ?, ?, ?) ON DUPLICATE KEY UPDATE product_name = ?, product_des = ?, product_price = ?"
        );
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        setSelectPreparedStatement(preparedStatement, product.productId());
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, ProductId id) throws SQLException {
        preparedStatement.setString(1, id.asString());
    }

    @Override
    public void setInsertPreparedStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.productId().asString());
        preparedStatement.setString(2, product.name());
        preparedStatement.setString(3, product.description());
        preparedStatement.setLong(4, product.price());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        setDeletePreparedStatement(preparedStatement, product.productId());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, ProductId id) throws SQLException {
        preparedStatement.setString(1, id.asString());
    }

    @Override
    public void setUpdatePreparedStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.name());
        preparedStatement.setString(2, product.description());
        preparedStatement.setLong(3, product.price());
        preparedStatement.setString(4, product.productId().asString());
    }

    @Override
    public void setUpdateOrInsertPreparedStatement(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.productId().asString());
        preparedStatement.setString(2, product.name());
        preparedStatement.setString(3, product.description());
        preparedStatement.setLong(4, product.price());
        preparedStatement.setString(5, product.name());
        preparedStatement.setString(6, product.description());
        preparedStatement.setLong(7, product.price());
    }
}
