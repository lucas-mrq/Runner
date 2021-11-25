import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class AnimatedThing {

    private String fileName;
    private Image image;
    private Rectangle2D hitBox;

    public double vX = 100;
    public double aX = 0;
    public double vY = 0;
    public double aY = 0;

    private double attitude;
    private double hauteur;
    private double largeur;

    private ImageView imgV;

    public double x;
    public double y;

    public AnimatedThing(String fileName, double x, double y, double hauteur, double largeur, double attitude, double vX) {
        this.fileName = fileName;
        this.attitude = attitude;
        this.x = x;
        this.vX=vX;
        this.y = y;
        this.vY = vY;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.hitBox = new Rectangle2D(0,0,largeur,hauteur);
        image = new Image(fileName);
        this.imgV = new ImageView(image);
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

    public double getLargeur() {
        return largeur;
    }


    public void setY(double y) {
        imgV.setY(y);;
    }

    public void setX(double x) {
        imgV.setX(x);;
    }

    public void setXX(double a) {
        x = a;
    }

    /*public ArrayList getHitBox(Rectangle2D image){
        ArrayList liste = new ArrayList<Double>();
        liste.add(image.getWidth());
        liste.add(image.getHeight());
        return liste;
    }*/

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
