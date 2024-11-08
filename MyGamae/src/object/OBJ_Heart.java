package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		
		type = type_pickupOnly;
		name = "Heart";
		value = 2;
		down1 = setup("/objects/heart_full", gp.tileSize,gp.tileSize);
		image = setup("/objects/heart_full", gp.tileSize,gp.tileSize);
		image2= setup("/objects/heart_half", gp.tileSize,gp.tileSize);
		image3 = setup("/objects/heart_blank", gp.tileSize,gp.tileSize);
		
	}
	
}
