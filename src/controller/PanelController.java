package controller;

import java.net.URL;
import java.util.ResourceBundle;

import context.AppContext;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import util.AppContextBuffer;

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

    AppContext appContext = AppContextBuffer.getAppContext();

    @FXML
    void initialize() {

        User currentUser = appContext.getCurrentUser();

        profileUsername.setText(currentUser.getUsername());
        cardNo.setText(currentUser.getCardNo());
        balance.setText(String.valueOf(currentUser.getBalance()));
    }

}
