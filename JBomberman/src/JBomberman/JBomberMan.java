package JBomberman;

import JBomberman.Model.Model;
import JBomberman.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JBomberMan extends Application {

    private static Stage stage;
    private static final Model model = new Model();
    private static final View view = new View();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        //System.out.println(System.getProperty("user.dir"));
        JBomberMan.stage = stage;
        model.addObserver(view);
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene scene = new Scene(root, 533, 400);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("JBomberMan");
        stage.show();
    }

    public static Model getModel(){
        return model;
    }
    public static Stage getStage() {
        return stage;
    }
}