import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

public class GameScene extends Scene {

    final Group root;
    final Camera cam;

    final staticThing leftB;
    final staticThing rightB;
    private staticThing game;

    final AnimatedThing laser;
    final AnimatedThing sKeyPicture;
    final AnimatedThing zKeyPicture;
    final AnimatedThing spaceKeyPicture;
    final AnimatedThing pKeyPicture;
    final AnimatedThing laserMenu;
    final AnimatedThing levelUp;

    final Hero player;

    private Integer computerLevelUp = 0;
    final Integer out = 1000;
    private Integer lives = 3;
    private Integer computer = 0;
    private Integer scoreValue = 0;
    private Integer indexLaser = 6;
    private Integer sizeEagle = 0;
    private Integer sizeTrap = 0;
    private Integer generationRandom = 100;
    private Integer indexRandom = 5;
    private Integer indexFinalStage = 0;

    private double invincibility = 0;
    private double past;
    final double gsX;
    double indexScore = 0;

    private boolean pause = false;
    private boolean endMenu = false;
    private boolean startMenu = true;

    final Random random = new Random();

    final ArrayList<Eagle> eagle = new ArrayList<>();
    final ArrayList<Trap> trap = new ArrayList<>();

    final ArrayList<staticThing> heart = new ArrayList<>();

    final ArrayList<Integer> listScore = new ArrayList<>();
    final ArrayList<Text> scoreText = new ArrayList<>();

    final Text textScore;
    final Text textLastScore;
    final Text textMenu;
    final Text textScoreValue;
    final Text textFinalStage;
    final Text zKeyText;
    final Text pKeyText;
    final Text sKeyText;
    final Text spaceKeyText;

