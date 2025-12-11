package tile;

import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager
{
    GamePanel gp;
    SpriteSheet ss;
    public Tile[] tile;//stocarea dalelor
    public int mapTileNumber[][][];
    public int mapTileNumber2[][][];
    public TileManager(GamePanel gp)
    {
        this.gp=gp;
        tile=new Tile[97];
        mapTileNumber=new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        mapTileNumber2=new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        ss=new SpriteSheet(gp.getSpriteSheet());
        getTileImage();
        loadMap("/maps/map1_layer2.csv", "/maps/map1_layer1.csv",0);
        loadMap("/maps/map2_layer2.csv", "/maps/map2_layer1.csv",1);
        loadMap("/maps/map3_layer2.csv", "/maps/map3_layer1.csv",2);
    }
//creeaza dale si seteaza coliziunea lor
    public void getTileImage()
    {
            int k=0,j=1;
            //Mapa 1
            for(int i=0;i<54;i++)
            {
                tile[i]=new Tile();
                if(k<9)
                {
                    k++;
                }
                else {k=1;j++;}
                tile[i].image = ss.grabImage(k+4, j+0, 16, 16);
                if((k>=4&&k<=9)&&(j>=4&&j<=6))
                {
                        tile[i].collision=true;
                }
            }
        // Mapa 2
        k=0;j=9;
        for(int i=54;i<97;i++)
        {
            tile[i]=new Tile();
            if(k<9)
            {
                k++;
            }
            else {k=1;j++;}
            tile[i].image = ss.grabImage(k+4, j+0, 16, 16);
            if((k>=1&&k<=9)&&(j>=10&&j<=12)||(k==5&&j==13))
            {
                tile[i].collision=true;
            }
        }
    }
//citeste fisierele care creeaza mapa
    public void loadMap(String filePath1,String filePath2,int map)
    {
        try
        {
            InputStream is=getClass().getResourceAsStream(filePath1);
            BufferedReader br=null;
            
            if (is != null) {
                br=new BufferedReader(new InputStreamReader(is));
            } else {
                // Fall back to file system
                br=new BufferedReader(new FileReader("res" + filePath1));
            }
            
            int col=0;
            int row=0;
            while(col<gp.maxWorldCol&&row<gp.maxWorldRow)
            {
                String line = br.readLine();
                while (col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
              is=getClass().getResourceAsStream(filePath2);
              if (is != null) {
                  br=new BufferedReader(new InputStreamReader(is));
              } else {
                  br=new BufferedReader(new FileReader("res" + filePath2));
              }
              col=0;
            row=0;
            while(col<gp.maxWorldCol&&row<gp.maxWorldRow)
            {
                String line = br.readLine();
                while (col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber2[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }

            br.close();
            if (is != null) is.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
//deseneaza mapa in functie de camera
    public void draw(Graphics2D g2)
    {
        int worldCol=0;
        int worldRow=0;

        while(worldCol<gp.maxWorldCol&&worldRow<gp.maxWorldRow)
        {
            int tileNum=mapTileNumber[gp.currentMap][worldCol][worldRow];
            int tileNum2=mapTileNumber2[gp.currentMap][worldCol][worldRow];
            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            int screenX=worldX-gp.player.worldX+gp.player.screenX;
            int screenY=worldY-gp.player.worldY+gp.player.screenY;
            //render doar la ce vede camera
            if(worldX+gp.tileSize>gp.player.worldX-gp.player.screenX&&
                    worldX-gp.tileSize<gp.player.worldX+gp.player.screenX&&
                    worldY+gp.tileSize>gp.player.worldY-gp.player.screenY&&
                    worldY-gp.tileSize<gp.player.worldY+gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize, gp.tileSize, null);
                g2.drawImage(tile[tileNum2].image,screenX,screenY,gp.tileSize, gp.tileSize, null);
            }
            worldCol++;
            if(worldCol==gp.maxWorldCol)
            {
                worldCol=0;
                worldRow++;
            }

        }
    }
}
