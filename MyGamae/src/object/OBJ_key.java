package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_key extends Entity{

 
	
  public OBJ_key(GamePanel gp) {
	  super(gp);
		
		name = "Key";
		down1 = setup("/objects/key", gp.tileSize,gp.tileSize);
		description = "[" + name + "]\nIt opens a door";
		
	}

}
