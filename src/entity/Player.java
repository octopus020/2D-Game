package entity;
import main.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity
{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey=0;
    public Player(GamePanel gp,KeyHandler keyH)
    {
        super(gp);
        worldX=gp.tileSize*10;
        worldY=gp.tileSize*3;
        type=0;
        this.keyH=keyH;
        screenX=gp.screenWidth/2-(gp.tileSize/2);
        screenY=gp.screenHeight/2-(gp.tileSize/2);
        solidArea=new Rectangle(18,20,24,20);//12 20 24 20
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues()
    {
        //player stats
        maxLife=6;
        speed=6;
        life=maxLife;
        direction="down";
        int []x=new int[5];
        x=GameDataBase.load();
        gp.currentMap=x[0];
        worldX=gp.tileSize*x[1];
        worldY= gp.tileSize*x[2];
        life=x[3];
        hasKey=x[4];
    }
    public void setDefaultPositions()
    {
        switch(gp.currentMap)
        {
            case 0:worldX=gp.tileSize*10;
                worldY= gp.tileSize*3;break;
            case 1:worldX=gp.tileSize*1;
                worldY= gp.tileSize*3;break;
            case 2:worldX=gp.tileSize*2;
                        worldY= gp.tileSize*3;break;
        }

        direction="down";
    }
    public void restoreLife()
    {
        life=maxLife;
        invincible=false;
    }
    public void getPlayerImage()
    {
        //citire sprite-uri
            up1=ss.grabImage(1,4,xsize*1,ysize*1);
            up2=ss.grabImage(2,4,xsize*1,ysize*1);
            up3=ss.grabImage(3,4,xsize*1,ysize*1);
            up4=ss.grabImage(4,4,xsize*1,ysize*1);
            down1=ss.grabImage(1,1,xsize*1,ysize*1);
            down2=ss.grabImage(2,1,xsize*1,ysize*1);
            down3=ss.grabImage(3,1,xsize*1,ysize*1);
            down4=ss.grabImage(4,1,xsize*1,ysize*1);
            left1=ss.grabImage(1,2,xsize*1,ysize*1);
            left2=ss.grabImage(2,2,xsize*1,ysize*1);
            left3=ss.grabImage(3,2,xsize*1,ysize*1);
            left4=ss.grabImage(4,2,xsize*1,ysize*1);
            right1=ss.grabImage(1,3,xsize*1,ysize*1);
            right2=ss.grabImage(2,3,xsize*1,ysize*1);
            right3=ss.grabImage(3,3,xsize*1,ysize*1);
            right4=ss.grabImage(4,3,xsize*1,ysize*1);

    }
    public void update()
    {
        //atunci cand sta pe loc sa nu se miste
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            //verificare coliziune dale
            collisionOn=false;
            gp.cChecker.checkTile(this);
            //verificare coliziune obiect
            int objIndex=gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);
            //verificare coliziune cu NPC
            int npcIndex=gp.cChecker.checkEntity(this,gp.npc);//player si npc
            interactNPC(npcIndex);
            //event check
            gp.eHandler.checkEvent();
            //daca coliziune e falsa, jucatorul se poate misca
            if(collisionOn==false)
            {
                switch (direction)
                {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 8) {
                switch (spriteNumber) {
                    case 1:
                        spriteNumber = 2;
                        break;
                    case 2:
                        spriteNumber = 3;
                        break;
                    case 3:
                        spriteNumber = 4;
                        break;
                    case 4:
                        spriteNumber = 1;
                        break;

                }
                spriteCounter=0;
            }
        }
        if(invincible==true)
        {
            invincibleCounter++;
        }
        if(invincibleCounter>60)
        {
            invincible=false;
            invincibleCounter=0;
        }
        if(life<=0)
        {
            gp.gameState=gp.gameOverState;
        }
    }
    public void pickUpObject(int i)
    {
        if(i!=999)
        {
            String objectName=gp.obj[gp.currentMap][i].name;
            switch (objectName) {
                case"Key":
                    hasKey++;
                    gp.obj[gp.currentMap][i]=null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case"Door":
                    if(hasKey==2)
                    {
                        hasKey=0;
                        gp.ui.levelFinished=true;
                        gp.ui.showMessage("You opened the door!");
                        gp.obj[gp.currentMap][i].collision=false;
                        if(gp.currentMap==2)
                        {
                            gp.ui.gameFinished=true;
                            gp.stopMusic();
                        }
                    }
                    else {
                        gp.ui.showMessage("You got "+hasKey+"/2 keys");
                    }
                    break;
            }
        }
    }
    public void interactNPC(int i)
    {
        if(i!=999)
        {
            if(invincible==false) {
                gp.ui.showMessage("Stop getting in NPC's way!");
                /*gp.player.worldX=gp.tileSize*10;
                gp.player.worldY=gp.tileSize*3;*/
                gp.eHandler.damagePit(10, 3, gp.playState);
                gp.eHandler.teleport(1,1,3);
                invincible=true;
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image=null;
        switch(direction)
        {
            case "up":
                switch(spriteNumber)
                {
                    case 1:
                        image=up1;
                        break;
                    case 2:
                        image=up2;
                        break;
                    case 3:
                        image=up3;
                        break;
                    case 4:
                        image=up4;
                        break;
                }
                break;
            case "down":
                switch(spriteNumber)
                {
                    case 1:
                        image=down1;
                        break;
                    case 2:
                        image=down2;
                        break;
                    case 3:
                        image=down3;
                        break;
                    case 4:
                        image=down4;
                        break;
                }
                break;
            case"left":
                switch(spriteNumber)
                {
                    case 1:
                        image=left1;
                        break;
                    case 2:
                        image=left2;
                        break;
                    case 3:
                        image=left3;
                        break;
                    case 4:
                        image=left4;
                        break;
                }
                break;
            case"right":
                switch(spriteNumber)
                {
                    case 1:
                        image=right1;
                        break;
                    case 2:
                        image=right2;
                        break;
                    case 3:
                        image=right3;
                        break;
                    case 4:
                        image=right4;
                        break;
                }
                break;
        }
        //O sa faca caracteru transparent pe jumate
        if(invincible==true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);
        //reset pentru tranasparenta imaginii
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
