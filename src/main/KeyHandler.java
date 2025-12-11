package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed;
    public boolean nou=false;
    public int x=0;
    GamePanel gp;
    public KeyHandler(GamePanel gp)
    {
        this.gp=gp;
    }
    @Override
    public void keyTyped(KeyEvent e)
    {

    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyCode();
        //Title state
        if(gp.gameState==gp.titleState)
        {
            titleState(code);
        }
        else if(gp.gameState==gp.playState)
        {
            playState(code);
        }
        else if(gp.gameState==gp.pauseState)
        {
            pauseState(code);
        }
        else if(gp.gameState==gp.optionState)
        {
            optionState(code);
        }
        else if(gp.gameState==gp.gameOverState)
        {
            gameOverState(code);
        }
    }
    public void titleState(int code)
    {
        if(nou==true)
        {
           GameDataBase.update(gp.currentMap,gp.player.worldX/gp.tileSize,gp.player.worldY/gp.tileSize,gp.player.life,gp.player.hasKey);
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.cmdNum--;
            if (gp.ui.cmdNum < 0)
                gp.ui.cmdNum = 2;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.cmdNum++;
            if (gp.ui.cmdNum > 2)
                gp.ui.cmdNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            //New game
            if (gp.ui.cmdNum == 0&&nou==false) {
                nou=true;
                GameDataBase.delete();
                GameDataBase.insert(0,10,3,6,0);
                gp.player.setDefaultValues();
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            else if (gp.ui.cmdNum == 0&&nou==true)
            {
                gp.ui.playTime=0;
                GameDataBase.delete();
                GameDataBase.insert(0,10,3,6,0);
                gp.player.setDefaultValues();
                gp.aSetter.setObject();
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            //Continue game
            if (gp.ui.cmdNum == 1&&nou==true) {
                int []x=new int[5];
                x=GameDataBase.load();
                System.out.println(x[0]+""+x[1]+""+x[2]+""+x[3]+""+x[4]);
                gp.gameState = gp.playState;
                gp.playMusic(0);
                /*gp.currentMap=x[0];
                gp.player.worldX=x[1]*gp.tileSize;
                gp.player.worldY=x[2]*gp.tileSize;
                gp.player.life=x[3];
                gp.player.hasKey=x[4];*/
            }
            //Quit
            if (gp.ui.cmdNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code)
    {
        // Allow skipping to title screen when game is finished
        if(gp.ui.gameFinished == true && code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.titleState;
            gp.ui.gameFinishedCounter = 0;
            gp.ui.counter = 0;
            gp.ui.levelFinished = false;
            gp.ui.gameFinished = false;
            enterPressed = false;
            return;
        }
        
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.optionState;
        }
        if (code == KeyEvent.VK_P)
        {
            gp.stopMusic();
            gp.gameState = gp.pauseState;
        }
        if(code==KeyEvent.VK_R)
        {
            switch (gp.currentMap)
            {
                case 0:gp.tileM.loadMap("/maps/map1_layer2.csv", "/maps/map1_layer1.csv",0);break;
                case 1:gp.tileM.loadMap("/maps/map2_layer2.csv", "/maps/map2_layer1.csv",1);break;
            }
        }
    }
    public void pauseState(int code)
    {
        gp.stopMusic();
        if(code == KeyEvent.VK_P)
        {
            gp.playMusic(0);
            gp.gameState = gp.playState;
        }
    }
    public void optionState(int code)
    {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code==KeyEvent.VK_ENTER)
        {
            enterPressed=true;
        }
        int maxCmdNum=0;
        switch(gp.ui.subState)
        {
            case 0:maxCmdNum=3;break;
            case 1:maxCmdNum=0;break;
            case 2:maxCmdNum=1;break;
        }
        if(code==KeyEvent.VK_W)
        {
            gp.ui.cmdNum--;
            if(gp.ui.cmdNum<0)
            {
                gp.ui.cmdNum = maxCmdNum;
            }
        }
        if(code==KeyEvent.VK_S)
        {
            gp.ui.cmdNum++;
            if(gp.ui.cmdNum>maxCmdNum)
            {
                gp.ui.cmdNum = 0;
            }
        }
        if(code==KeyEvent.VK_A)
        {
            if(gp.ui.subState==0)
            {
                if(gp.ui.cmdNum==0&&gp.music.volumeScale>0)
                {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
            }
        }
        if(code==KeyEvent.VK_D)
        {
            if(gp.ui.subState==0)
            {
                if(gp.ui.cmdNum==0&&gp.music.volumeScale<=5)
                {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
            }
        }
    }
    public void gameOverState(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            gp.ui.cmdNum--;
            if(gp.ui.cmdNum<0)
            {
                gp.ui.cmdNum = 1;
            }
        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.cmdNum++;
            if(gp.ui.cmdNum>1)
            {
                gp.ui.cmdNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(gp.ui.cmdNum==0)
            {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.cmdNum==1)
            {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        int code=e.getKeyCode();
        if(code==KeyEvent.VK_W)
        {
            upPressed=false;
        }
        if(code==KeyEvent.VK_S)
        {
            downPressed=false;
        }
        if(code==KeyEvent.VK_A)
        {
            leftPressed=false;
        }
        if(code==KeyEvent.VK_D)
        {
            rightPressed=false;
        }
    }
}
