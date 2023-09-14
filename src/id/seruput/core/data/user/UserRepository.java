package id.seruput.core.data.user;

import id.seruput.api.database.BootlegRepository;
import id.seruput.api.database.Database;
import id.seruput.api.user.User;
import id.seruput.api.user.UserId;
import id.seruput.api.user.UserRole;
import id.seruput.api.util.FakeOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends BootlegRepository<User, UserId> {

    public UserRepository(Database database) {
        super(database, new UserOperationHelper());
    }

    public UserId fetchHighestId(UserRole role) {
        try (Connection connection = database.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT userID FROM user WHERE role = ? ORDER BY userID DESC LIMIT 1")) {
            preparedStatement.setString(1, role.name());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return UserId.of(resultSet.getString("userID"));
            }
            return UserId.of("CU000");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch highest id", e);
        }

    }

    public FakeOption<User> findUserByUsername(String username) {
        try (Connection connection = database.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return FakeOption.of(deserialize(resultSet));
            }
            return FakeOption.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user by username", e);
        }
    }

}
