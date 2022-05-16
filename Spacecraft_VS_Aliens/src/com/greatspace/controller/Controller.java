package com.greatspace.controller;

import com.greatspace.interfaces.IStrategy;
import com.greatspace.model.Player;
import com.greatspace.view.Game;

import Network.Client;

import java.awt.event.KeyEvent;

import static com.greatspace.controller.Touch.*;


public enum Controller implements IStrategy {

    PLAYER_1 {
        @Override
        public void keyPressed(Player player, KeyEvent key)
        {
            int codigo = key.getKeyCode();
           
                switch (codigo) {
                    case PLAYER_ONE_FIRE:
                        player.fire();
                        Client.SendstringToServer(Game.index+" =Press=FIRE");
                        break;
                    case PLAYER_ONE_MOVE_UP:
                        player.setDy(-1);
                        Client.SendstringToServer(Game.index+" =Press=UP");

                        break;
                    case PLAYER_ONE_MOVE_DOWN:
                        player.setDy(1);
                        Client.SendstringToServer(Game.index+"=Press=DOWN");

                        break;
                    case PLAYER_ONE_MOVE_LEFT:
                        player.setDx(-1);
                        Client.SendstringToServer(Game.index+" =Press=LEFT");

                        break;
                    case PLAYER_ONE_MOVE_RIGHT:
                        player.setDx(1);
                        Client.SendstringToServer(Game.index+" =Press=RIGHT");

                        break;
                }
            
          
        }

        @Override
        public void keyReleased(Player player, KeyEvent key)
        {
         

            int codigo = key.getKeyCode();
            
                switch (codigo) {
                    case PLAYER_ONE_MOVE_UP:

                        player.setDy(0);
                        Client.SendstringToServer(Game.index+"=Release=UP");

                        break;
                    case PLAYER_ONE_MOVE_DOWN:
                        player.setDy(0);
                        Client.SendstringToServer(Game.index+"=Release=DOWN");

                        break;
                    case PLAYER_ONE_MOVE_LEFT:
                        player.setDx(0);
                        Client.SendstringToServer(Game.index+"=Release=LEFT");

                        break;
                    case PLAYER_ONE_MOVE_RIGHT:
                        player.setDx(0);
                        Client.SendstringToServer(Game.index+"=Release=RIGHT");

                        break;
                }
            
           
        }

		@Override
		public void keyPressed(Player player, int key) {
			 
	                switch (key) {
	                    case PLAYER_ONE_FIRE:
	                        player.fire();
	                        break;
	                    case PLAYER_ONE_MOVE_UP:
	                        player.setDy(-1);

	                        break;
	                    case PLAYER_ONE_MOVE_DOWN:
	                        player.setDy(1);

	                        break;
	                    case PLAYER_ONE_MOVE_LEFT:
	                        player.setDx(-1);

	                        break;
	                    case PLAYER_ONE_MOVE_RIGHT:
	                        player.setDx(1);

	                        break;
	                }
	            
		}

		@Override
		public void keyReleased(Player player, int key) {

            
            
                switch (key) {
                    case PLAYER_ONE_MOVE_UP:

                        player.setDy(0);

                        break;
                    case PLAYER_ONE_MOVE_DOWN:
                        player.setDy(0);

                        break;
                    case PLAYER_ONE_MOVE_LEFT:
                        player.setDx(0);

                        break;
                    case PLAYER_ONE_MOVE_RIGHT:
                        player.setDx(0);

                        break;
                }
     
			
			
		}
    }
}
