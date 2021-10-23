package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import context.AppContext;
import dao.UserDao;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.AppContextBuffer;

public class PanelController {

    @FXML
    private Text balance;

    @FXML
    private Text cardNo;

    @FXML
    private Text profileUsername;

    @FXML
    private Text currentTime;

    @FXML
    private TextArea transactionHistory;

    @FXML
    private TextField usernameToSendField;

    @FXML
    private TextField amountField;

    private final AppContext appContext = AppContextBuffer.getAppContext();
    private final ExecutorService exService = Executors.newFixedThreadPool(10);
    private final Stage primaryStage = appContext.getPrimaryStage();
    private final UserDao userDao = new UserDao();

    @FXML
    void initialize() {

        User currentUser = appContext.getCurrentUser();

        profileUsername.setText(currentUser.getUsername());
        cardNo.setText(currentUser.getCardNo());

        updateBalance(currentUser);
        updateTransactionHistory(currentUser);

        runTime();
    }

    @FXML
    void logout() {
        primaryStage.setScene(appContext.getPages().get("Login"));
    }

    @FXML
    void sendMoney() {

        User currentUser = appContext.getCurrentUser();

        String usernameToSend = usernameToSendField.getText();
        int amountToSend = Integer.parseInt(amountField.getText());

        String currentUsername = currentUser.getUsername();
        int currentBalance = currentUser.getBalance();

        if (amountToSend <= currentBalance) {

            userDao.makeTransactionByUsernames(currentUsername, usernameToSend, amountToSend);

            updateBalance(userDao.getUserByUsername(currentUsername));
            updateTransactionHistory(userDao.getUserByUsername(currentUsername));

            clearSendFormFields();
        } else {
            System.out.println("You have no enough money!");
        }
    }

    void clearSendFormFields() {
        usernameToSendField.clear();
        amountField.clear();
    }

    @FXML
    void showInfo() {
        Stage infoStage = new Stage();
        infoStage.setScene(appContext.getPages().get("InfoPage"));
        infoStage.setResizable(false);
        infoStage.setOnCloseRequest(e -> primaryStage.show());
        infoStage.initStyle(StageStyle.UTILITY);
        primaryStage.hide();
        infoStage.show();
    }

    void updateBalance(User currentUser) {
        balance.setText(String.valueOf(currentUser.getBalance()));
    }

    void updateTransactionHistory(User currentUser) {
        transactionHistory.setText(currentUser.getTransactionHistory());
    }

    void runTime() {

        exService.execute(() -> {
            while (true) {
                LocalDateTime dateTime = LocalDateTime.now();
                String toSet = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                currentTime.setText(toSet);
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}