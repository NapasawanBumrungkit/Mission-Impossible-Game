package entity;


import java.util.Random;

import main.GamePanel;


public class NPC_MoMon extends Entity{
	
	public NPC_MoMon(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
public void getImage() {
		
		up1 = setup("/npc/oldman_up_1", gp.tileSize,gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize,gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize,gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize,gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize,gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize,gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize,gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize,gp.tileSize);
	}

    public void setDialogue() {
    	
    	dialogues[0] = "Hello, I am a wizard.";
    	dialogues[1] = "I will come to help you \n with your mission.";
    	dialogues[2] = "Your task is to find the items \nrequested by the person \nasking for help..";
    	dialogues[3] = "Then your mission will be \n accomplished.";
    	
    }
	public void setAction() {

		actionLockCounter++;

		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}
            actionLockCounter = 0;
		}
	}
	public void speak() {
		super.speak();
		
	}
}
