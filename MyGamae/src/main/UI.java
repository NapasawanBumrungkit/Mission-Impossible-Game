package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;


public class UI {
 
	GamePanel gp;
	Graphics2D g2;
	Font  maruMonica;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	//public String message = "";
	//int messageCount = 0;
	ArrayList<String> message = new ArrayList<String>();
	ArrayList<Integer> massageCounter = new ArrayList<Integer>();
	
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;

	
 	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void addMessage(String text) {
	//	message = text;
	//	messageOn = true;
		
		message.add(text);
		massageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {	
			drawTitleScreen();
		}
		
		// play state
		if(gp.gameState == gp.playState) {	
			drawPlayerLife();
			drawMessage();
		}
		// pause state
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// character state
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		// game over state
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
			
		}
		
	}
	public void drawPlayerLife() {
		
		//gp.player.life = 5;
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// DRAW MAX LIFE
		while (i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y,null);
			i++;
			x += gp.tileSize;
		}
		
		// RESET
		 x = gp.tileSize/2;
		 y = gp.tileSize/2;
		 i = 0;
		 
		 // DRAW CURRENT LIFE
		 while(i < gp.player.life) {
			 g2.drawImage(heart_half, x, y, null);
			 i++;
			 if(i < gp.player.life) {
				 g2.drawImage(heart_full, x, y, null);
			 }
			 i++;
			 x += gp.tileSize;
		 }
		
		
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for(int i = 0; i  < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = massageCounter.get(i) + 1;
				massageCounter.set(i, counter);
				messageY += 50;
				
				if(massageCounter.get(i) > 180) {
					message.remove(i);
					massageCounter.remove(i);
				}
				
				
			}
		}
				
	}
	public void drawTitleScreen() {
		
		// title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,65F));
		String text =" Mission: Impossible Game";
		int x = getXforCentersdText(text);
		int y = gp.tileSize *3;
		
		// shadow
		g2.setColor(Color.gray);
		g2.drawString(text, x, y);
		
		// main color
		g2.setColor(Color.white);
		g2.drawString(text, x+3, y+3);
		
		// image
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		
		text = "NEW GAME";
		x = getXforCentersdText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "QUIT";
		x = getXforCentersdText(text);
		y += gp.tileSize ;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCentersdText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		// window
		int x = gp.tileSize *2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth -(gp.tileSize * 4);
		int height = gp.tileSize*4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawCharacterScreen() {
	 	
		// 	CREATE a frame
		final int frameX = gp.tileSize *2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// NAME
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 15 ;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		
		// Values
		int tailX = (frameX + frameWidth) - 30;
		
		// reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.life +"_"+ gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
	

		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;		
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14 ,null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);

		
	}
    
	public void drawInventory() {
		int frameX = gp.tileSize *9 ;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize *5;
		 drawSubWindow(frameX, frameY, frameWidth, frameHeight);	
		 // slot
		 final int slotXstaet = frameX + 20;
		 final int slotYstaet = frameY + 20;
		 int slotX = slotXstaet;
		 int slotY = slotYstaet;
		 int slotSize = gp.tileSize +3;
		 
		 // Draw player's ITEms
		 for(int i = 0; i < gp.player.inventory.size(); i++) {
			 
			 if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					 gp.player.inventory.get(i) == gp.player.currentShield) {
				 g2.setColor(new Color(240, 190, 90));
				 g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			 }
			 
			 g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null) ;
			 
			 slotX += gp.tileSize;
			 
			 if(i == 4 || i == 9  || i == 14) {
				 slotX = slotXstaet;
				 slotY += gp.tileSize;
			 }
		 }
				 
		 // cursor
		 int cursorX = slotXstaet + (gp.tileSize * slotCol);
		 int cursorY = slotYstaet + (gp.tileSize * slotRow);
         int cursorWidth = gp.tileSize;
         int cursorHeight = gp.tileSize;
         
         // draw cursor
         
         g2.setColor(Color.white);
         g2.setStroke(new BasicStroke(3));
         g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10 , 10);
         
         // description frame
         int dFrameX = frameX;
         int dFrameY = frameY + frameHeight;
         int dFrameWidth = frameWidth;
         int dFrameHeight = gp.tileSize * 3;
       
         
         
      // Draw description text
         int textX = dFrameX + 20;
         int textY = dFrameY+ gp.tileSize;
         g2.setFont(g2.getFont().deriveFont(25F));
         
         int itemIndex = getTtemIndexOnSlot();
         
         if(itemIndex < gp.player.inventory.size()) {
        	 
        	  drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
        	  
        	 for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
        		 g2.drawString(line, textX, textY);
        		 textY += 32;
        	 }
        	
         }
         
         
	}
	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150 ));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		
		text ="Game Over";
		g2.setColor(Color.black);
		x = getXforCentersdText(text);
		y = gp.tileSize *4;
		g2.drawString(text, x , y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y -4);
		
		
		g2.setFont(g2.getFont().deriveFont(50f));
		
		text ="Retry";
		x = getXforCentersdText(text);
		y = gp.tileSize *6;
		
		g2.drawString(text, x , y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}

		text ="Quit";
		x = getXforCentersdText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}


		
	}
	public int getTtemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow *5);
		return itemIndex;
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
	    c = new Color(255, 255, 255);
	    g2.setColor(c);
	    g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect( x + 5, y+5, width-10, height-10, 25, 25);
	}
	
	public int getXforCentersdText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
	} 
} 
