package id.seruput.core.data.user;

import id.seruput.api.database.OperationHelper;
import id.seruput.api.user.User;
import id.seruput.api.user.UserGender;
import id.seruput.api.user.UserId;
import id.seruput.api.user.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOperationHelper implements OperationHelper<User, UserId> {

    @Override
    public User deserialize(ResultSet rs) throws SQLException {
        return UserImpl.builder()
                .id(UserId.of(rs.getString("userID")))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone_num"))
                .gender(UserGender.fromName(rs.getString("gender")).orElseThrow())
                .role(UserRole.fromName(rs.getString("role")).orElseThrow())
                .build();

    }

    @Override
    public PreparedStatement selectAll(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM user");
    }

    @Override
    public PreparedStatement selectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM user WHERE `userID` = ?");
    }

    @Override
    public PreparedStatement insertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO user (`userID`, `username`, `password`, `role`, `address`, `phone_num`, `gender`) VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "DELETE FROM user WHERE `userID` = ?");
    }

    @Override
    public PreparedStatement updateStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "UPDATE user SET `username` = ?, `password` = ?, `role` = ?, `address` = ?, `phone_num` = ?, `gender` = ? WHERE `userID` = ?");
    }

    /**
     * Set prepared statement for select. To use this method, you must set the parameter in the same order as the
     * parameter in the query:
     *
     * <pre>
     * `userID` = ?
     * </pre>
     *
     * @param preparedStatement prepared statement
     * @param user user
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.id().asString());
    }

    /**
     * Set prepared statement for select. To use this method, you must set the parameter in the same order as the
     * parameter in the query:
     *
     * <pre>
     *     `userID` = ?
     * </pre>
     *
     * @param preparedStatement prepared statement
     * @param id {@link UserId}
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, UserId id) throws SQLException {
        preparedStatement.setString(1, id.asString());
    }

    /**
     * Set prepared statement for insert. To use this method, you must set the parameter in the same order as the
     * parameter in the query:
     *
     * <pre>
     * `userID` = ?, `username` = ?, `password` = ?, `role` = ?, `address` = ?, `phone_num` = ?, `gender` = ?
     * </pre>
     *
     * @param preparedStatement prepared statement
     * @param user user
     * @throws SQLException if a database access error occurs
     */
    public void setInsertPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.id().asString());
        preparedStatement.setString(2, user.username());
        preparedStatement.setString(3, user.password());
        preparedStatement.setString(4, user.role().toString());
        preparedStatement.setString(5, user.address());
        preparedStatement.setString(6, user.phone());
        preparedStatement.setString(7, user.gender().toString());
    }

    /**
     * Set prepared statement for delete. To use this method, you must set the parameter in the same order as the
     * parameter in the query:
     *
     * <pre>
     * `userID` = ?
     * </pre>
     *
     * @param preparedStatement prepared statement
     * @param user user
     * @throws SQLException if a database access error occurs
     */
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.id().asString());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, UserId id) throws SQLException {
        preparedStatement.setString(1, id.asString());
    }

    /**
     * Set prepared statement for update. To use this method, you must set the parameter in the same order as the
     * parameter in the query:
     *
     * <pre>
     * `username` = ?, `password` = ?, `role` = ?, `address` = ?, `phone_num` = ?, `gender` = ? WHERE `userID` = ?
     * </pre>
     *
     * @param preparedStatement prepared statement
     * @param user user
     * @throws SQLException if a database access error occurs
     */
    public void setUpdatePreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.username());
        preparedStatement.setString(2, user.password());
        preparedStatement.setString(3, user.role().getName());
        preparedStatement.setString(4, user.address());
        preparedStatement.setString(5, user.phone());
        preparedStatement.setString(6, user.gender().getName());
    }

}
