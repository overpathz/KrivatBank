package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public final class SceneFxmlLoader {

    public static Parent loadFxml(String fxmlViewName) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects
                    .requireNonNull(SceneFxmlLoader.class
                            .getResource("../" + fxmlViewName + ".fxml")));
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }
}
