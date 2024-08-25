package com.paris.game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AssetsManager {

    public static HashMap<String,byte[]>   getAudioMap(){

        HashMap<String,byte[]> audioMap = new HashMap<>();
        try {
            audioMap.put("loop",SoundPlayer.readFileToByteArray("/sounds/running.mp3"));
            audioMap.put("dead",SoundPlayer.readFileToByteArray("/sounds/dead.mp3"));
            audioMap.put("win",SoundPlayer.readFileToByteArray("/sounds/you-win.mp3"));
            audioMap.put("jump",SoundPlayer.readFileToByteArray("/sounds/jump.mp3"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return audioMap;

    }
    public  static HashMap<String,Image> getImages(){
        HashMap<String,Image> imageHashMap = new HashMap<>();
        //Load 10 background images
        for(int i = 1; i <= 10; i++) {
            Image background = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/background/" + i + ".png"))).getImage();
            imageHashMap.put(String.valueOf(i), background);
        }
        Image congrats = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/background/12.png"))).getImage();
        Image gamerOver = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/background/11.png"))).getImage();
        Image characterRunning = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/character/run-ok.gif"))).getImage();
       Image characterJumping = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/character/jump-ok.png"))).getImage();
       Image characterDead = new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/character/dead-ok.png"))).getImage();
        imageHashMap.put("win", congrats);
        imageHashMap.put("dead", gamerOver);
        imageHashMap.put("characterRunning", characterRunning);
        imageHashMap.put("characterJumping", characterJumping);
        imageHashMap.put("characterDead", characterDead);

        return imageHashMap;
    }
    public  static  ArrayList<Block> getObstacles(int x, int y, int width, int height) {
        ArrayList<Block> obstacles = new ArrayList<>();

        Image gamepad= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/gamepad.png"))).getImage();
        Image tv= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/tv.png"))).getImage();
        Image tiktok= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/tiktok.png"))).getImage();
        Image smartphone= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/smartphone.png"))).getImage();
       Image instagram= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/instagram.png"))).getImage();
       Image facebook= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/facebook.png"))).getImage();
       Image netflix= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/netflix.png"))).getImage();
       Image dota2= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/dota-2.png"))).getImage();
      Image drug= new ImageIcon(Objects.requireNonNull(AssetsManager.class.getResource("/img/obstacles/drug.png"))).getImage();
       obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        gamepad)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        tv)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        tiktok)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        smartphone)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        instagram)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        facebook)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        netflix)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        dota2)
        );
        obstacles.add(
                new Block(x,
                        y,
                        width,
                        height,
                        drug)
        );
        return obstacles;
    }
}
