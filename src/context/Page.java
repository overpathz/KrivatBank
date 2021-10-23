package context;

import javafx.scene.Scene;
import util.SceneFxmlLoader;

public class Page {

    public Scene get(String viewName) {
        return new Scene(SceneFxmlLoader.loadFxml(viewName));
    }

}
