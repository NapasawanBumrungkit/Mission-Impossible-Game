package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreencol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreencol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	public Player player = new Player(this,keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public ArrayList<Entity> projectList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	
    public GamePanel() {
    	this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    	this.setBackground(Color.black);
    	this.setDoubleBuffered(true);
    	this.addKeyListener(keyH);
    	this.setFocusable(true);
	}
    
    public void setupGame(){
    	
    	aSetter.setOject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	gameState = titleState;
    }
    
    public void retry() {
    	player.setDefaulutPositions();
    	player.restoreLifeAndMan();
    	aSetter.setNPC();
    	aSetter.setMonster();
    }
    public void restart() {
    	player.setDefaultValues();
    	player.setDefaulutPositions();
    	player.restoreLifeAndMan();
    	player.setItems();
    	aSetter.setOject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    }
    public void startGameThread() {
    	
    	gameThread = new Thread(this);
    	gameThread.start();
    }
   
   @Override
    public void run() {
	   
	   double drawInterval = 1000000000/FPS;
	   double delta = 0;
	   long lastTime = System.nanoTime();
	   long currentTime;
	   long timer = 0;
	   int drawCout = 0;
	   
    	
	   while(gameThread != null) {
		   
		   currentTime = System.nanoTime();
		   
		   delta += (currentTime - lastTime)/ drawInterval;
		   timer += (currentTime - lastTime) ;
		   lastTime = currentTime;
		   
		   if(delta >= 1) {
			   update();
			   repaint();
			   delta--;
			   drawCout++;
		   }
		   
		   if(timer >= 1000000000) {
			 System.out.println("FPS:" + drawCout); 
			 drawCout = 0;
			 timer = 0;
		   }
	   }
	   
    }
   public void update() {
	   if(gameState == playState) {
		   player.update(); 
		   
		   // NPC
		   for(int i = 0; i < npc.length; i++) {
			   if(npc[i] != null) {
				   npc[i].update();
			   }
		   }
		   for(int i = 0; i < monster.length; i++) {
			   if(monster[i] != null) {
				   if(monster[i].alive == true && monster[i].dying == false) {
					   monster[i].update();   
				   }
				   if(monster[i].alive == false) {
					   monster[i] = null;   
				   }
			   }
		   }
		   for(int i = 0; i < projectList.size(); i++) {
			   if(projectList.get(i) != null) {
				   if(projectList.get(i).alive == true && monster[i].dying == false) {
					   projectList.get(i).update();   
				   }
				   if(projectList.get(i).alive == false) {
					   projectList.remove(i); 
				   }
			   }
		   }
	   }
	   if(gameState == pauseState) {
		   
	   }
	
   }
   
   public void paintComponent(Graphics g) {
	   
	   super.paintComponent(g);
	   
	   Graphics2D g2 = (Graphics2D)g;
	   
	   // DEBUG
	   long drawStart = 0;
	   if(keyH.showDebugText == true) {
		   drawStart = System.nanoTime();
	   }
	   // TITLE SCREEN
	   if(gameState == titleState) {
		   ui.draw(g2);
	   }
	   // OTHERS
	   else {
		// TILE
		   tileM.draw(g2);
		   
		// add entitles to the list
		   entityList.add(player);
		   for(int i = 0; i < npc.length; i++) {
			   if(npc[i]  != null) {
				   entityList.add(npc[i]);
				   
			   }
		   }
		   for(int i = 0; i < obj.length; i++) {
			   if(obj[i]  != null) {
				   entityList.add(obj[i]);
				   
			   }
		   }
		   for(int i = 0; i < monster.length; i++) {
			   if(monster[i]  != null) {
				   entityList.add(monster[i]);
				   
			   }
		   }
		   for(int i = 0; i < monster.length; i++) {
			   if(monster[i]  != null) {
				   entityList.add(monster[i]);
				   
			   }
		   }
		   for(int i = 0; i < projectList.size(); i++) {
			   if(projectList.get(i)  != null) {
				   entityList.add(projectList.get(i));
				   
			   }
		   }
		   
		   
		   //sort
		   Collections.sort(entityList ,  new Comparator<Entity>() {

			@Override
			public int compare(Entity o1, Entity o2) {
				
				int result = Integer.compare(o1.worldY, o2.worldY);
				return result;
			}
	  
		   });
		   
		   // draw entitles
		   for(int i = 0;i < entityList.size();i++) {
			   entityList.get(i).draw(g2);
		   }
		   // empty entity list
		   entityList.clear(); 
			 
		   // UI
		   ui.draw(g2);
	   }
	  
	   
	   
	   if(keyH.showDebugText == true) {
		   long drawEnd = System.nanoTime();
		   long passed = drawEnd  - drawStart;
		   
		   g2.setFont(new Font("Arial",Font.PLAIN,20));
		   g2.setColor(Color.white);
		   int x = 10;
		   int y = 400;
		   int lineHeight = 20;
		   
		   g2.drawString("WorldX" + player.worldX  , x, y); y +=lineHeight;
		   g2.drawString("WorldY" + player.worldY  , x, y); y +=lineHeight;
		   g2.drawString("Col" + (player.worldX + player.solidArea.x)/ tileSize,x, y); y +=lineHeight;
		   g2.drawString("Row" + (player.worldY + player.solidArea.y)/ tileSize,x, y); y +=lineHeight;
	       g2.drawString("Draw Time : " + passed, x, y);	 
	       
	   }
	   
       
       
	   g2.dispose();
	   
	   
   }
}
