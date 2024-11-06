package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Shield";
		down1 = setup("/objects/shield_wood",gp.tileSize,gp.tileSize);
		attackValue = 1;
		description = "[" + name + "]\nMade by wood.";
	}

}
