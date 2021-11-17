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
    private static double index = 0;

    public Hero(double x, double y, double attitude, double index, double indexMax) {
        super("heros.png", x, y, attitude, index, indexMax,vX);
    }

    public void update(double time, double xC){
        if(time>1){time=0;}
        vX += aX * time;
        x += vX * time;
        if(attitude==0){
            index+=1;
            if(index==5*5){
                index=0;
            }
            Hero.moove(index, (int) index/5,(xC-x)%800);
        }
    }
}