    public GameScene(double gsX, double gsY, Group root, double length, double height) {
        super(root, height, length);

        this.gsX=gsX;
        this.cam = new Camera(gsX, gsY);
        this.root = root;

        this.leftB = new staticThing("desert.png",0,0, 800-gsX,0);
        this.rightB = new staticThing("desert.png",gsX,0, 0,0);
        root.getChildren().add(leftB.getImageView());
        root.getChildren().add(rightB.getImageView());

        for(int i = 0; i<6; i++){
            scoreText.add(new Text(out, 100+30*i, "HighScore :"));
            txt(scoreText.get(i));
            scoreText.get(i).setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        }

        this.textLastScore = new Text(out, 50, "Last");
        txt(textLastScore);

        this.textScore = new Text(600, out, "Score: ");
        txt(textScore);

        this.textMenu = new Text(300, 50, "Menu");
        txt(textMenu);
        textMenu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

        this.textScoreValue = new Text(700, 50, "");
        txt(textScoreValue);

        this.textFinalStage = new Text(300, out, "Final Stage");
        txt(textFinalStage);

        this.sKeyPicture = new AnimatedThing("sKey.png",50,28, 0,0,0,0);
        this.sKeyText = new Text(105, 50, "Start");
        txt(sKeyText);

        this.zKeyPicture = new AnimatedThing("zKey.png",50,78, 0,0,0,0);
        this.zKeyText = new Text(100, 100, "Shoot");
        txt(zKeyText);

        this.pKeyPicture = new AnimatedThing("pKey.png",50,128, 0,0,0,0);
        this.pKeyText = new Text(100, 150, "Pause");
        txt(pKeyText);

        this.spaceKeyPicture = new AnimatedThing("spaceKey.png",50,178, 0,0,0,0);
        this.spaceKeyText = new Text(80, 235, "Jump");
        txt(spaceKeyText);

        root.getChildren().add(zKeyPicture.getAnimatedView());
        root.getChildren().add(pKeyPicture.getAnimatedView());
        root.getChildren().add(sKeyPicture.getAnimatedView());
        root.getChildren().add(spaceKeyPicture.getAnimatedView());

        for (int i=0; i<6;i++) {
            staticThing coeur = new staticThing("heart.png",20+30*i,out,0,0 );
            heart.add(coeur);
            root.getChildren().add(heart.get(i).getImageView());
        }

        this.player = new Hero("heroes.png",800,gsY-40,85,100);
        player.getAnimatedView().setPreserveRatio(true);
        player.getAnimatedView().setFitHeight(50);
        root.getChildren().add(player.getAnimatedView());

        this.levelUp = new AnimatedThing("levelUp.png",out,0,85,85,0,0);
        root.getChildren().add(levelUp.getAnimatedView());

        this.laser = new AnimatedThing("laser.png",out,0, 0,0,0,0);
        root.getChildren().add(laser.getAnimatedView());

        this.laserMenu = new AnimatedThing("laser.png",out,0, 0,0,0,0);
        root.getChildren().add(laserMenu.getAnimatedView());
        laserMenu.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser,15 ));
        laserMenu.getAnimatedView().setX(out);
        laserMenu.getAnimatedView().setY(75);

        this.setOnKeyPressed( (event)->{
            switch(event.getText()){
                case " " ->{
                    if(player.getAttitude()==0 & !startMenu & !endMenu) spaceKey();}
                case "p" -> {
                    if(!startMenu & !endMenu){pKey();}}
                case "e" -> {
                    if(!startMenu & !endMenu){eKey();}}
                case "c" -> {
                    if(!startMenu & !endMenu){cKey();}}
                case "r" -> {
                    if(endMenu){rKey();}}
                case "x"->{
                    if(endMenu){xKey();}}
                case "s" ->{
                    if(startMenu){sKey();}}
                case "z" ->{
                    if(!startMenu & !endMenu & indexLaser!=0){zKey();}}
            }
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(!pause & !endMenu & !startMenu) {

                    double timeInt = now - past;
                    double time = (now - past) * Math.pow(10, -9);
                    if (timeInt > 5000000) {

                        computer +=1;
                        scoreValue = computer /100;

                        indexFinalStage-=1;
                        if (indexFinalStage==0){
                            textFinalStage.setY(out);
                        }

                        textScoreValue.setText(scoreValue.toString());
                        cam.update(time, player.getX());

                        if(tabCollision()){
                            invincibility = 100;
                            lives-=1;
                            if(lives ==0){
                                endMenu = true;
                                listScore.add(scoreValue);
                                indexScore+=1;
                                saveScore();
                                game = new staticThing("gameOver.png",318,50,0,0 );
                                root.getChildren().add(game.getImageView());
                            }
                        }

                        invincibility-=1;
                        player.update(time, cam.getX(), invincibility,laser);

                        if (Objects.equals(computer, generationRandom)){
                            newFoe(random.nextInt(indexRandom+1));
                            generationRandom = computer + 40 + random.nextInt(50);
                        }

                        if (computer %250==0 & indexRandom>1) {
                            indexRandom -= 1;
                            computerLevelUp = 50;
                            if (indexRandom == 1) {
                                textFinalStage.setY(50);
                                indexFinalStage = 50;
                            }
                        }
                        if(computer % 2000==0) {
                            lives += 1;
                        }

                        update(cam.getX());
                        past = now;
                    }
                }
                else{
                    past = now;
                    if(startMenu){
                        leftB.getImageView().setX(0);
                        player.updateMenu();
                    }
                }
            }
        };

        timer.start();
    }

    public void update(double x){
        double newX = x%800;
        double camX = 800-newX;
        rightB.setX(camX);
        leftB.setCamX(newX);

        if (computerLevelUp >0){
            levelUp.getAnimatedView().setX(player.getAffx()+15);
            levelUp.getAnimatedView().setY(player.getY()-20);
            computerLevelUp -=1;
        }
        else{
            levelUp.getAnimatedView().setX(out);
        }

        if(player.getTir()>0 & (player.getTir()>40 | player.getTir()<10)){
            player.setTir(player.getTir()-1);
            laser.getAnimatedView().setViewport(new Rectangle2D(0, 0, 12, 15));
            laser.getAnimatedView().setX(player.getAffx()+72);
            laser.getAnimatedView().setY(player.getY()+40);
        }
        else{
            if (player.getTir()>0){
                player.setTir(player.getTir()-1);
                laser.getAnimatedView().setViewport(new Rectangle2D(0, 0, 0, 0));
                laser.getAnimatedView().setX(player.getAffx()+72);
                laser.getAnimatedView().setY(player.getY()+40);
                if (laser.getAnimatedView().getY()>130 & laser.getAnimatedView().getY()<170){
                    setFoeOut(1,player.getAffx()+80);
                }

            }
            else{
                laser.getAnimatedView().setX(out);
            }
        }

        for(int i = lives; i<6; i+=1){
            heart.get(i).setX(out);
        }
        for(int i = 0; i<lives; i+=1){
            heart.get(i).setX(20+30*i);
        }
    }

    public void newFoe(int value){
        if(value==0){
            sizeEagle+=1;
            Eagle newEagle = new Eagle("eagle.png",800,150,47,47);
            eagle.add(newEagle);
            root.getChildren().add(eagle.get(sizeEagle-1).getAnimatedView());
        }
        if(value==1){
            Trap newTrap = new Trap("trap.png",800,280,23,34);
            trap.add(newTrap);
            sizeTrap+=1;
            root.getChildren().add(trap.get(sizeTrap-1).getAnimatedView());
        }
    }
    public boolean collision(Rectangle2D pHitBox, double pX, double pY, Rectangle2D fHitBox, double fX, double fY, String a){
        if(Objects.equals(a, "eagle")){
            return (pX + 20 < fX & pX + pHitBox.getHeight() - 20 > fX & pY + 15 < fY + 15 & pY + pHitBox.getWidth() > fY + 15)
                    | (pX + 20 < fX + fHitBox.getHeight() & pX + pHitBox.getHeight() - 20 > fX + fHitBox.getHeight() & pY + 15 < fY + 15 & pY + pHitBox.getWidth() > fY + 15)
                    | (pX + 20 < fX & pX + pHitBox.getHeight() - 20 > fX & pY + 15 < fY + fHitBox.getHeight() & pY + pHitBox.getWidth() > fY + fHitBox.getWidth())
                    | (pX + 20 < fX + fHitBox.getHeight() & pX + pHitBox.getHeight() - 20 > fX + fHitBox.getHeight() & pY + 15 < fY + fHitBox.getWidth() & pY + pHitBox.getWidth() > fY + fHitBox.getWidth());
        }
        else{
            if(Objects.equals(a, "trap")){
                return (pX + 20 < fX & pX + pHitBox.getHeight() - 20 > fX & pY + 15 < fY & pY + pHitBox.getWidth() > fY)
                        | (pX + 20 < fX + fHitBox.getHeight() & pX + pHitBox.getHeight() - 20 > fX + fHitBox.getHeight() & pY + 15 < fY & pY + pHitBox.getWidth() > fY)
                        | (pX + 20 < fX & pX + pHitBox.getHeight() - 20 > fX & pY + 15 < fY + fHitBox.getHeight() & pY + pHitBox.getWidth() > fY + fHitBox.getWidth())
                        | (pX + 20 < fX + fHitBox.getHeight() & pX + pHitBox.getHeight() - 20 > fX + fHitBox.getHeight() & pY + 15 < fY + fHitBox.getWidth() & pY + pHitBox.getWidth() > fY + fHitBox.getWidth());
            }
        }
        return false;
    }

    public void setFoeOut(double a, double b){
        if(a!=1) {
            for (int i = 0; i < sizeTrap; i++) {
                trap.get(i).setXX(-out);
            }
        }
        for(int i = 0; i<sizeEagle; i++){
            if(eagle.get(i).getX()>b) {
                eagle.get(i).setXX(-out);
            }
        }
    }


    public void spaceKey(){
        if (player.getAttitude()==0){
            player.jump();
        }
    }
    public void pKey(){
        pause = !pause;
    }

    public void eKey(){
        sizeEagle+=1;
        Eagle newEagle = new Eagle("eagle.png",800,150,47,47);
        eagle.add(newEagle);
        root.getChildren().add(eagle.get(sizeEagle-1).getAnimatedView());
    }

    public void cKey(){
        Trap newTrap = new Trap("trap.png", 800, 280, 23, 34);
        trap.add(newTrap);
        sizeTrap += 1;
        root.getChildren().add(trap.get(sizeTrap - 1).getAnimatedView());
    }

    public void rKey(){
        cam.setX(gsX);
        cam.setVX(50);
        player.setX(800);
        player.setXX(800);
        leftB.setCamX(800-gsX);
        rightB.setX(gsX);
        newStart();
        laserMenu.getAnimatedView().setX(20);
        laserMenu.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        endMenu = false;
        game.setX(out);
    }

    public void xKey(){
        newStart();
        player.getAnimatedView().setFitHeight(50);
        startMenu =true;
        textLastScore.setX(530);
        endMenu = false;
        displayMenu();
    }
    public void sKey(){
        cam.setX(gsX);
        cam.setVX(50);
        player.setX(800);
        player.setXX(800);
        leftB.setCamX(800-gsX);
        rightB.setX(gsX);
        startMenu =false;
        textScore.setY(50);
        textLastScore.setX(out);
        for (int i=0; i<6;i++) {
            heart.get(i).getImageView().setY(20);
        }
        player.getAnimatedView().setFitHeight(100);
        laserMenu.getAnimatedView().setX(20);
        laserMenu.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        deleteMenu();
    }

    public void zKey(){
        player.setTir(50);
        indexLaser -= 1;
        laserMenu.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        if(indexLaser==0){
            laserMenu.getAnimatedView().setX(out);
        }
    }

    public void saveScore(){
        Collections.sort(listScore);
        Collections.reverse(listScore);
        try {
            FileWriter myWriter = new FileWriter("highScore.txt");
            StringBuilder txt= new StringBuilder();
            txt.append("HighScore:\n");
            for(int i = 1; i<indexScore+1;i++){
                txt.append(i).append("e score: ").append(listScore.get(i - 1).toString()).append("\n");
            }
            myWriter.write(txt.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayScore(){
        scoreText.get(0).setX(570);
        for(int i = 1;i<1+indexScore;i++){
            if(i<7){
                scoreText.get(i).setText(i + "e score: " + listScore.get(i-1).toString() );
                scoreText.get(i).setX(570);
            }
        }
    }

    public void displayMenu(){
        displayScore();
        textMenu.setY(50);
        zKeyPicture.getAnimatedView().setY(28);
        zKeyText.setY(50);
        sKeyPicture.getAnimatedView().setY(78);
        sKeyText.setY(100);
        pKeyPicture.getAnimatedView().setY(128);
        pKeyText.setY(150);
        spaceKeyPicture.getAnimatedView().setY(178);
        spaceKeyText.setY(235);
    }

    public void deleteMenu(){
        textMenu.setY(out);
        zKeyPicture.getAnimatedView().setY(out);
        zKeyText.setY(out);
        sKeyPicture.getAnimatedView().setY(out);
        sKeyText.setY(out);
        pKeyPicture.getAnimatedView().setY(out);
        pKeyText.setY(out);
        spaceKeyPicture.getAnimatedView().setY(out);
        spaceKeyText.setY(out);
        for(int i = 0;i<1+indexScore;i++){
            scoreText.get(i).setX(out);
        }
    }

    public void newStart(){
        indexLaser=6;
        setFoeOut(0, 0);
        scoreValue = 0;
        invincibility = 0;
        game.setX(out);
        lives = 3;
        indexRandom =10;
        computer =0;
        int index = 0;
        while (index < sizeEagle) {
            eagle.get(index).update();
            index += 1;
        }
        index = 0;
        while (index < sizeTrap) {
            trap.get(index).update();
            index += 1;
        }
        laserMenu.getAnimatedView().setX(out);
        laser.getAnimatedView().setX(out);
        levelUp.getAnimatedView().setX(out);
    }

    public void txt(Text texts){
        texts.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        texts.setWrappingWidth(400);
        texts.setStrokeWidth(1);
        texts.setStroke(Color.WHITE);
        root.getChildren().add(texts);
    }

    public boolean tabCollision(){
        int detectCollision = 0;
        int index = 0;
        while (index < sizeEagle) {
            eagle.get(index).update();
            if (invincibility<1 & collision(player.getHitBox(), player.getAffx(), player.getY(), eagle.get(index).getHitBox(), eagle.get(index).getX(), eagle.get(index).getY(), "eagle")) {
                detectCollision += 1;
                eagle.get(index).getAnimatedView().setY(149);
            }
            index += 1;
        }
        index = 0;
        while (index < sizeTrap) {
            trap.get(index).update();
            if (invincibility<1 & collision(player.getHitBox(), player.getAffx(), player.getY(), trap.get(index).getHitBox(), trap.get(index).getX(), trap.get(index).getY(), "trap")) {
                detectCollision += 1;
                trap.get(index).getAnimatedView().setViewport(new Rectangle2D(34,0,17,29));
            }
            index += 1;
        }
        return detectCollision > 0;
    }

}