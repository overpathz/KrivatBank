package dao;

import entity.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final String SAVE_QUERY = "insert into users (username, password, balance, card_no) values (?,?,?,?);";
    private static final String CHECK_USER_QUERY = "select * from users where username = (?)";

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void save(User entity) {

        try(var connection = ConnectionManager.get();) {

            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, 500);
            preparedStatement.setString(4, "");
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isUserExist(User user) {

        try(Connection connection = ConnectionManager.get();){
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_QUERY);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            return next;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean checkData(User user) {
        User other = null;

        try(Connection connection = ConnectionManager.get();){
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_QUERY);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                other = new User();
                other.setUsername(resultSet.getString("username"));
                other.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user.getUsername().equals(other.getUsername())
                && user.getPassword().equals(other.getPassword());
    }
}
