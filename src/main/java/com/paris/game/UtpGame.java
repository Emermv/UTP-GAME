package com.paris.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;


public class UtpGame extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 1920/2;
    int boardHeight = 1080/2;
    int level = 1;
    int forLevel = 500;//score by level
    int maxLevel = 10;
    boolean youWin = false;

    //images

    HashMap<String,Image> imagesHashMap = new HashMap<>();


    //girl
    int girlWidth = 88;
    int girlHeight = 94;
    int girlX = 50;
    int girlY = boardHeight-100 - girlHeight;

    Block girl;


    int obstacleWidth = 69;

    int obstacleHeight = 70;
    int cactusX = boardWidth-obstacleWidth;
    int cactusY = (boardHeight-100) - obstacleHeight; //quit 100 pixels from the bottom of the screen
    ArrayList<Block> placedObstacles = new ArrayList<>();
    ArrayList<Block> obstacles = new ArrayList<>();
    //physics
    int velocityX = -8; //cactus moving left speed
    int velocityY = 0; //dinosaur jump speed
    int gravity = 1;

    boolean gameOver = false;
    int score = 0;

    Timer gameLoop;
    Timer placeObstaclesTimer;
    int placeObstaclesDelay = 3000;
    HashMap<String,byte[]> audioMap = new HashMap<>();


    public UtpGame() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

       // setLayout(null);

        setFocusable(true);
        addKeyListener(this);

       obstacles=AssetsManager.getObstacles(cactusX, cactusY, obstacleWidth, obstacleHeight);

        imagesHashMap =AssetsManager.getImages();
        //girl
        girl = new Block(girlX, girlY, girlWidth, girlHeight, imagesHashMap.get("characterRunning"));


        //game timer
        gameLoop = new Timer(1000/60, this); //1000/60 = 60 frames per 1000ms (1s), update
        gameLoop.start();

        //place obstacles timer
        placeObstaclesTimer = new Timer(placeObstaclesDelay, e -> placeObstacles());
        placeObstaclesTimer.start();

        audioMap=AssetsManager.getAudioMap();


        SoundPlayer.playInLoop("loop", audioMap.get("loop"));

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        //background
        String background =  String.valueOf(level);
        if(gameOver){
            background="dead";
        }else if(youWin){
            background="win";
        }
        g.drawImage(imagesHashMap.get(background), 0, 0, boardWidth, boardHeight, null);
        //girl
       if(girl.img!=null){
           g.drawImage(girl.img, girl.x, girl.y, girl.width, girl.height, null);
       }
        //Obstacles
        for (Block o : placedObstacles) {
            g.drawImage(o.img, o.x, o.y, o.width, o.height, null);
        }
        //score
        g.setColor(Color.black);
       g.setFont(new Font("Courier", Font.PLAIN, 32));

        if (gameOver) {

            SoundPlayer.play("dead", audioMap.get("dead"));
        }else if(!youWin){
            g.drawString(String.valueOf(score), 10, 100);

        }

    }

    public void move() {
        //girl
        velocityY += gravity;
        girl.y += velocityY;

        if (girl.y > girlY) { //stop the dinosaur from falling past the ground
            girl.y = girlY;
            velocityY = 0;
            girl.img = imagesHashMap.get("characterRunning");
        }


        for (Block o : placedObstacles) {
            o.x += velocityX;

            if (collision(girl, o)) {
                gameOver = true;
                girl.img = imagesHashMap.get("characterDead");
            }
        }
        //score
        score++;

        int factor=(forLevel*level);
        if(score>factor && level<maxLevel){
            level++;
            placeObstaclesTimer.setDelay(placeObstaclesDelay-level*100);
        }else if(score>factor && level==maxLevel){
           youWin=true;
        }
    }

    boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver || youWin) {
            placeObstaclesTimer.stop();
            gameLoop.stop();
            SoundPlayer.stop("loop");
        }
        if(youWin){
            SoundPlayer.play("win", audioMap.get("win"));
            girl.img = null;
            placedObstacles.clear();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
            if (girl.y == girlY && !youWin && !gameOver) {
               // velocityY = -17;
                velocityY = -25;
                girl.img = imagesHashMap.get("characterJumping");
                SoundPlayer.play("jump", audioMap.get("jump"));
            }
            
            if (gameOver || youWin) {
                //restart game by resetting conditions
                girl.y = girlY;
                girl.img = imagesHashMap.get("characterRunning");
                velocityY = 0;
                placedObstacles.clear();
                score = 0;
                level = 1;
                gameOver = false;
                youWin = false;
                gameLoop.start();
                placeObstaclesTimer.setDelay(placeObstaclesDelay);
                placeObstaclesTimer.start();
                SoundPlayer.playInLoop("loop", audioMap.get("loop"));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}


    void placeObstacles() {
        if (gameOver || youWin) {
            return;
        }
        int placeObstacleIndex = (int) (Math.random() * obstacles.size());
        Block placedObstacle = obstacles.get(placeObstacleIndex);
        placedObstacle.x = boardWidth;
        System.out.println(placedObstacle.x);
        placedObstacles.add(placedObstacle);
        //remove all obstacles if obstacle.x < 0

        placedObstacles.removeIf(o -> o.x < 0);

        if (placedObstacles.size() > 10) {
            placedObstacles.remove(0); //remove the first cactus from ArrayList
        }
    }
}
