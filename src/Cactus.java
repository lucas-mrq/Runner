import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;
import java.util.ArrayList;

public class Cactus{
    private static double attitude = 0;
    private static Boolean accelerate = false;
    private double indexMax;
    private Image image;
    private static ImageView imgV;
    private static double x = 0;
    public static double vX = 100;
    public static double aX = 0;

    public Cactus(double x, double y) {
        image = new Image("cactus.png");
        this.imgV = new ImageView(image);
        imgV.setX(x);
        imgV.setY(y);
        imgV.setViewport(new Rectangle2D(0,0,50,50));
    }

    public void update(double time, double xC){
        x -= 2;
        imgV.setX(x);
    }

    public static ImageView getAnimatedView() {
        return imgV;
    }

    public void setX(double a){
        x = a;
    }

    public double getX(){
        return x;
    }

}
