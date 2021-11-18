import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.concurrent.atomic.AtomicReference;

public class GameScene extends Scene{
    private static staticThing leftB;
    private static staticThing rightB;
    private static Hero player;
    private static Eagle eagle;
    private static Cactus cactus;
    private static Camera cam;
    private static double gsX;
    private static boolean pause = false;
    private static double compteurVitesse = 5000000;

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

        this.player = new Hero(800,gsY,0,0,6);
        root.getChildren().add(player.getAnimatedView());

        this.eagle = new Eagle(-1000000,gsY-50,0);
        root.getChildren().add(eagle.getAnimatedView());

        this.cactus = new Cactus(-1000000,gsY+70);
        root.getChildren().add(cactus.getAnimatedView());

        this.setOnKeyPressed( (event)->{
            String keyKey = event.getText();
            String spaceKey = " ";
            String pKey = "p";
            String eKey = "e";
            String cKey = "c";
            String vKey = "v";
            char key = keyKey.charAt(0);
            char space = spaceKey.charAt(0);
            char p = pKey.charAt(0);
            char e = eKey.charAt(0);
            char c = cKey.charAt(0);
            char v = vKey.charAt(0);
            if (player.getAttitude()==0){
                if(key==space){
                    player.jump();
                }
            }
            if(key==p){
                pause = !pause;
            }
            if(key==e){
                eagle.setX(800);
            }
            if(key==c){
                cactus.setX(800);
            }
            if(key==v){
                cam.setVX(cam.getVX()+1);
            }
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(player.checkObstacle(eagle.getX(),cam.getX())){
                    pause=true;
                }
                if(!pause) {
                    double timeInt = now - past;
                    double time = (now - past) * Math.pow(10, -9);
                    if (timeInt > compteurVitesse) {
                        //System.out.println(compteurVitesse);
                        cam.update(time, player.getX());
                        eagle.update(time, cam.getX());
                        cactus.update(time, cam.getX());
                        player.update(time, cam.getX());
                        GameScene.update(time, cam.getX());
                        past = now;
                    }
                }
                else{
                    past = now;
                }
            }
        };

        timer.start();
    }

    public static void update(double time, double x){
        double newX = x%800;
        double camX = 800-newX;
        rightB.setX(camX);
        leftB.setCamX(newX);
    }
}
