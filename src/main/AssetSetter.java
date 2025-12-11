package main;

import entity.NPC_Guardian;
import object.ObjectKey;
import object.ObjectDoor;

public class AssetSetter
{
    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp=gp;
    }
//initializare coordonate obiecte
    public void setObject()
    {
//mapNum se schimba in functie de ce vrei sa pui si pe ce mapa
        int mapNum=0;
        gp.obj[mapNum][0]=new ObjectKey(gp);
        gp.obj[mapNum][0].worldX=gp.tileSize*6;
        gp.obj[mapNum][0].worldY=gp.tileSize*23;

        gp.obj[mapNum][1]=new ObjectKey(gp);
        gp.obj[mapNum][1].worldX=gp.tileSize*25;
        gp.obj[mapNum][1].worldY=gp.tileSize*25;

        gp.obj[mapNum][2]=new ObjectDoor(gp);
        gp.obj[mapNum][2].worldX=gp.tileSize*13;
        gp.obj[mapNum][2].worldY=gp.tileSize*9;

        mapNum=1;
        gp.obj[mapNum][0]=new ObjectDoor(gp);
        gp.obj[mapNum][0].worldX=gp.tileSize*28;
        gp.obj[mapNum][0].worldY=gp.tileSize*24;

        gp.obj[mapNum][1]=new ObjectKey(gp);
        gp.obj[mapNum][1].worldX=gp.tileSize*3;
        gp.obj[mapNum][1].worldY=gp.tileSize*14;

        gp.obj[mapNum][2]=new ObjectKey(gp);
        gp.obj[mapNum][2].worldX=gp.tileSize*2;
        gp.obj[mapNum][2].worldY=gp.tileSize*27;

        mapNum=2;
        gp.obj[mapNum][0]=new ObjectDoor(gp);
        gp.obj[mapNum][0].worldX=gp.tileSize*28;
        gp.obj[mapNum][0].worldY=gp.tileSize*27;

        gp.obj[mapNum][1]=new ObjectKey(gp);
        gp.obj[mapNum][1].worldX=gp.tileSize*28;
        gp.obj[mapNum][1].worldY=gp.tileSize*4;

        gp.obj[mapNum][2]=new ObjectKey(gp);
        gp.obj[mapNum][2].worldX=gp.tileSize*4;
        gp.obj[mapNum][2].worldY=gp.tileSize*26;
    }
//initializare coordonate NPC
    public void setNPC()
    {
        int mapNum=0;
        mapNum=1;
        gp.npc[mapNum][0]=new NPC_Guardian(gp);
        gp.npc[mapNum][0].worldX=gp.tileSize*6;
        gp.npc[mapNum][0].worldY=gp.tileSize*3;

        gp.npc[mapNum][1]=new NPC_Guardian(gp);
        gp.npc[mapNum][1].worldX=gp.tileSize*13;
        gp.npc[mapNum][1].worldY=gp.tileSize*3;

        gp.npc[mapNum][3]=new NPC_Guardian(gp);
        gp.npc[mapNum][3].worldX=gp.tileSize*18;
        gp.npc[mapNum][3].worldY=gp.tileSize*13;

        gp.npc[mapNum][4]=new NPC_Guardian(gp);
        gp.npc[mapNum][4].worldX=gp.tileSize*17;
        gp.npc[mapNum][4].worldY=gp.tileSize*20;

        gp.npc[mapNum][5]=new NPC_Guardian(gp);
        gp.npc[mapNum][5].worldX=gp.tileSize*4;
        gp.npc[mapNum][5].worldY=gp.tileSize*25;

        gp.npc[mapNum][6]=new NPC_Guardian(gp);
        gp.npc[mapNum][6].worldX=gp.tileSize*20;
        gp.npc[mapNum][6].worldY=gp.tileSize*25;

        gp.npc[mapNum][7]=new NPC_Guardian(gp);
        gp.npc[mapNum][7].worldX=gp.tileSize*24;
        gp.npc[mapNum][7].worldY=gp.tileSize*26;

    }
}
