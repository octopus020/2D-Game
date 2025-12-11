package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ObjectHeart extends Entity {

    public ObjectHeart(GamePanel gp)
    {
        super(gp);

        name="Heart";
        image1=ss.grabImage(8,8,xsize*1,ysize*1);
        image2=ss.grabImage(9,8,xsize*1,ysize*1);
        image3=ss.grabImage(10,8,xsize*1,ysize*1);
    }
}
