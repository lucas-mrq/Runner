import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class staticThing {
    private double camX;
    private double coX;
    private double coY;
    final ImageView imgV;
    final String fileName;
    final Image image;

    public staticThing(String fileName, double coX, double coY, double camX, double camY) {
        this.fileName = fileName;
        image = new Image(fileName);
        this.imgV = new ImageView(image);
        imgV.setX(coX);
        imgV.setY(coY);
        imgV.setViewport(new Rectangle2D(camX,camY,800,400));
    }

    public ImageView getImageView() {
        return imgV;
    }

    public void setX(double x){
        this.imgV.setX(x);
        this.coX = x;
    }

    public void setY(double y){
        this.imgV.setY(y);
        this.coY = y;
    }

    public void setCamX(double x){
        this.imgV.setViewport(new Rectangle2D(x,0,800-x,400));
        this.camX=x;
        }

}
