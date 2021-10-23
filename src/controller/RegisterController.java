package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import context.AppContext;
import dao.UserDao;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.AppContextBuffer;
import util.FormValidator;

public class RegisterController {

    @FXML
    private PasswordField confirmPswdField;

    @FXML
    private PasswordField pswdField;

    @FXML
    private Text pswdsNotSimilarText;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTextEntry;

    private final ExecutorService exService = Executors.newFixedThreadPool(5);
    private final AppContext appContext = AppContextBuffer.getAppContext();
    private final Stage primaryStage = appContext.getPrimaryStage();
    private final UserDao userDao = new UserDao();

    @FXML
    void login() {
        primaryStage.setScene(appContext.getPages().get("Login"));
    }

    @FXML
    void register() {

        String username = usernameTextEntry.getText();
        String password = pswdField.getText();
        String confirmedPassword = confirmPswdField.getText();

        User user = new User(username, password);

        if (FormValidator.checkRegForm(username, password, confirmedPassword)) {
            userDao.save(user);
            primaryStage.setScene(appContext.getPages().get("Login"));
        } else {
            showPswdsNotSimilar();
        }
    }

    @FXML
    void initialize() {

    }

    void showPswdsNotSimilar() {
        LoginController.showError(exService, registerBtn, pswdsNotSimilarText);
    }

}
