package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader {
    private BufferedImage image;
    public BufferedImage loadImage(String path) throws IOException
    {
        try {
            // Try loading from classpath first
            var resource = getClass().getResource(path);
            if (resource != null) {
                image = ImageIO.read(resource);
            } else {
                // Fall back to file system (from project root)
                File file = new File("res" + path);
                if (!file.exists()) {
                    throw new IOException("Resource not found: " + path + " (checked: " + file.getAbsolutePath() + ")");
                }
                image = ImageIO.read(file);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
}
