package com.greatspace.model;

import com.greatspace.proxy.ProxyImage;
import java.awt.Rectangle;


public class Bullet extends GameObject {

    private int x;
    private int y;
    private static ProxyImage imagemProxy;
    private static final int maxWidth = 1300;
    //speed
    public static enum mode { Player, Enemy};
    private int SPEED = 5;

    public Bullet(mode mode)
    {
        if (imagemProxy == null) {
            imagemProxy = new ProxyImage("/com/greatspace/sprites/bullet.png");
        }
        
        if (mode == mode.Enemy)
        	this.SPEED *= -1;

        this.setImage(imagemProxy.loadImage().getImage());
        this.setHeight(getImage().getHeight(null));
        this.setWidth(getImage().getWidth(null));

        this.setVisibility(true);
    }

    public void check_if_the_bullet_cross_the_maxWidth()
    {

        this.x += SPEED;
        if (this.x > maxWidth) {
            setVisibility(false);
        }

    }
    public void check_if_the_bullet_cross_the_maxWidth_bollean(boolean f)
    {
    	if (f) {
    		   this.x += SPEED;
    	        if (this.x > maxWidth) {
    	            setVisibility(false);
    	}
     
        }
    	else {
    		   this.x += SPEED;
   	        if (this.x < 0) {
   	            setVisibility(false);
    	}

    }
    }


    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

}
