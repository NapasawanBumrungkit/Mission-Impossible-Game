package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_bomb extends Entity{
	
	
	
	public OBJ_bomb(GamePanel gp) {
		super(gp);
		name = "Bomb";
		down1 = setup("/objects/bomb", gp.tileSize,gp.tileSize);
	}

}
