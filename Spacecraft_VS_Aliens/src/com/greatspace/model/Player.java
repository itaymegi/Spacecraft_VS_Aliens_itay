package com.greatspace.model;

import com.greatspace.controller.Controller;
import com.greatspace.model.Bullet.mode;
import com.greatspace.proxy.ProxyImage;
import com.greatspace.view.Game;

import Network.Client;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Player extends GameObject implements Runnable {

    private int x, y;
    private int dx, dy;
    public int index;
    private boolean isdead;
    private static ProxyImage imagemProxy;
    private Bullet missel;

    private List<Bullet> bullets;
    private Controller controle;

    

    public Player() {
        if (imagemProxy == null)
            imagemProxy = new ProxyImage("/com/greatspace/sprites/ship.gif");

        this.setImage(imagemProxy.loadImage().getImage());

        this.setHeight(getImage().getHeight(null));
        this.setWidth(getImage().getWidth(null));

        bullets = new ArrayList<Bullet>();
        missel = new Bullet(mode.Player);
    }

    public void the_speed_of_the_player() {
        x += dx*3; // 1 e 462
        y += dy*3; // 1 e 340

        colisaoJanela();
    }

    public void colisaoJanela() {

        if (this.x < 1) {
            x = 1;
        }

        if (this.x > 1240) {
            x = 1240;
        }

        if (this.y < 1) {
            y = 1;
        }

        if (this.y > 620) {
            y = 620;
        }
    }

    public List<Bullet> getBullet() {
        return bullets;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDx(int d) {
        this.dx = d;
    }

    public void setDy(int d) {
        this.dy = d;
    }

    public int getY() {
        return y;
    }

    public void setY(int num) {

        this.y = num;
    }

    public boolean isDead() {
        return isdead;
    }

    public void setIsDead(boolean isdead) {//is dead or not
        this.isdead = isdead;
    }

    public void fire() {
        Bullet mis = (Bullet) missel.clone();
        //the width and height of the bullet image+CORDS OF PLAYER
        mis.setX(x + getWidth());
        mis.setY(y + getHeight() / 2);

        this.bullets.add(mis);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }
    
    
  
  
    
    public Controller getControle() {
        return controle;
    }

    public void setControle(Controller controle) {
        this.controle = controle;
    }
    public void findCollision()//interct between the enemy and the players
    {

        Rectangle player1 = this.getBounds();//create player1
        Rectangle enemy; //enemy
        Rectangle bullet; //bullet
        if(Game.enemies!=null) {
        for (int i = 0; i < Game.enemies.size(); i++) {

            Enemy TempEnemy = Game.enemies.get(i);
            enemy = TempEnemy.getBounds();//create enemy
            
          //the enemy interct the  player1 and kiil him	
            if ( player1.intersects(enemy)) {
                this.setVisibility(false);
                this.setIsDead(true);
                Client.SendstringToServer("DEAD");
            	
        
            }
        }

        List<Bullet> bulletsOne = this.getBullet();//create a list of bullets for player1
        
// for that run on all the bullets of player1
        for (int i = 0; i < bulletsOne.size(); i++) {

            Bullet TempBullet = bulletsOne.get(i);//bullet from the first bullet list
            bullet = TempBullet.getBounds();// the creation of the bullet
         // for that run on all the enemys
            for (int j = 0; j < Game.enemies.size(); j++) {

                Enemy TempEnemy = Game.enemies.get(j);
                
                enemy = TempEnemy.getBounds();
                
                if (bullet.intersects(enemy)) {// interct between bullit and enemy
                	{
                	TempEnemy.setCurrent_shots(TempEnemy.getCurrent_shots()+1);//update the shots the enemy gave
                	
                	if(TempEnemy.isdead()) {
                        TempEnemy.setVisibility(false);
                	}
                    TempBullet.setVisibility(false);
                	}
                }

            }
        }
        }
        
     }
        

	@Override
	public void run() {
		while(true) {
			try {
				Game.sem.acquire();
				findCollision();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Game.sem.release();
		}
	}

}
