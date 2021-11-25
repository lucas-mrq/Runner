import javafx.geometry.Rectangle2D;

public class Eagle extends AnimatedThing{
    private double indexMax = 2;
    private double indexEagle = 0;

    public Eagle(String nameFile, double x, double y, double largeur, double hauteur) {
        super(nameFile, x, y, largeur, hauteur,0,0);
    }

    public void update(){
        setXX(getX()-3);
        indexEagle+=1;
        if(indexEagle==indexMax*5){
            indexEagle=0;
        }
        if (getAnimatedView().getY()<150){
            getAnimatedView().setY(getAnimatedView().getY()-3);
        }
        getAnimatedView().setX(x);
        setHitBox(new Rectangle2D(48*((int) indexEagle/5),0,getLargeur(),getHauteur()));
        getAnimatedView().setViewport(getHitBox());
    }
}
