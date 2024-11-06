package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Fireball;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.OBJ_key;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int hasKey = 0;
	int standCounter = 0;
	public boolean attackCancled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxinventorySize = 20;
	GamePanel gp;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		
		
	
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		attackArea.width = 32;
		attackArea.height = 32;
		// Attack area
		//attackArea.width = 36;
		//attackArea.height = 36;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		
	}

	public void setDefaultValues() {

		worldX = gp.tileSize * 25;
		worldY = gp.tileSize * 25;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword(gp);
		currentShield = new OBJ_Shield(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
		
	}
	
	public void setDefaulutPositions() {
		worldX = gp.tileSize *25;
		worldY = gp.tileSize *25;
		direction = "down";
	}
	
   public void restoreLifeAndMan() {
	   life= maxLife;
		invincible = false;
		
	}

	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_key(gp));
		inventory.add(new OBJ_key(gp));
		
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/up_1", gp.tileSize,gp.tileSize);
		up2 = setup("/player/up_2", gp.tileSize,gp.tileSize);
		down1 = setup("/player/down1", gp.tileSize,gp.tileSize);
		down2 = setup("/player/down2", gp.tileSize,gp.tileSize);
		left1 = setup("/player/Left_1", gp.tileSize,gp.tileSize);
		left2 = setup("/player/Left_2", gp.tileSize,gp.tileSize);
		right1 = setup("/player/Right_1", gp.tileSize,gp.tileSize);
		right2 = setup("/player/Right_2", gp.tileSize,gp.tileSize);
	}
	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2,gp.tileSize);
		}
		if(currentWeapon.type == type_Axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2,gp.tileSize);
		}
		
	}

	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		
		else if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
			if (keyH.upPressed == true) {
				direction = "up";
				
			} 
			else if (keyH.downPressed == true) {
				direction = "down";
			
			} 
			else if (keyH.leftPressed == true) {
				direction = "left";
				
			} 
			else if (keyH.rightPressed == true) {
				direction = "right";
				
			}
			
			// check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkobject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK MONSTER COLLISION
			int monsterTndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterTndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			
			if(collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case "up":worldY -= speed;break;					
				case "down":worldY += speed;break;
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
				}
			}
			
			
			if( keyH.enterPressed == true && attackCancled == false) {
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCancled = false;
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
		}
		else {
			standCounter++;
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter ==30) {
			
			projectile.set(worldX, worldY, direction, true, this);
			
			gp.projectList.add(projectile);
			
			shotAvailableCounter = 0;
		}
		
		// This needs to be outside of key if statement!
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
		}
	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25 ) {
			spriteNum = 2;
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int soliAreaWidth = solidArea.width;
			int soliAreaHeight = solidArea.height;
			
			// Adjust player's worldX/Y for the attackArea
			switch(direction) {
			case"up": worldY -= attackArea.height; break;
			case"down": worldY += attackArea.height; break;
			case"left": worldX -= attackArea.width; break;
			case"ringht": worldX += attackArea.width; break;
			}
			
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = soliAreaWidth;
			solidArea.height =  soliAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
		
			// pickup only items
			if(gp.obj[i].type == type_pickupOnly) {
				
				//gp.obj[i].user(this);
				//gp.obj[i] = null;
			}
			// inventory items
			else {
				String text;
				if(inventory.size() != maxinventorySize) {
					
					inventory.add(gp.obj[i]);
					text = "Got a" +gp.obj[i].name + "!";
				}
				else {
					
					text = "You cannot carry any more!";
				}
				gp.ui.addMessage(text);
				gp.obj[i] = null;
				
				}
			}
			
			
			
		}
	
	public void interactNPC(int i) {

		if (gp.keyH.enterPressed == true) {

			if (i != 999) {
				
				attackCancled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			} 
		}

	}
	public void contactMonster(int i) {
		
		if(i != 999) {
			
			if(invincible == false && gp.monster[i].dying == false) {
				
				int damage = gp.monster[i].attack - defense;	
				if(damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
			
		}
	}
	public void damageMonster(int i, int attack) {
		if(i != 999) {
				
				if(gp.monster[i].invincible == false) {
					
					int damage = attack - gp.monster[i].defense;	
					if(damage < 0) {
						damage = 0;
					}
					gp.monster[i].life -= damage;
					gp.ui.addMessage(damage + "damage!");
					gp.monster[i].invincible = true;
					gp.monster[i].damageReaction();
					
					if(gp.monster[i].life <= 0) {
						gp.monster[i].dying = true;
						gp.ui.addMessage("Killed the" + gp.monster[i].name + "!");
						gp.ui.addMessage("Exp +" + gp.monster[i].exp);
						exp += gp.monster[i].exp;
						checkLevelUp();
					}
				}
			}
		}
	public void checkLevelUp() {
		 
		if(exp >= nextLevelExp) {
			
			level++;
			nextLevelExp = nextLevelExp *2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are level " + level + "now!\n"
					+"You feel stronger!";
		}
	}
	public  void selectItem() {
		
		int itemIndex = gp.ui.getTtemIndexOnSlot();
		
		if( itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_sword || selectedItem.type == type_Axe) {
				
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				attack = getDefense();
			}
		}
	}

	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}	
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}	
				if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2 ;}
			}
			break;
		}
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
		// DEBUG
		//g2.setFont(new Font("Arial", Font.PLAIN,26));
		//g2.setColor(Color.white);
		//g2.drawString("Invincible:"+invincibleCounter, 10, 400);
		

	}

}
