import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameScene extends Scene{
    private static staticThing leftB;
    private static staticThing rightB;
    private static Hero player;
    private static Camera cam;
    private static double gsX;

    private double x;

    private double past;

    public GameScene(double gsX, double gsY, Group root, double length, double height, double vitesse) {
        super(root, height, length);

        this.cam = new Camera(gsX, gsY);

        this.gsX=gsX;
        this.leftB = new staticThing("desert.png",0,0, 800-gsX,0);
        this.rightB = new staticThing("desert.png",gsX,0, 0,0);

        staticThing heart1 = new staticThing("heart.png",20,20,0,0 );
        staticThing heart2 = new staticThing("heart.png",50,20,0,0 );
        staticThing heart3 = new staticThing("heart.png",80,20,0,0 );

        root.getChildren().add(leftB.getImageView());
        root.getChildren().add(rightB.getImageView());
        root.getChildren().add(heart1.getImageView());
        root.getChildren().add(heart2.getImageView());
        root.getChildren().add(heart3.getImageView());

        this.player = new Hero(200,gsY,0,0,6);
        root.getChildren().add(player.getAnimatedView());


    /*    root.setOnKeyPressed(new EventHandler<KeyEvent>() { //Code permettant de récupérer l'information d'un appuie de touche
            @Override
            public void handle(KeyEvent ke) {
                System.out.println(ke.getCode());
                if (ke.getCode() == KeyCode.SPACE) { // Si appuie de la touche SPACE
                    System.out.println("Jump");
                    //Hero.jump(); //Permet de sauter
                }
            }
        });*/

        this.setOnKeyPressed( (event)->{
            System.out.println(event);
            String a = event.getText();
            System.out.println("-"+a+"-");
            System.out.println(a=="a");
            if(a=="a"){
                //player.jump();
                System.out.println("OUIIIIII");
            }
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                double time = (now - past) * Math.pow(10,  -9);
                if(time>50* Math.pow(10,  -9)){
                    cam.update(time,player.getX());
                    player.update(time,cam.getX());
                    GameScene.update(time,cam.getX());
                    past = now;
                }
            }
        };

        timer.start();
    }

    public static void update(double time, double x){
        double newX = cam.getX()%800;
        double camX = 800-newX;
        rightB.setX(camX);
        leftB.setCamX(newX);
    }

}
