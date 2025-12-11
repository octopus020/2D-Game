package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize=16;
    final int scale=3;
    public final int tileSize=originalTileSize*scale;
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth=tileSize*maxScreenCol;
    public final int screenHeight=tileSize*maxScreenRow;
    //World sett
    public final int maxWorldCol=30;
    public final int maxWorldRow=30;
    public final int maxMap=3;
    public int currentMap=0;
    //FPS
    int FPS=60;
    TileManager tileM=null;
    public KeyHandler keyH=new KeyHandler(this);
    Sound music=new Sound();
    //Sound se=new Sound();//pentru sound effect
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    public UI ui;
    Thread gameThread;
    //Entity si object
    public Player player;
    public Entity [][]obj=new Entity[maxMap][10];
    public Entity [][]npc=new Entity[maxMap][10];
    ArrayList<Entity> entityList=new ArrayList<>();
    //Game state
    public int gameState;
    public final int titleState=0;
    public final int playState=1;
    public final int pauseState=2;
    public final int optionState=3;
    public final int gameOverState=4;
    //spriteSheet
    private BufferedImage spriteSheet=null;
    //evenimente
    public EventHandler eHandler=new EventHandler(this);
    Config config=new Config(this);
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        BufferedImageLoader loader=new BufferedImageLoader();
        try
        {
            spriteSheet=loader.loadImage("/Spritesheet/Spritesheet.png");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        ui=new UI(this);
        player=new Player(this,keyH);
        tileM=new TileManager(this);
    }
    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        //playMusic(0);
        //stopMusic();
        gameState=titleState;
    }
    public void retry()
    {
        aSetter.setObject();
        player.hasKey=0;
        player.setDefaultPositions();
        player.restoreLife();
        ui.playTime=0;
    }
    public void restart()
    {
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLife();
        GameDataBase.delete();
        keyH.nou=false;
        stopMusic();
        ui.playTime=0;
    }
    public void startGameThread()
    {
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run()
    {
        double drawInterval=(double)1000000000/FPS;
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;
        while(gameThread!=null)
        {
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            lastTime=currentTime;
            if(delta>=1)
            {
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update()
    {
            if(gameState==playState)
            {
                //player
                player.update();
                //NPC
                for(int i=0;i<npc[currentMap].length;i++)
                    if(npc[currentMap][i]!=null)
                        npc[currentMap][i].update();
            }
            /*if(gameState==pauseState)
            {
            }*/

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;//more functions vs Graphics
        //Title screen
        if(gameState==titleState)
        {
            ui.draw(g2);
        }
        else {
            //tile
            tileM.draw(g2);
            //adaugare entitati la lista
            entityList.add(player);

            for(int i=0;i<npc[currentMap].length;i++)
            {
                if(npc[currentMap][i]!=null)
                   {
                       entityList.add(npc[currentMap][i]);
                   }
            }
            for(int i=0;i<obj[currentMap].length;i++)
            {
                if(obj[currentMap][i]!=null)
                {
                    entityList.add(obj[currentMap][i]);
                }
            }
            //sortare dupa care dintre entitati e mai jos in y
            Collections.sort(entityList,new Comparator<Entity>() {
                @Override
                public int compare(Entity e1,Entity e2)
                {
                    int result=Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });
            //draw entitati
            for(int i=0;i<entityList.size();i++)
            {
                entityList.get(i).draw(g2);
            }
            //golire lista de entitati ca sa nu creasca dupa fiecare loop
            entityList.clear();
            //UI
            ui.draw(g2);
            //scapa de ce a fost incarcat
            g2.dispose();
        }
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic()
    {
        music.stop();
    }
    //pentru un singur sunet fara loop sound effect
    /* public void playSE(int i)
       {
           sound.setFile(i);
           sound.play();
       }*/
    public BufferedImage getSpriteSheet()
    {
        return spriteSheet;
    }
}
