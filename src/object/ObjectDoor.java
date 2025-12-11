package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ObjectDoor extends Entity
{
    public ObjectDoor(GamePanel gp)
    {
        super(gp);
        solidArea=new Rectangle(0,0,48,48);
        name="Door";
        down1=ss.grabImage(5,7,xsize*3,ysize*2);
        collision=true;
    }
}
