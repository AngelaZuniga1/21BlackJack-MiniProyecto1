package co.edu.univalle.poe.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackjackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackApplication.class.getResource("view/blackjack-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 620);
        stage.setTitle("21Blackjack");
        stage.setScene(scene);
        stage.show();
    }
}
