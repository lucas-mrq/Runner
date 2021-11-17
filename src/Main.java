//Lucas Marquet 1G1 TD1 TP2

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

        public void start(Stage primaryStage){

                primaryStage.setTitle("Runner");

                Group root = new Group();

                GameScene groupScene = new GameScene(500,250,root,400,800,3);

                primaryStage.setScene(groupScene);
                primaryStage.show();

        }
        public static void main(String[] args) {
                launch(args);


        }
}