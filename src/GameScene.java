import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class GameScene extends Scene{

    private Integer out = 1000;
    private staticThing leftB;
    private staticThing rightB;
    private staticThing game;
    private AnimatedThing laser;
    private AnimatedThing sKeyPicture;
    private AnimatedThing zKeyPicture;
    private AnimatedThing spaceKeyPicture;
    private AnimatedThing pKeyPicture;
    private AnimatedThing laserMenue;
    private AnimatedThing levelUp;
    private Hero player;
    private Group root;
    private Camera cam;
    private Integer compteurLevelUp = 0;
    private double gsX;
    private double invincibility = 0;
    private boolean pause = false;
    private boolean endMenue = false;
    private boolean startMenue = true;
    private Integer collision = 0;
    private Integer lives = 3;
    private Integer compteur = 0;

    private Random random = new Random();
    private ArrayList<Eagle> eagle = new ArrayList<Eagle>();
    private ArrayList<Piege> piege = new ArrayList<Piege>();
    private ArrayList<staticThing> heart = new ArrayList<staticThing>();
    private ArrayList<String> listeName = new ArrayList<String>();
    private ArrayList<Integer> listeScore = new ArrayList<Integer>();
    private double indexScore = 0;

    private Integer sizeEagle = 0;
    private Integer sizePiege = 0;
    private Integer indexRandom = 10;
    private int generationRandom = 100;
    private Integer indexLaser = 6;
    private Text textScore;
    private Text textLastScore;
    private Text textScoreValue;
    private Text zKeyText;
    private Text pKeyText;
    private Text sKeyText;
    private Text spaceKeyText;
    private Integer scoreValue = 0;


    private double x;

    private double past;

    public GameScene(double gsX, double gsY, Group root, double length, double height, double vitesse) {
        super(root, height, length);

        this.cam = new Camera(gsX, gsY);
        this.root = root;
        this.gsX=gsX;
        this.leftB = new staticThing("desert.png",0,0, 800-gsX,0);
        this.rightB = new staticThing("desert.png",gsX,0, 0,0);


        root.getChildren().add(leftB.getImageView());
        root.getChildren().add(rightB.getImageView());

        this.textLastScore = new Text(out, 50, "Last");
        txt(textLastScore);

        this.textScore = new Text(600, out, "Score: ");
        txt(textScore);

        this.textScoreValue = new Text(700, 50, "");
        txt(textScoreValue);

        this.sKeyPicture = new AnimatedThing("sKey.png",50,28, 0,0,0,0);
        this.sKeyText = new Text(105, 50, "Start");
        txt(sKeyText);
        System.out.println(sKeyText.getWrappingWidth());


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

        this.player = new Hero("heros.png",800,gsY-40,85,100);
        player.getAnimatedView().setPreserveRatio(true);
        player.getAnimatedView().setFitHeight(50);
        root.getChildren().add(player.getAnimatedView());

        this.levelUp = new AnimatedThing("levelUp.png",out,0,85,85,0,0);
        root.getChildren().add(levelUp.getAnimatedView());

        this.laser = new AnimatedThing("laser.png",out,0, 0,0,0,0);
        root.getChildren().add(laser.getAnimatedView());

        this.laserMenue = new AnimatedThing("laser.png",out,0, 0,0,0,0);
        root.getChildren().add(laserMenue.getAnimatedView());
        laserMenue.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser,15 ));
        laserMenue.getAnimatedView().setX(out);
        laserMenue.getAnimatedView().setY(75);

        this.setOnKeyPressed( (event)->{
            switch(event.getText()){
                case " " ->{
                    if(player.getAttitude()==0 & !startMenue & !endMenue) spaceKey();}
                case "p" -> {
                    if(!startMenue & !endMenue){pKey();}}
                case "e" -> {
                    if(!startMenue & !endMenue){eKey();}}
                case "c" -> {
                    if(!startMenue & !endMenue){cKey();}}
                case "r" -> {
                    if(endMenue){rKey();}}
                case "x"->{
                    if(endMenue){xKey();}}
                case "h"->{
                    if(endMenue){hKey();}}
                case "s" ->{
                    if(startMenue){sKey();}}
                case "z" ->{
                    if(!startMenue & !endMenue & indexLaser!=0){zKey();}}
            }
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(!pause & !endMenue & !startMenue) {

                    double timeInt = now - past;
                    double time = (now - past) * Math.pow(10, -9);
                    if (timeInt > 5000000) {

                        compteur+=1;
                        scoreValue = compteur/100;

                        textScoreValue.setText(scoreValue.toString());
                        cam.update(time, player.getX());

                        if(tabCollision()){
                            invincibility = 100;
                            lives-=1;
                            if(lives ==0){
                                endMenue = true;
                                game = new staticThing("gameOver.png",318,66,0,0 );
                                root.getChildren().add(game.getImageView());
                            }
                        }

                        invincibility-=1;
                        player.update(time, cam.getX(), invincibility,laser);

                        if (compteur == generationRandom){
                            newFoe(random.nextInt(indexRandom));
                            generationRandom = compteur + 40 + random.nextInt(50);
                        }

                        if (compteur%250==0 & indexRandom>2){
                            indexRandom-=1;
                            compteurLevelUp = 50;
                            if(compteur%750==0) {
                                lives += 1;
                            }
                        }

                        update(cam.getX());
                        past = now;
                    }
                }
                else{
                    past = now;
                    if(startMenue){
                        leftB.getImageView().setX(0);
                        player.updateMenue();
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

        if (compteurLevelUp>0){
            levelUp.getAnimatedView().setX(player.getAffx()+15);
            levelUp.getAnimatedView().setY(player.getY()-20);
            compteurLevelUp-=1;
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
                    setFoeOut(1);
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
            Piege newPiege = new Piege("piege.png",800,280,23,34);
            piege.add(newPiege);
            sizePiege+=1;
            root.getChildren().add(piege.get(sizePiege-1).getAnimatedView());
        }
    }
    public boolean collision(Rectangle2D pHitBox, double pX, double pY, Rectangle2D fHitBox, double fX, double fY, String a){
        if(a=="eagle"){
            if((pX+20<fX & pX+pHitBox.getHeight()-20>fX & pY+15<fY+15 & pY+pHitBox.getWidth()>fY+15)
                    |(pX+20<fX+fHitBox.getHeight() & pX+pHitBox.getHeight()-20>fX+fHitBox.getHeight() & pY+15<fY+15 & pY+pHitBox.getWidth()>fY+15)
                    |(pX+20<fX & pX+pHitBox.getHeight()-20>fX & pY+15<fY+fHitBox.getHeight() & pY+pHitBox.getWidth()>fY+fHitBox.getWidth())
                    | (pX+20<fX+fHitBox.getHeight() & pX+pHitBox.getHeight()-20>fX+fHitBox.getHeight() & pY+15<fY+fHitBox.getWidth() & pY+pHitBox.getWidth()>fY+fHitBox.getWidth())) {
                return true;
            }
        }
        else{
            if(a=="piege"){
                if((pX+20<fX & pX+pHitBox.getHeight()-20>fX & pY+15<fY & pY+pHitBox.getWidth()>fY)
                        |(pX+20<fX+fHitBox.getHeight() & pX+pHitBox.getHeight()-20>fX+fHitBox.getHeight() & pY+15<fY & pY+pHitBox.getWidth()>fY)
                        |(pX+20<fX & pX+pHitBox.getHeight()-20>fX & pY+15<fY+fHitBox.getHeight() & pY+pHitBox.getWidth()>fY+fHitBox.getWidth())
                        | (pX+20<fX+fHitBox.getHeight() & pX+pHitBox.getHeight()-20>fX+fHitBox.getHeight() & pY+15<fY+fHitBox.getWidth() & pY+pHitBox.getWidth()>fY+fHitBox.getWidth())) {
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public void setFoeOut(double a){
        if(a!=1) {
            for (int i = 0; i < sizePiege; i++) {
                piege.get(i).setXX(-out);
            }
        }
        for(int i = 0; i<sizeEagle; i++){
            eagle.get(i).setXX(-out);
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
        Piege newPiege = new Piege("piege.png", 800, 280, 23, 34);
        piege.add(newPiege);
        sizePiege += 1;
        root.getChildren().add(piege.get(sizePiege - 1).getAnimatedView());
    }

    public void rKey(){
        newStart();
        laserMenue.getAnimatedView().setX(20);
        laserMenue.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        endMenue = false;
        game.setX(out);
    }

    public void xKey(){
        newStart();
        player.getAnimatedView().setFitHeight(50);
        startMenue=true;
        textLastScore.setX(530);
        endMenue = false;
        afficheMenue();
    }
    public void sKey(){
        startMenue=false;
        textScore.setY(50);
        textLastScore.setX(out);
        for (int i=0; i<6;i++) {
            heart.get(i).getImageView().setY(20);
        }
        player.getAnimatedView().setFitHeight(100);
        laserMenue.getAnimatedView().setX(20);
        laserMenue.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        supprimeMenue();
    }

    public void zKey(){
        player.setTir(50);
        indexLaser -= 1;
        System.out.println(20 * indexLaser);
        laserMenue.getAnimatedView().setViewport(new Rectangle2D(20, 0, 20 * indexLaser, 15));
        if(indexLaser==0){
            laserMenue.getAnimatedView().setX(out);
        }
    }

    public void hKey(){
        fillScore("highScore.csv");
        System.out.println(listeScore);
        System.out.println(listeName);
    }

    public void fillScore(String fileName){
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String li = br.readLine();
            while(li!=null){
                Integer firstComma = li.indexOf(',');
                String Name = li.substring(0,firstComma);
                String ScoreString = li.substring(firstComma+1, li.length());
                Integer Score = Integer.valueOf(ScoreString);
                li = br.readLine();
                if (indexScore==0){
                    listeName.add(Name);
                    listeScore.add(Score);
                }
                else {
                    Integer saveScore;
                    String saveName;
                    for (int i = 0; i < indexScore; i++) {
                        if (listeScore.get(i) <= Score) {
                            saveScore = listeScore.get(i);
                            saveName = listeName.get(i);
                            listeScore.set(i, Score);
                            listeName.set(i,Name);
                            Score = saveScore;
                            Name=saveName;
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Problème de FileReader");
        }
    }

    public void afficheMenue(){
        zKeyPicture.getAnimatedView().setY(28);
        zKeyText.setY(50);
        sKeyPicture.getAnimatedView().setY(78);
        sKeyText.setY(100);
        pKeyPicture.getAnimatedView().setY(128);
        pKeyText.setY(150);
        spaceKeyPicture.getAnimatedView().setY(178);
        spaceKeyText.setY(235);
    }

    public void supprimeMenue(){
        zKeyPicture.getAnimatedView().setY(out);
        zKeyText.setY(out);
        sKeyPicture.getAnimatedView().setY(out);
        sKeyText.setY(out);
        pKeyPicture.getAnimatedView().setY(out);
        pKeyText.setY(out);
        spaceKeyPicture.getAnimatedView().setY(out);
        spaceKeyText.setY(out);
    }

    public void newStart(){
        indexLaser=6;
        setFoeOut(0);
        scoreValue = 0;
        invincibility = 0;
        game.setX(out);
        lives = 3;
        indexRandom =10;
        compteur=0;
        Integer index = 0;
        while (index < sizeEagle) {
            eagle.get(index).update();
            index += 1;
        }
        index = 0;
        while (index < sizePiege) {
            piege.get(index).update();
            index += 1;
        }
        laserMenue.getAnimatedView().setX(out);
        laser.getAnimatedView().setX(out);
        levelUp.getAnimatedView().setX(out);
    }

    public void txt(Text texte){
        texte.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        texte.setWrappingWidth(200);
        texte.setStrokeWidth(1);
        texte.setStroke(Color.WHITE);
        root.getChildren().add(texte);
    }

    public boolean tabCollision(){
        Integer detectCollision = 0;
        Integer index = 0;
        while (index < sizeEagle) {
            eagle.get(index).update();
            if (invincibility<1 & collision(player.getHitBox(), player.getAffx(), player.getY(), eagle.get(index).getHitBox(), eagle.get(index).getX(), eagle.get(index).getY(), "eagle")) {
                detectCollision += 1;
                eagle.get(index).getAnimatedView().setY(149);
            }
            index += 1;
        }
        index = 0;
        while (index < sizePiege) {
            piege.get(index).update();
            if (invincibility<1 & collision(player.getHitBox(), player.getAffx(), player.getY(), piege.get(index).getHitBox(), piege.get(index).getX(), piege.get(index).getY(), "piege")) {
                detectCollision += 1;
                piege.get(index).getAnimatedView().setViewport(new Rectangle2D(34,0,17,29));
            }
            index += 1;
        }
        if (detectCollision>0) {
            return true;
        }
        else{
            return false;
        }
    }

}