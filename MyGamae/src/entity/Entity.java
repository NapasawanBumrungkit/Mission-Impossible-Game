package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
	attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean coliision = false;
	String dialogues[] = new String[20]; 
	
	// state
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1; 
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	
	// counter
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	public int shotAvailableCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	
	
	// CHARCTER STATUS
	
	public String name;
	public int speed;
    public int	maxLife;
    public int life;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    
    // ITEM ATTIBUTES
    public  int value = 0;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    
    // type
    public int type; // 0 = player. 1 = npc,  2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_shield = 4;
    public final int type_Axe = 5;
    public final int type_pickupOnly = 6;
 
    
    
    
    public Entity(GamePanel gp) {
    	this.gp = gp;
    }
    
    public void setAction() {}
    public void damageReaction() {}
    public void speak() {
    	if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case"up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	
    }
    public void update() {
    	
    	setAction();
    	
    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkobject(this, false);
    	gp.cChecker.checkEntity(this, gp.npc);
    	gp.cChecker.checkEntity(this, gp.monster);
    	boolean contactPlayer =  gp.cChecker.checkPlayer(this);
    	
    	
    	if(this.type == type_monster && contactPlayer == true ) {
    		if(gp.player.invincible == false) {
    			// we can give damage
    			
    			int damage = attack -gp.player.defense;	
				if(damage < 0) {
					damage = 0;
				}
				gp.player.life -= damage;
    			
    			gp.player.invincible = true;
    		}
    	}
    	
		if(collisionOn == false) {
			
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			 
			  
			  }
			}
		
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
	
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}	
    }
    public void draw(Graphics2D g2) {
    	
    	BufferedImage image = null;
    	int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				break;
			case "down":
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				break;
			case "left":
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				break;
			case "right":
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				break;
			}
			
			// Monster HP bar
			if(type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize / maxLife;
				double hpBarValue = oneScale * life;
				
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
				
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changAlpha(g2, 0.4F);
			}
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY,  null);
			
			changAlpha(g2, 1F);
		}
    }
    public void dyingAnimation(Graphics2D g2) {
    	dyingCounter ++;
    	
    	int i = 10;
    	
    	if(dyingCounter <= i) {changAlpha(g2, 0f);}
    	if(dyingCounter > i && dyingCounter <= i * 2) {changAlpha(g2, 1f);}
    	if(dyingCounter > i * 2 && dyingCounter <= i * 3) {changAlpha(g2, 0f);}
    	if(dyingCounter > i * 3 && dyingCounter <= i * 4) {changAlpha(g2, 1f);}
    	if(dyingCounter > i * 4 && dyingCounter <= i * 5) {changAlpha(g2, 0f);}
    	if(dyingCounter > i * 5 && dyingCounter <= i * 6) {changAlpha(g2, 1f);}
    	if(dyingCounter > i * 6 && dyingCounter <= i * 7) {changAlpha(g2, 0f);}   	
    	if(dyingCounter > i * 7 && dyingCounter <= i * 8) {changAlpha(g2, 1f);}    
    	if(dyingCounter > i * 8) {dying = false;alive = false;	
    	}
    }
    public void changAlpha(Graphics2D g2, float alphaValue) {
    	
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scImage(image, width, height);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
		
	}

}

