package dao;

import entity.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final String SAVE_QUERY = "insert into users (username, password, balance, card_no, transaction_history) values (?,?,?,?,?);";
    private static final String CHECK_USER_QUERY = "select username, password from users where username = (?)";
    private static final String GET_USER_BY_NAME = "select id, username, password, balance, card_no, transaction_history from users where username = (?)";
    private static final String WITHDRAW_MONEY_QUERY = "update users set balance = balance - (?) where username = (?);";
    private static final String ENROLL_MONEY_QUERY = "update users set balance = balance + (?) where username = (?);";
    private static final String ADD_TRANSACTION_TO_HISTORY = "update users set transaction_history = concat(transaction_history, (?)) where username = (?)";

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

        try(var connection = ConnectionManager.get()) {

            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, 500);
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isUserExist(User user) {

        try(Connection connection = ConnectionManager.get()){
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_QUERY);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean checkData(User user) {
        User other = null;

        try(Connection connection = ConnectionManager.get()){
            assert connection != null;
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

        assert other != null;
        return user.getUsername().equals(other.getUsername())
                && user.getPassword().equals(other.getPassword());
    }

    public User getUserByUsername(String username) {
        User user = null;

        try(Connection connection = ConnectionManager.get()) {

            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_NAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();

                int dbId = resultSet.getInt("id");
                String dbUsername = resultSet.getString("username");
                String dbPswd = resultSet.getString("password");
                int dbBalance = resultSet.getInt("balance");
                String dbCardNo = resultSet.getString("card_no");
                String dbTransactHist = resultSet.getString("transaction_history");

                user.setId(dbId);
                user.setUsername(dbUsername);
                user.setPassword(dbPswd);
                user.setBalance(dbBalance);
                user.setCardNo(dbCardNo);
                user.setTransactionHistory(dbTransactHist);

            }

            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    public void makeTransactionByUsernames(String currentUsername, String usernameToSend, int amountToSend) {

        Connection connection = ConnectionManager.get();

        try {
            assert connection != null;
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement withdraw_statement = connection.prepareStatement(WITHDRAW_MONEY_QUERY);
            PreparedStatement enroll_statement = connection.prepareStatement(ENROLL_MONEY_QUERY);

            withdraw_statement.setInt(1, amountToSend);
            withdraw_statement.setString(2, currentUsername);

            enroll_statement.setInt(1, amountToSend);
            enroll_statement.setString(2, usernameToSend);

            withdraw_statement.execute();
            enroll_statement.execute();

            addTransToHistory(usernameToSend, amountToSend, currentUsername, "To", connection);
            addTransToHistory(currentUsername, amountToSend, usernameToSend, "From", connection);

            connection.commit();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTransToHistory(String usernameToSend, int amount, String currentUser, String toOrFrom, Connection connection) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String timeToSet = localDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));

        String trans = """
                Date: %s
                %s: %s
                Amount: %s
                _______________
                """.formatted(timeToSet, toOrFrom, usernameToSend, amount);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TRANSACTION_TO_HISTORY);
            preparedStatement.setString(1, trans);
            preparedStatement.setString(2, currentUser);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}