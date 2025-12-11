package object;

import entity.Entity;
import main.EventRect;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ObjectKey extends Entity {
    public ObjectKey(GamePanel gp)
    {
        super(gp);
        solidArea=new Rectangle(0,0,48,48);
        name="Key";
        down1=ss.grabImage(1,9,xsize*3,ysize*3);
    }
}
