package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	 // debug
	boolean showDebugText = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	 

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		//title state
		if(gp.gameState == gp.titleState) {
			titleState(code);

         }
		// play state
		else if(gp.gameState == gp.playState) {
            
			playState(code);
        }
	    // pause state
        else if(gp.gameState == gp.pauseState) {
        	
        	pauseState(code);
        }
        
     
        // dialogue state
        else if(gp.gameState == gp.dialogueState) {
        	
        	dialogueState(code);

        }
		
		// character state
        else if(gp.gameState == gp.characterState) {
        
        	characterState(code);
        }
		// game over state
        else if(gp.gameState == gp.gameOverState) {
        
        	gameOverState(code);
        }
		
	}
	
	public void titleState(int code) {
		
		if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
         	   gp.ui.commandNum = 1;
            }
 		}
 		if (code == KeyEvent.VK_S) {
 			gp.ui.commandNum++;
             if(gp.ui.commandNum > 1) {
          	   gp.ui.commandNum = 0;
             }
         }
 	
 		if (code == KeyEvent.VK_ENTER) {
             if(gp.ui.commandNum == 0) {
          	  gp.gameState = gp.playState;
		        }
             if(gp.ui.commandNum == 1) {
             	
             }
             if(gp.ui.commandNum == 1) {
             	System.exit(0);
             }
      }
		
		
	}
    public void playState(int code) {
    	
    	if (code == KeyEvent.VK_W) {
            upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
           downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
            leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
           rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
			}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
			}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = true;
			}
		
		 // debug
		if (code == KeyEvent.VK_T) {
	           if(showDebugText == false) {
	        	   showDebugText =  true;
	           }
	           else if (showDebugText == true) {
	        	   showDebugText = false;
	           }
			}
		if (code == KeyEvent.VK_R) {
			gp.tileM.loadMap("/maps/map_world.txt");
		}
		
	}
    public void pauseState(int code) {
    	if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			}
		
	}
    public void dialogueState(int code) {
    	if(code == KeyEvent.VK_ENTER) {
    		gp.gameState = gp.playState;
    	}
		
	}
    public void characterState(int code) {
    	
    	if(code == KeyEvent.VK_C) {
    		gp.gameState = gp.playState;
    	}
    	if(code == KeyEvent.VK_W) {
    		if(gp.ui.slotRow != 0) {
    			gp.ui.slotRow--;
    		}
    	}
        if(code == KeyEvent.VK_A) {
        	if(gp.ui.slotCol != 0) {
        		gp.ui.slotCol--;
        	}
    	}
        if(code == KeyEvent.VK_S) {
        	if(gp.ui.slotRow != 3) {
            	gp.ui.slotRow++;
        	}
        }
        if(code == KeyEvent.VK_D) {
        	if(gp.ui.slotCol != 4) {
            	gp.ui.slotCol++;
        	}
        }
        if(code == KeyEvent.VK_ENTER) {
        	gp.player.selectItem();
        }
	}
    
    public void gameOverState(int code) {
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		if(gp.ui.commandNum < 0) {
    			gp.ui.commandNum = 1;
        	
        }
	  }
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum++;
    		if(gp.ui.commandNum > 1) {
    			gp.ui.commandNum = 0;
        	
        }
	  }
    	if(code == KeyEvent.VK_ENTER) {
    		if(gp.ui.commandNum == 0) {
    			gp.gameState = gp.playState;
    			gp.retry();
        	
        }
    		else if(gp.ui.commandNum == 1) {
    			gp.gameState = gp.titleState;
    			gp.restart();
    		}
    	}
    }

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
            upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
           downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
            leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
           rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = false;
			}
	}

}
