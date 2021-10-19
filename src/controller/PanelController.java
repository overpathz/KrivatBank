package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import context.AppContext;
import dao.UserDao;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.AppContextBuffer;
import util.SceneFxmlLoader;

public class PanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text balance;

    @FXML
    private Text cardNo;

    @FXML
    private Text profileUsername;

    @FXML
    private Text currentTime;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea transactionHistory;

    @FXML
    private TextField usernameToSendField;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField amountField;

    AppContext appContext = AppContextBuffer.getAppContext();
    ExecutorService exService = Executors.newFixedThreadPool(10);
    Stage primaryStage = appContext.getPrimaryStage();
    UserDao userDao = new UserDao();

    @FXML
    void initialize() {

        User currentUser = appContext.getCurrentUser();

        profileUsername.setText(currentUser.getUsername());
        cardNo.setText(currentUser.getCardNo());

        updateBalance(currentUser);

        runTime();
    }

    @FXML
    void logout() {
        primaryStage.setScene(appContext.getPages().getPage("Login"));
    }

    @FXML
    void sendMoney(ActionEvent event) {

        User currentUser = appContext.getCurrentUser();

        String usernameToSend = usernameToSendField.getText();
        int amountToSend = Integer.parseInt(amountField.getText());

        String currentUsername = currentUser.getUsername();
        int currentBalance = currentUser.getBalance();

        System.out.println(usernameToSend);
        System.out.println(amountToSend);
        System.out.println(currentUsername);
        System.out.println(currentBalance);

        if (amountToSend <= currentBalance) {
            userDao.makeTransactionByUsernames(currentUsername, usernameToSend, amountToSend);
            updateBalance(userDao.getUserByUsername(currentUsername));
        }
    }

    void updateBalance(User currentUser) {
        balance.setText(String.valueOf(currentUser.getBalance()));
    }

    void runTime() {
        exService.execute(() -> {
            while (true) {
                LocalDateTime dateTime = LocalDateTime.now();
                String toSet = dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond();
                currentTime.setText(toSet);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
