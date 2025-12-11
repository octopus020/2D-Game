package entity;

import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity
{
    GamePanel gp;
    public SpriteSheet ss;
    public int worldX,worldY;
//obiect
    public BufferedImage image1,image2,image3;
    public boolean collision=false;
//scalare pe dale
    public int xsize=16,ysize=16;
    public BufferedImage up1,up2,up3,up4,down1,down2,down3,down4,left1,left2,left3,left4,right1,right2,right3,right4;
//obiectele vor avea mereu directia asta
    public String direction="down";
    public int spriteCounter=0;
    public int spriteNumber=1;
    public Rectangle solidArea;
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn=false;
//NPC sa aiba o miscare continua
    public int actionLockCounter=0;
    public boolean invincible=false;
    public int invincibleCounter=0;
//stats character
    public int type;//0 player,1 npc
    public String name;
    public int maxLife;
    public int life;
    public int speed;

    public Entity(GamePanel gp)
    {
        this.gp=gp;
        ss=new SpriteSheet(gp.getSpriteSheet());
    }
    public void setAction()
    {

    }
    public void update()
    {
        setAction();
        collisionOn=false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        boolean contactPlayer=gp.cChecker.checkPlayer(this);
        if(this.type==1&&contactPlayer==true)
        {
            if(gp.player.invincible==false)
            {
                gp.ui.showMessage("Stop staying in NPC's way!");
                gp.player.life-=1;
                gp.player.invincible=true;
            }
        }
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
    public void draw(Graphics2D g2)
    {
        BufferedImage image=null;
        int screenX=worldX-gp.player.worldX+gp.player.screenX;
        int screenY=worldY-gp.player.worldY+gp.player.screenY;
        //render doar la ce vede camera
        if(worldX+gp.tileSize>gp.player.worldX-gp.player.screenX&&
                worldX-gp.tileSize<gp.player.worldX+gp.player.screenX&&
                worldY+gp.tileSize>gp.player.worldY-gp.player.screenY&&
                worldY-gp.tileSize<gp.player.worldY+gp.player.screenY)
        {
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
            g2.drawImage(image,screenX,screenY,gp.tileSize*xsize/16, gp.tileSize*ysize/16, null);
        }

    }
}
