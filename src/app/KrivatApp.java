package app;

import context.AppContext;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.AppContextBuffer;
import util.SceneFxmlLoader;

public class KrivatApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initializeAppContext(primaryStage);

        Parent root = SceneFxmlLoader.loadFxml("Login");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setAlwaysOnTop(true);
    }

    void initializeAppContext(Stage primaryStage) {

        AppContext appContext = new AppContext();
        appContext.setApplication(this);
        appContext.setPrimaryStage(primaryStage);

        AppContextBuffer.setAppContext(appContext);
    }
}
