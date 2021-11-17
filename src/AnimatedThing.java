import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedThing {
    private String fileName;
    private Image image;

    public static double vX = 100;
    public static double aX = 0;
    private double y;
    private double vY = 0;
    private double aY = 0;

    private double indexMax;

    private static double index = 0;
    private static double attitude =0;
    private static ImageView imgV;
    public static double x;

    private double duration = 100;
    private double coX = 800;
    private double coY = 400;
    private double spaceImg = 80;

    public AnimatedThing(String fileName, double x, double y, double attitude, double index, double indexMax, double vX) {
        this.fileName = fileName;
        this.attitude = attitude;
        this.index = index;
        this.x = x;
        this.vX=vX;
        this.indexMax = indexMax;
        image = new Image(fileName);
        this.imgV = new ImageView(image);
        imgV.setX(x);
        imgV.setY(y);
        imgV.setViewport(new Rectangle2D(85*2,0,85,100));
    }

    public static ImageView getAnimatedView() {
        return imgV;
    }

    public static double getIndex() {
        return index;
    }

    public static double getX() {
        return x;
    }

    public static double getAttitude() {
        return attitude;
    }

    public static void moove(double compt, int i, double newX) {
        imgV.setX(300+newX);
        imgV.setViewport(new Rectangle2D(85*i,0,85,100));
        index=compt;
    }
}
