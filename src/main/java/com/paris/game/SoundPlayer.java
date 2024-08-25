package com.paris.game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.HashMap;

public class SoundPlayer {
    private  static final HashMap<String,Thread> threads = new HashMap<>();
    private  static  final HashMap<String,AdvancedPlayer> players = new HashMap<>();
    private  static  final HashMap<String,InputStream> streams = new HashMap<>();
    public static byte[] readFileToByteArray(String filePath) throws IOException {
        try (InputStream is = SoundPlayer.class.getResourceAsStream(filePath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (true) {
                assert is != null;
                if ((bytesRead = is.read(buffer)) == -1) break;
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
    public static  void playInLoop(String name,byte[] mp3Data) {
        InputStream inputStream = new ByteArrayInputStream(mp3Data);

        try {
            AdvancedPlayer player = new AdvancedPlayer(inputStream);
            streams.put(name,inputStream);
            players.put(name,player);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // Handle playback finished event
                    System.out.println("Playback finished.");
                }
            });
            Thread playThread = new Thread(() -> {
                while (players.containsKey(name)) {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        System.out.println("Error playing MP3 file: " + e.getMessage());
                        break;
                    }

                }
            });

            // Start the playback thread
            playThread.start();
            threads.put(name,playThread);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Playing " + name + " MP3 infinitely...");
    }

    public static void stop(String name){
        System.out.println("Stopping " + name + " MP3...");


      if(players.containsKey(name)){
          System.out.println("Closing player " + name + "...");
          AdvancedPlayer player = players.get(name);
          player.stop();
          player.close();
          players.remove(name);
      }
      if(streams.containsKey(name)){
          System.out.println("Closing stream " + name + "...");
          InputStream inputStream = streams.get(name);
          try {
              inputStream.close();
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
          streams.remove(name);
      }
        Thread t = threads.get(name);
        t.interrupt();
        threads.remove(name);
    }
    public static void play(String name,byte[] mp3Data){
        InputStream inputStream = new ByteArrayInputStream(mp3Data);
        try {
            AdvancedPlayer player = new AdvancedPlayer(inputStream);
            // Create a new thread for playing the MP3 file
            Thread playThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            });

            // Start the playback thread
            playThread.start();
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }

        // Main application code continues to run
        System.out.println("Playing " + name + " MP3 in background...");
        // Your other application logic here
    }

}
