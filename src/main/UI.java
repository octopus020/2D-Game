package main;


import entity.Entity;
import object.ObjectHeart;
import object.ObjectKey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI
{
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_60B;//40 plain,80 bold
    BufferedImage heart_full, heart_empty,heart_half;
    BufferedImage keyImage;
    public int messageCounter=0;
    public int counter=0;
    public boolean messageOn=false;
    public String message="";
    public boolean levelFinished=false;
    public boolean gameFinished=false;
    public int gameFinishedCounter=0;
    double playTime;
    DecimalFormat dFormat=new DecimalFormat("#0.00");
    public int cmdNum=0;//cursor navigat menu
    int subState=0;
    public UI(GamePanel gp)
    {
        this.gp=gp;
        arial_40=new Font("Arial",Font.PLAIN,40);
        arial_60B=new Font("Arial",Font.BOLD,60);
        ObjectKey key=new ObjectKey(gp);
        keyImage=key.down1;

        //hud object
        Entity heart=new ObjectHeart(gp);
        heart_full=heart.image3;
        heart_half=heart.image2;
        heart_empty=heart.image1;
    }
    public void showMessage(String text)
    {
        message=text;
        messageOn=true;
    }
    public void draw(Graphics2D g2)
    {
        this.g2=g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
//titlu
        if(gp.gameState==gp.titleState)
        {
            drawTitleScreen();
        }
        if(gp.gameState==gp.playState) {
//Play
            if (levelFinished == true)
            {
                counter++;
                String text;
                int textlen;
                int x;
                int y;
                if(counter<120) {
                    if(gameFinished==false)
                    {
                        text = "NEXT LEVEL!";
                        textlen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                        x = gp.screenWidth / 2 - textlen / 2;
                        y = gp.screenHeight / 2 - (gp.tileSize * 3);
                        g2.drawString(text, x, y);
                    }

                    text = "Your time is " + dFormat.format(playTime) + " seconds!";
                    textlen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                    x = gp.screenWidth / 2 - textlen / 2;
                    y = gp.screenHeight / 2 + (gp.tileSize * 4);
                    g2.drawString(text, x, y);

                    g2.setFont(arial_60B);
                    g2.setColor(Color.yellow);
                    text = "CONGRATULATIONS";
                    textlen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                    x = gp.screenWidth / 2 - textlen / 2;
                    y = gp.screenHeight / 2 + (gp.tileSize * 2);
                    g2.drawString(text, x, y);
                    if(gameFinished == true)
                    {
                        gp.gameThread=null;
                        gameFinishedCounter++;
                        // Transition to title screen after 4 seconds (240 frames at 60 FPS)
                        if(gameFinishedCounter > 240) {
                            gp.gameState = gp.titleState;
                            gameFinishedCounter = 0;
                            counter = 0;
                            levelFinished = false;
                            gameFinished = false;
                            gp.keyH.enterPressed = false;
                        }
                    }
                }
                else{
                    counter=0;levelFinished=false;
                }

            }
            else
            {
                drawPlayerLife();
//Timp
                playTime+=(double)1/60;
                g2.drawString("Time:"+dFormat.format(playTime),gp.tileSize*11,65);
//Mesaj
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("x" + gp.player.hasKey, 74, 65);
                if (messageOn == true)
                {
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5-messageCounter);

                    messageCounter++;
                    if (messageCounter > 100)
                    {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
        }
//Pauza
        if(gp.gameState==gp.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }
//Options
        if(gp.gameState==gp.optionState)
        {
            drawOptionsScreen();
        }
//Game over
        if(gp.gameState==gp.gameOverState)
        {
            drawGameOverScreen();
        }
    }
    public void drawGameOverScreen()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x,y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
        text="Game Over!";
        //Umbra
        g2.setColor(Color.black);
        x=getXforCenteredText(text);
        y=gp.tileSize*4;
        //Main
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);
        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text="Retry";
        x=getXforCenteredText(text);
        y+=gp.tileSize*4;
        g2.drawString(text,x,y);
        if(cmdNum==0)
        {
            g2.drawString(">",x-40,y);
        }
        //Iesire de tot
        text="Quit";
        x=getXforCenteredText(text);
        y+=55;
        g2.drawString(text,x,y);
        if(cmdNum==1)
        {
            g2.drawString(">",x-40,y);
        }
    }
    public void drawTitleScreen()
    {
            g2.setColor(Color.GRAY);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
            String text = "Orpheus Odyssey";
            int x = getXforCenteredText(text), y = gp.tileSize * 3;
//Umbra titlu
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
//Culori
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
//Caracterul in mijloc
            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
//Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (cmdNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "CONTINUE GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (cmdNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "QUIT GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (cmdNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
    }
    public void drawPlayerLife()
    {
        int x=gp.tileSize/2;
        int y=gp.tileSize*2;
        int i=0;
//Inimi maxime
        while(i<gp.player.maxLife/2)
        {
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x+=gp.tileSize;
        }
//Reset
        x=gp.tileSize/2;
        y=gp.tileSize*2;
        i=0;
//Inimi curente
        while(i<gp.player.life)
        {
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life)
            {
                g2.drawImage(heart_full,x,y,null);
            }
            ++i;
            x+=gp.tileSize;
        }
    }
    public void drawPauseScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text="PAUSED";
        int x=getXforCenteredText(text);
        int y=gp.screenHeight/2;
        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text)
    {
        int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-length/2;
        return x;
    }
    public void drawOptionsScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int frameX=gp.tileSize*4;
        int frameY=gp.tileSize;
        int frameWidth=gp.tileSize*10;
        int frameHeight=gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState)
        {
            case 0:options_top(frameX,frameY);break;
            case 1:options_control(frameX,frameY);break;
            case 2:options_endGameConfirmation(frameX,frameY);break;
        }
        gp.keyH.enterPressed=false;
    }
    public void options_top(int frameX,int frameY)
    {
        int textX;
        int textY;
        //title
        String text="Options";
        textX=getXforCenteredText(text);
        textY=frameY+gp.tileSize;
        g2.drawString(text,textX,textY);
        //Music
        textX=frameX+gp.tileSize;
        textY+=gp.tileSize*2;
        g2.drawString("Music",textX,textY);
        if(cmdNum==0)
        {
            g2.drawString(">",textX-25,textY);
        }
        //Control
        textY+=gp.tileSize;
        g2.drawString("Control",textX,textY);
        if(cmdNum==1)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                subState=1;
                cmdNum=0;
            }
        }
        //EndGame
        textY+=gp.tileSize;
        g2.drawString("End Game",textX,textY);
        if(cmdNum==2)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                subState=2;
                cmdNum=0;
            }
        }
        //Back
        textY+=gp.tileSize*3;
        g2.drawString("Back",textX,textY);
        if(cmdNum==3)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                gp.gameState=gp.playState;
                cmdNum=0;
            }
        }
        //Music
        textY=frameY+gp.tileSize*2+24;
        textX=frameX+(int)(gp.tileSize*4.5);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,120,24);
        int volumeWidth=24*gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);
        gp.config.saveConfig();
    }
    public void options_control(int frameX,int frameY)
    {
        int textX;
        int textY;
        //Title
        String text="Control";
        textX=getXforCenteredText(text);
        textY=frameY+gp.tileSize;
        g2.drawString(text,textX,textY);

        textX=frameX+gp.tileSize;
        textY+=gp.tileSize;
        g2.drawString("Move",textX,textY);textY+=gp.tileSize;
        g2.drawString("Pause",textX,textY);textY+=gp.tileSize;
        g2.drawString("Options",textX,textY);textY+=gp.tileSize;

        textY=frameY+gp.tileSize*2;
        textX=frameX+gp.tileSize*4;
        g2.drawString("W/A/S/D",textX,textY);textY+=gp.tileSize;
        g2.drawString("P",textX,textY);textY+=gp.tileSize;
        g2.drawString("ESCAPE",textX,textY);textY+=gp.tileSize;
        //Back
        textY=frameY+gp.tileSize*9;
        textX=frameX+gp.tileSize;
        g2.drawString("Back",textX,textY);
        if(cmdNum==0)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                subState=0;
                cmdNum=2;
            }
        }
    }
    public void options_endGameConfirmation(int frameX,int frameY)
    {
        int textX=frameX+gp.tileSize;
        int textY=frameY+gp.tileSize*3;
        String currentDialogue="Quit the game and \n return to the title screen?";

        for(String line:currentDialogue.split("\n"))
        {
            g2.drawString(line,textX,textY);
            textY+=40;
        }
        //Yes
        String text="Yes";
        textX=getXforCenteredText(text);
        textY+=gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if(cmdNum==0)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                subState=0;
                gp.stopMusic();
                gp.gameState=gp.titleState;
            }
        }
        //No
        text="No";
        textX=getXforCenteredText(text);
        textY+=gp.tileSize;
        g2.drawString(text,textX,textY);
        if(cmdNum==1)
        {
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed==true)
            {
                subState=0;
                cmdNum=2;
            }
        }
    }
    public void drawSubWindow(int x,int y,int width,int height)
    {
        Color c= new Color(0,0,0);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c=new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
}
