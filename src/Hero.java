import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;

public class Hero extends AnimatedThing {
    private static double attitude = 0;
    private static Boolean accelerate = false;
    private double indexMax;
    private Image image;
    private static ImageView imgV;
    private static double indexPlayer = 0;

    public Hero(double x, double y, double attitude, double indexPlayer, double indexMax) {
        super("heros.png", x, y, attitude, indexPlayer, indexMax,vX);
    }

    public void update(double time, double xC){
        if(time>1){time=0;}
        vX += aX * time;
        x += vX * time;
        if(attitude==0){
            indexPlayer+=1;
            if(indexPlayer==6*5){
                indexPlayer=0;
            }
            //System.out.println((int) indexPlayer/5);
            Hero.getAnimatedView().setViewport(new Rectangle2D(85*((int) indexPlayer/5),0,85,100));
        }
        if(attitude==1) {
            aY += -9.81 * 1200 * time;
            vY += aY * time;
            y -= vY * time;
            if (vY>0) {
                Hero.getAnimatedView().setViewport(new Rectangle2D(0, 160, 85, 100));
            }
            else {
                Hero.getAnimatedView().setViewport(new Rectangle2D(85, 160, 85, 100));
            }
        }
        if (y>250){
            attitude = 0;
            y = 250;
            vY = 0;
            aY = 0;
        }

        Hero.getAnimatedView().setX(300+(xC-x)%800);
        Hero.setY(y);
    }

    public void jump(){
        attitude = 1;
        aY=3000;
    }

    public static boolean checkObstacle(double xE, double xC){
        if((attitude==1 & xE>Hero.getX()-xC-100) & xE<Hero.getX()-xC-30){
            return true;
        }
        else{
            return false;
        }
    }

    public static double getAttitude() {
        return attitude;
    }

    public static double getVX(){
        return vX;
    }

    public static void setVX(double vitesse){
        vX = vitesse;
    }
}
