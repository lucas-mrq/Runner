import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedThing {

    private Rectangle2D hitBox;

    public double vX ;
    public double aX = 0;
    public double vY = 0;
    public double aY = 0;

    final double hauteur;
    final double length;

    private double attitude;

    final ImageView imgV;

    public double x;
    public double y;

    public AnimatedThing(String fileName, double x, double y, double hauteur, double length, double attitude, double vX) {
        this.attitude = attitude;
        this.x = x;
        this.vX=vX;
        this.y = y;
        this.length = length;
        this.hauteur = hauteur;
        this.hitBox = new Rectangle2D(0,0,length,hauteur);
        this.imgV = new ImageView(new Image(fileName));
        imgV.setX(x);
        imgV.setY(y);

        imgV.setViewport(hitBox);
    }

    public ImageView getAnimatedView() {
        return imgV;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLength() {
        return length;
    }


    public void setY(double y) {
        imgV.setY(y);
    }

    public void setX(double x) {
        imgV.setX(x);
    }

    public void setXX(double a) {
        x = a;
    }

    public double getAttitude() {
        return attitude;
    }

    public void setAttitude(double att) {
        attitude = att;
    }

    public Rectangle2D getHitBox(){
        return hitBox;
    }

    public void setHitBox(Rectangle2D a){
        hitBox = a;
    }
}
