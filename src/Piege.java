import javafx.geometry.Rectangle2D;

public class Piege extends AnimatedThing{
    private double indexMax = 1;
    private double indexPiege = 0;

    public Piege(String nameFile, double x, double y, double largeur, double hauteur) {
        super(nameFile, x, y, largeur, hauteur,0,0);
    }

    public void update(){
        setXX(getX()-3);
        getAnimatedView().setX(x);
    }
}
