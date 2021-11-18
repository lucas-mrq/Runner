import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;

public class Eagle{
    private static double attitude = 0;
    private static Boolean accelerate = false;
    private double indexMax;
    private Image image;
    private static ImageView imgV;
    private static double indexEagle = 0;
    private static double x = 0;
    public static double vX = 100;
    public static double aX = 0;

    public Eagle(double x, double y, double indexEagle) {
        this.indexEagle = indexEagle;
        image = new Image("eagle.png");
        this.imgV = new ImageView(image);
        imgV.setX(x);
        imgV.setY(y);
        imgV.setViewport(new Rectangle2D(47*indexEagle,0,47,47));
    }

    public void update(double time, double xC){
        x -= 3;
        indexEagle+=1;
        if(indexEagle==2*5){
            indexEagle=0;
        }
        imgV.setX(x);
        imgV.setViewport(new Rectangle2D(48*((int) indexEagle/5),0,47,47));
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
