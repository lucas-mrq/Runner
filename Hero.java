import javafx.geometry.Rectangle2D;

public class Hero extends AnimatedThing {
    final double indexMax = 6;
    private static double indexPlayer = 0;
    private double affX;
    private double xMenu =0;
    private double tir=0;

    public Hero(String nameFile, double x, double y, double length, double hauteur) {
        super(nameFile, x, y, length, hauteur, 0,100);
    }

    public void update(double time, double xC, double invincibility, AnimatedThing laser){
        if(time>1){time=0;}
        vX += aX * time;
        x += vX * time;

        if(getAttitude()==0 & invincibility<1){
            indexPlayer+=1;
            if(indexPlayer==indexMax*5){
                indexPlayer=0;
            }
            if (tir==0) {
                setHitBox(new Rectangle2D(85 * ((int) indexPlayer / 5), 0, 85, 100));
                laser.getAnimatedView().setX(1000);
            }
            else{
                setHitBox(new Rectangle2D(84*((int) indexPlayer/5),325,85,100));
                tir-=1;
                laser.setX(affX);
                laser.setY(y-50);
            }
            getAnimatedView().setViewport(getHitBox());
        }

        if(getAttitude()==0 & invincibility>0){
            indexPlayer+=1;
            if(indexPlayer==indexMax*5){
                indexPlayer=0;
            }
            setHitBox(new Rectangle2D(85*((int) indexPlayer/5),640,85,100));
            getAnimatedView().setViewport(getHitBox());
            laser.getAnimatedView().setX(1000);
        }

        if(getAttitude()==1 & invincibility<1) {
            aY += -9.81 * 1000 * time;
            vY += aY * time;
            y -= vY * time;
            if (vY>0) {
                if(tir==0) {
                    setHitBox(new Rectangle2D(0, 160, 85, 100));
                }
                else{
                    setHitBox(new Rectangle2D(0, 485, 85, 100));
                }
                getAnimatedView().setViewport(getHitBox());
            }
            else {
                if(tir==0) {
                    setHitBox(new Rectangle2D(85, 160, 85, 100));
                }
                else{
                    setHitBox(new Rectangle2D(85, 485, 85, 100));
                }
                getAnimatedView().setViewport(getHitBox());
            }
        }
        if(getAttitude()==1 & invincibility>0) {
            aY += -9.81 * 1000 * time;
            vY += aY * time;
            y -= vY * time;
            if (vY>0) {
                setHitBox(new Rectangle2D(0, 800, 85, 100));
            }
            else {
                setHitBox(new Rectangle2D(85, 800, 85, 100));
            }
            getAnimatedView().setViewport(getHitBox());
        }
        if (y>200){
            setAttitude(0);
            y = 200;
            vY = 0;
            aY = 0;
        }
        getAnimatedView().setX(300+(xC-x)%800);
        setAffX(300+(xC-x)%800);
        setY(y);
    }

    public void updateMenu() {
        indexPlayer+=1;
        xMenu +=3;
        if(indexPlayer==indexMax*5){
            indexPlayer=0;
        }
        getAnimatedView().setX(xMenu %800);
        setAffX(x);
        setY(230);
        setHitBox(new Rectangle2D(85*((int) indexPlayer/5),0,85,100));
        getAnimatedView().setViewport(getHitBox());
    }

    public void jump(){
        setAttitude(1);
        aY=3000;
    }

    public void setAffX(double x){
        affX = x;
    }

    public double getAffx(){return affX;}

    public void setTir(double value){
        tir = value;
    }

    public double getTir(){
        return tir;
    }
}
