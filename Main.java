//Lucas Marquet 1G1 TD1 TP2

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

        public void start(Stage primaryStage) throws IOException {

                primaryStage.setTitle("Runner Lucas Marquet");
                primaryStage.getIcons().add(new Image("logo.png"));
                primaryStage.setResizable(false);

                Group root = new Group();

                GameScene groupScene = new GameScene(500,250,root,400,800);

                primaryStage.setScene(groupScene);

                primaryStage.show();

        }
        public static void main(String[] args) {

                launch(args);
        }
}