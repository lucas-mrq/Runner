public class Camera {
    private static double x;
    private double y;
    private double vX=50;
    private double aX;

    private double f = 20;
    private double k = 10;

    public Camera(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVX() {
        return vX;
    }

    public void update(double time, double xH){
        if(time>1){time=0;}
        aX = k * (xH-x) - f * vX;
        vX += aX * time;
        x += vX * time;


    }

    @Override
    public String toString(){
        return (x + "," + y);
    }
}
