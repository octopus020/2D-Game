package main;

import java.awt.*;

public class EventHandler
{
    GamePanel gp;
//un Rectangulare care verifica interactiuni
    EventRect eventRect[][][];
//pentru implementari viitoare de intoarcere la un nivel anterior
    int previouseEventX,previouseEventY;
// evenimentul sa astepte playerul sa se miste cu o dala
    boolean canTouchEvent=true;
    public EventHandler(GamePanel gp)
    {
        this.gp = gp;
        eventRect=new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map=0;
        int col=0;
        int row=0;
//instantiere de Rectangulare pe harta
        while(map<gp.maxMap&&col<gp.maxWorldCol&&row<gp.maxWorldRow)
        {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x=23;
            eventRect[map][col][row].y=23;
            eventRect[map][col][row].width=2;
            eventRect[map][col][row].height=2;
            eventRect[map][col][row].eventRectDefaultX=eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY=eventRect[map][col][row].y;
            col++;
            if(col==gp.maxWorldCol)
            {
                col=0;row++;
                if(row==gp.maxWorldRow)
                {
                    row=0;
                    map++;
                }
            }
        }
    }
    public void checkEvent()
    {
//verificare daca playerul este cu o dala mai departe de ultimul eveniment
        int xDistance=Math.abs(gp.player.worldX-previouseEventX);
        int yDistance=Math.abs(gp.player.worldY-previouseEventY);
        int distance=Math.max(xDistance,yDistance);
        if(distance>gp.tileSize)
        {
            canTouchEvent=true;
        }
        if(canTouchEvent==true)
        {
//tranzitie nivel 1 spre 2
            if(hit(13,9,"any",0)==true)
            {
                teleport(1,1,3);
                gp.player.speed=10;
                gp.ui.playTime=0;
            }
//tranzitie nivel 2 spre 3
            else if(hit(28,24,"any",1)==true)
            {
                teleport(2,2,3);
                gp.ui.playTime=0;
            }
//daca ating un anumit tile sa iau damage
            if(hit(5,3,"any",2)==true)
                damagePit(5,3, gp.playState);
            if(hit(7,2,"any",2)==true)
                damagePit(7,2, gp.playState);
            if(hit(9,3,"any",2)==true)
                damagePit(9,3, gp.playState);
            if(hit(12,2,"any",2)==true)
                damagePit(12,2, gp.playState);
            if(hit(15,3,"any",2)==true)
                damagePit(15,3, gp.playState);
            if(hit(19,2,"any",2)==true)
                damagePit(19,2, gp.playState);
            if(hit(22,3,"any",2)==true)
                damagePit(22,3, gp.playState);
            if(hit(25,2,"any",2)==true)
                damagePit(25,2, gp.playState);
            if(hit(21,10,"any",2)==true)
                damagePit(21,10, gp.playState);
            if(hit(15,9,"any",2)==true)
                damagePit(15,9, gp.playState);
            if(hit(12,10,"any",2)==true)
                damagePit(12,10, gp.playState);
            if(hit(10,9,"any",2)==true)
                damagePit(10,9, gp.playState);
            if(hit(6,9,"any",2)==true)
                damagePit(6,9, gp.playState);
            if(hit(2,10,"any",2)==true)
                damagePit(2,10, gp.playState);
            if(hit(4,14,"any",2)==true)
                damagePit(4,14, gp.playState);
            if(hit(7,13,"any",2)==true)
                damagePit(7,13, gp.playState);
            if(hit(10,13,"any",2)==true)
                damagePit(10,13, gp.playState);
            if(hit(13,13,"any",2)==true)
                damagePit(13,13, gp.playState);
            if(hit(15,13,"any",2)==true)
                damagePit(15,13, gp.playState);
            if(hit(17,14,"any",2)==true)
                damagePit(17,14, gp.playState);
            if(hit(21,13,"any",2)==true)
                damagePit(21,13, gp.playState);
            if(hit(26,14,"any",2)==true)
                damagePit(26,14, gp.playState);
            if(hit(28,13,"any",2)==true)
                damagePit(28,13, gp.playState);
            if(hit(1,20,"any",2)==true)
                damagePit(1,20, gp.playState);
            if(hit(2,22,"any",2)==true)
                damagePit(2,22, gp.playState);
            if(hit(1,24,"any",2)==true)
                damagePit(1,24, gp.playState);
            if(hit(2,26,"any",2)==true)
                damagePit(2,26, gp.playState);
            if(hit(4,27,"any",2)==true)
                damagePit(4,27, gp.playState);
            if(hit(5,26,"any",2)==true)
                damagePit(5,26, gp.playState);
            if(hit(7,27,"any",2)==true)
                damagePit(7,27, gp.playState);
            if(hit(9,25,"any",2)==true)
                damagePit(9,25, gp.playState);
            if(hit(10,26,"any",2)==true)
                damagePit(10,26, gp.playState);
            if(hit(12,27,"any",2)==true)
                damagePit(12,27, gp.playState);
            if(hit(14,25,"any",2)==true)
                damagePit(14,25, gp.playState);
            if(hit(15,27,"any",2)==true)
                damagePit(15,27, gp.playState);
            if(hit(17,25,"any",2)==true)
                damagePit(17,25, gp.playState);
            if(hit(19,27,"any",2)==true)
                damagePit(19,27, gp.playState);
            if(hit(20,26,"any",2)==true)
                damagePit(20,26, gp.playState);
            if(hit(22,25,"any",2)==true)
                damagePit(22,25, gp.playState);
        }
    }
//daca se atinge un Rectangular
    public boolean hit(int col,int row,String reqDirection,int map)
    {
        boolean hit=false;
        if(map==gp.currentMap)
        {

            gp.player.solidArea.x=gp.player.worldX+gp.player.solidArea.x;
            gp.player.solidArea.y=gp.player.worldY+gp.player.solidArea.y;
            eventRect[map][col][row].x=col*gp.tileSize+eventRect[map][col][row].x;
            eventRect[map][col][row].y=row*gp.tileSize+eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row])&&eventRect[map][col][row].eventDone==false)
            {
//eventDone face ca evenimentul de la checkevent sa se petreaca o data
                if((gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any")))
                    hit=true;
                previouseEventX=gp.player.worldX;
                previouseEventY=gp.player.worldY;
            }
            gp.player.solidArea.x=gp.player.solidAreaDefaultX;
            gp.player.solidArea.y=gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x=eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y=eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
//teleportare la o anumita pozitie dintr-o anumita harta
    public void teleport(int map,int col,int row)
    {
        gp.currentMap=map;
        gp.player.worldX=gp.tileSize*col;
        gp.player.worldY=gp.tileSize*row;
        previouseEventX=gp.player.worldX;
        previouseEventY=gp.player.worldY;
        canTouchEvent=false;
    }
//zona de damage
    public void damagePit(int col,int row,int gameState)
    {
        gp.gameState= gameState;
        gp.player.life-=1;
       // eventRect[gp.currentMap][col][row].eventDone=true;
        canTouchEvent=false;
    }
}
