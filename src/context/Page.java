package context;

import javafx.scene.Parent;
import javafx.scene.Scene;
import util.SceneFxmlLoader;

public class Page {

    public Scene getPage(String viewName) {
        return new Scene(SceneFxmlLoader.loadFxml(viewName));
    }
}
