package main;

import entity.NPC_MoMon;
import monster.Mon_GteenSlime;
import object.OBJ_Axe;
import object.OBJ_Door;
import object.OBJ_bomb;
import object.OBJ_key;


public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setOject() {
		
		       int i = 0; 
				// Door
				gp.obj[i] = new OBJ_Door(gp);
				gp.obj[i].worldX = 41 * gp.tileSize;
				gp.obj[i].worldY = 17 * gp.tileSize;
				i++;
				gp.obj[i] = new OBJ_key(gp);
				gp.obj[i].worldX = 34 * gp.tileSize;
				gp.obj[i].worldY = 8 * gp.tileSize;
				i++;
				gp.obj[i] = new OBJ_Axe(gp);
				gp.obj[i].worldX = 31 * gp.tileSize;
				gp.obj[i].worldY = 25 * gp.tileSize;
				i++;
				
				
				
				
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC_MoMon(gp);
		gp.npc[0].worldX = gp.tileSize * 26;
		gp.npc[0].worldY = gp.tileSize * 26;
		
		
		
	}
	
	public void setMonster() {
		
		int i = 0;
		
		gp.monster[i] = new Mon_GteenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 42;
		gp.monster[i].worldY = gp.tileSize * 33;
		
		i++;
		gp.monster[i] = new Mon_GteenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 24;
		gp.monster[i].worldY = gp.tileSize * 31;
		
		i++;
		gp.monster[i] = new Mon_GteenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 24;
		gp.monster[i].worldY = gp.tileSize * 35;
		
		i++;
		gp.monster[i] = new Mon_GteenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 42;
		gp.monster[i].worldY = gp.tileSize * 29;
		
	}
	
}
