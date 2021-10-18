package controller;

import context.AppContext;
import dao.UserDao;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.AppContextBuffer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField pswdField;

    @FXML
    private Text pswdsNotSimilarText;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameTextEntry;

    private final ExecutorService exService = Executors.newFixedThreadPool(5);
    private AppContext appContext = AppContextBuffer.getAppContext();
    private Stage primaryStage = appContext.getPrimaryStage();
    private UserDao userDao = new UserDao();

    @FXML
    void login(ActionEvent event) {

        String username = usernameTextEntry.getText();
        String password = pswdField.getText();

        User user = new User(username, password, 0, "");

        if (!userDao.isUserExist(user)) {
            showUserNotExist();
        } else {
            if (userDao.checkData(user)) {
                appContext.getCurrentUser().setData(user);
                primaryStage.setScene(appContext.getPages().getPage("PersonalPanel"));
            } else {
                showUserNotExist();
            }
        }
    }

    @FXML
    void initialize() {

    }

    void showUserNotExist() {
        showError(exService, loginBtn, pswdsNotSimilarText);
    }

    static void showError(ExecutorService exService, Button loginBtn, Text pswdsNotSimilarText) {
        exService.execute(()-> {
            loginBtn.setVisible(false);
            pswdsNotSimilarText.setVisible(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pswdsNotSimilarText.setVisible(false);
            loginBtn.setVisible(true);
        });
    }

}

