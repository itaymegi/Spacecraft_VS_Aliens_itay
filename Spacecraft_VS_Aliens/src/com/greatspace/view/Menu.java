package com.greatspace.view;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Image;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Menu implements MouseMotionListener,MouseListener,KeyListener{
	private Rectangle SinglePlayer;
	private int gamemode;
	private Rectangle check;
	private Rectangle HowToPlay;
	private Rectangle MultiPlayer;
	private Color SinglePlayerColor;
	private Font font;
	private Image map;

	
	
	 Menu() {
		SinglePlayer= new Rectangle(235, 110, 200, 55);//X Y W H
		MultiPlayer= new Rectangle(235, 180, 200, 55);
		HowToPlay= new Rectangle(235, 250, 200, 55);
		loadMap();


	}
	 public void loadMap(){
			map = new ImageIcon(getClass().getResource("/com/greatspace/sprites/backimg.jpg")).getImage();
		}
	 public void render(Graphics g){
			
			Graphics2D g2d= (Graphics2D)g;
			if(Game.state==Game.STATE.MENU){
				g2d.drawImage(map, 0, 0, null);
				 font = new Font("Century Gothic",Font.BOLD,30);
				g2d.setFont(font);
				g2d.draw(SinglePlayer);
				g2d.drawString("SinglePlayer", SinglePlayer.x, SinglePlayer.y+47);
				g2d.draw(MultiPlayer);
				g2d.drawString("MultiPlayer", MultiPlayer.x, MultiPlayer.y+47);
				g2d.draw(HowToPlay);
				g2d.drawString("HowToPlay", HowToPlay.x, HowToPlay.y+47);
				
				
			}

		
		}
		public boolean isInsideRect(Rectangle button,int x,int y){
			return ( x >= button.getMinX() && x <= button.getMaxX() ) && ( y >= button.getMinY() && y <= button.getMaxY());
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int mouseX=e.getX();
			int mouseY=e.getY();
			if(isInsideRect(SinglePlayer,mouseX,mouseY)&&Game.state==Game.STATE.MENU){
				gamemode =1;
				
				Game.state=Game.STATE.GAME;
			}
			if(isInsideRect(MultiPlayer,mouseX,mouseY)&&Game.state==Game.STATE.MENU){
				
				gamemode = 2;
				
				Game.state=Game.STATE.GAME;
				
			}
			if(isInsideRect(HowToPlay,mouseX,mouseY)&&Game.state==Game.STATE.MENU){
				check= new Rectangle(235, 180, 200, 55);
				Game.state =Game.STATE.GAME;				
			}
			
		
			
		}
		

		@Override
		public void mouseMoved(MouseEvent e) {
		
			
			
		}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode=e.getKeyCode();
		if(keycode == KeyEvent.VK_ESCAPE)
		    {
			 	System.exit(1);
		    }

	}

}
