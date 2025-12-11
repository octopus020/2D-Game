package entity;

import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.util.Random;

public class NPC_Guardian extends Entity
{
    public NPC_Guardian(GamePanel gp)
    {
        super(gp);
        xsize=16;
        ysize=32;
        type=1;
        solidArea=new Rectangle(3,24,42,72);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        direction="left";
        speed=1;
        getNPCImage();
    }
    public void getNPCImage()
    {
        left1=ss.grabImage(1,5,xsize*1,ysize*1);
        left2=ss.grabImage(2,5,xsize*1,ysize*1);
        left3=ss.grabImage(3,5,xsize*1,ysize*1);
        left4=ss.grabImage(4,5,xsize*1,ysize*1);
        right1=ss.grabImage(1,7,xsize*1,ysize*1);
        right2=ss.grabImage(2,7,xsize*1,ysize*1);
        right3=ss.grabImage(3,7,xsize*1,ysize*1);
        right4=ss.grabImage(4,7,xsize*1,ysize*1);
        up1=ss.grabImage(1,5,xsize*1,ysize*1);
        up2=ss.grabImage(2,5,xsize*1,ysize*1);
        up3=ss.grabImage(3,5,xsize*1,ysize*1);
        up4=ss.grabImage(4,5,xsize*1,ysize*1);
        down1=ss.grabImage(1,7,xsize*1,ysize*1);
        down2=ss.grabImage(2,7,xsize*1,ysize*1);
        down3=ss.grabImage(3,7,xsize*1,ysize*1);
        down4=ss.grabImage(4,7,xsize*1,ysize*1);
    }
    public void setAction()
    {
        actionLockCounter++;
        if(actionLockCounter==120)//NPC va continua miscarea 2 secunde
        {
            Random random=new Random();
            int i=random.nextInt(100)+1;//numar random de la 1 la 100
            if(i<=25)
            {
                direction="up";
            }
            if(i>25&&i<=50)
            {
                direction="down";
            }
            if(i>50&&i<=75)
            {
                direction="left";
            }
            if(i>75&&i<=100)
            {
                direction="right";
            }
            actionLockCounter=0;
        }

    }
}
