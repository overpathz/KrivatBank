package context;

import entity.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppContext {

    private volatile Application application;
    private volatile Stage primaryStage;
    private volatile Page pages;
    private volatile User currentUser;

    public AppContext() {
        this.pages = new Page();
        this.currentUser = new User();
    }

    public synchronized Application getApplication() {
        return application;
    }

    public synchronized void setApplication(Application application) {
        this.application = application;
    }

    public synchronized Stage getPrimaryStage() {
        return primaryStage;
    }

    public Page getPages() {
        return pages;
    }

    public void setPages(Page pages) {
        this.pages = pages;
    }

    public synchronized void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
