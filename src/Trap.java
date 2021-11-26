public class Trap extends AnimatedThing{

    public Trap(String nameFile, double x, double y, double length, double hauteur) {
        super(nameFile, x, y, length, hauteur,0,0);
    }

    public void update(){
        setXX(getX()-3);
        getAnimatedView().setX(x);
    }
}
