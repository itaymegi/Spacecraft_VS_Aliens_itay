package com.greatspace.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.greatspace.model.Bullet.mode;

public class Enemy extends GameObject {
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public Bullet getMissel() {
		return missel;
	}

	public void setMissel(Bullet missel) {
		this.missel = missel;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(List<Bullet> bullets) {
		this.bullets = bullets;
	}

	public static int getSpeed() {
		return speed;
	}

	private boolean up = false;
    private int x;
    private int y;
    private int shots_to_death;
    private int current_shots;
    private String name;
    private Bullet missel;
    private List<Bullet> bullets;
    public int getCurrent_shots() {
		return current_shots;
	}

	public void setCurrent_shots(int current_shots) {
		this.current_shots = current_shots;
	}

	private static final int speed = 2;

    public Enemy()
    {
    	bullets = new ArrayList<Bullet>();
        missel = new Bullet(mode.Enemy);
        }

    public static int GeneratePosX(int level, int index)
    {
        return 1280+ index*5+level%2+5;
    }

    public static int GeneratePosX(int nb)
    {

        int aax = 1290 + (int) (Math.random() * 16 * nb);
        return aax;
    }

    public static int GeneratePosY(int level, int index)
    {
        int aay = 10 + level*10+50+index*21;

        return aay;
    }

    public void move()
    {
        move(100);
    }

    public void enemy_fire() {
        Bullet mis = (Bullet) missel.clone();
        //the width and height of the bullet image+CORDS OF PLAYER
        mis.setX((x - getWidth()));
        mis.setY(y + getHeight() / 2);

        this.bullets.add(mis);
    }
    public  void specialMove()
    {	
    	 int enemy = 10;
    	 
        if (this.x < 0) {
        	//get back to beginning
            this.x = GeneratePosX(enemy,enemy);
            this.y = GeneratePosY(enemy,enemy);
        } 
        if (this.y <= 0) {
           up = true;     
        } 
        	//moves to minus x
            this.x -= speed;
            if (!up)
            	this.y -= speed;
            else 
            {
            	if (this.y+156 <=690) {
                	this.y += speed;

            	}
            	else {
            		up = false;
            	}
            }
       }
    public  void specialMoveTOlitleEnemys()
    {	
    	 int enemy = 10;
    	 
        if (this.x < 0) {
        	//get back to beginning
            this.x = GeneratePosX(enemy,enemy);
            this.y = GeneratePosY(enemy,enemy);
        } 
        if (this.y <= 0) {
           up = true;     
        } 
        	//moves to minus x
            this.x -= speed;
            if (!up)
            	this.y -= speed;
            else 
            {
            	if (this.y+156 <=760) {
                	this.y += speed;

            	}
            	else {
            		up = false;
            	}
            }
       }
    public void moveBoss(int enemy)
    {

        if (this.x < 0) {
        	//get back to beginning
            this.x = GeneratePosX(250);
            this.y =150;
        } else {
        	//moves to minus x
            this.x -= speed+1;
        }
       }
    
    

    public void move(int enemy)
    {
        if (this.x < 0) {
        	//get back to beginning
            this.x = GeneratePosX(enemy,enemy);
            this.y = GeneratePosY(enemy,enemy);
        } else {
        	//moves to minus x
            this.x -= speed;
        }
    }
    public boolean isdead()
    {
    	return shots_to_death == current_shots;
    	
    }
    public int getShots_to_death() {
		return shots_to_death;
		
	}

	public void setShots_to_death(int shots_to_death) {
		this.shots_to_death = shots_to_death;
	}

	public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

	public void  setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;

	}

}
