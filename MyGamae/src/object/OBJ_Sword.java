package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity{

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Sword";
		down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nAn old sword";
		
		
	}

}
