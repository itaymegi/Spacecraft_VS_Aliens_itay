package com.greatspace.view;

import com.greatspace.controller.Controller;
import com.greatspace.model.Bullet;
import com.greatspace.model.Enemy;
import com.greatspace.model.Player;
import com.greatspace.proxy.ProxyImage;

import Network.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;//is an abstract (adapter) class for receiving keyboard events. All methods of this class are empty. This class is convenience class for creating listener objects.
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;


public class Game extends JPanel implements ActionListener, Runnable {

    private int gamemode;
    private Image background;
    private Image menuimg;
    private final Timer timer;
    public static Semaphore sem= new Semaphore(1);
    private int score = 0;
	public int Time=0; 
    private final Player mainPlayer;
    private final Player player2;

    private Menu menu;
	public enum STATE{
		MENU,
		GAME,
		MULTIPLAYER,
		SCREENPAUSE,
		BACKGAME
	};
    
    private boolean passedMainMenu = false;
    
    public static STATE state=STATE.MENU;	
    private boolean playing;
    private boolean begin;
    private boolean lose =false;
    public static int index;
    private boolean isWon;

    public static List<Enemy> enemies;

    public Game(int index)
    {
    	this.index = index;
    	//menu = new Menu();
        this.mainPlayer = new Player();
        this.player2 = new Player();
        new Thread(this.mainPlayer).start();
        Timer timer;
        
        
	
        
        setFocusable(true);//it can navigated with the keyboard(if its was false  it can no longer be navigated with the keyboard )
        setDoubleBuffered(true);
        addKeyListener(new KeyboardAdapter());
    	//this.addKeyListener(menu);
    	
		//this.addMouseListener(menu);
		//this.addMouseMotionListener(menu);
        ImageIcon imgicon = new ImageIcon(getClass().getResource("/com/greatspace/sprites/backimg.jpg"));
        background = imgicon.getImage();

		this.timer = null;
		if(index==0) {
			mainPlayer.setX(100);
			mainPlayer.setY(100);
			this.player2.setX(100);
			this.player2.setY(200);
		}
		else {
			mainPlayer.setX(100);
			mainPlayer.setY(200);
			this.player2.setX(100);
			this.player2.setY(100);
		}
		
		mainPlayer.setControle(Controller.PLAYER_1);
		player2.setControle(Controller.PLAYER_1);


        playing = false;
        isWon = false;
        begin = true;

        startGame();
        initEnemy();
// the speed of the whole game 
        timer = new Timer(10, this);
        timer.start();
        
    }

    public JMenuBar criarMenu()
    {
        // Create a new MenuBar
        JMenuBar menub = new JMenuBar();
        // Create a new Menu
        JMenu game = new JMenu("Game");
        // Create a new Menu Item of Jogo menu
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        // Add fechar menu item to the jogo menu
        game.add(close);

        JMenu help = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");
        about.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, "<html><strong>Great Itay </strong><br> "
                    + "Developed by <strong>Itay Megidish</strong>!<br><br>"
                    + "<strong>The Story Of The Game</strong><br><br>"
                    + "- Aliens have decided to attack the planet and destroy it <br>"
                    + "- the last hope of the planet is the spacecraft <br>"
                    + "- Will they be able to save the planet?<br>"
                    + "<br></html>", "About", 1);
        });
       
        JMenuItem htp = new JMenuItem("How to Play");
        htp.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, "<html>"
                    + "<strong>Player 1</strong><br>"
                    + "Fire - <strong>Space</strong><br>"
                    + "Up - <strong>W</strong><br>"
                    + "Down - <strong>S</strong><br>"
                    + "Left - <strong>A</strong><br>"
                    + "Right - <strong>D</strong><br><br>"
                    + "<strong>Player 2</strong><br>"
                    + "Fire - <strong>Delete</strong><br>"
                    + "Up - <strong>UP ARROW</strong><br>"
                    + "Down - <strong>DOWN ARROW</strong><br>"
                    + "Left - <strong>LEFT ARROW</strong><br>"
                    + "Right - <strong>RIGHT ARROW</strong><br>"
                    + "</html>", "How to play", JOptionPane.QUESTION_MESSAGE);
        });

        help.add(htp);
        help.add(about);
   

        menub.add(game);
        menub.add(help);

        return menub;
    }
    public void setScore(int s) {
    	this.score = s;
    	initEnemy();
    }
    //this function sets all the enemys photos and x,y
    private void initEnemy()
    {
    	
    	try {
			sem.acquire();
	        enemies = new ArrayList<>();
	        Enemy enemy = new Enemy();

	        ProxyImage enemyOneImage = new ProxyImage("/com/greatspace/sprites/ido_front.png");//create the enemys image
	        ProxyImage enemyTwoImage = new ProxyImage("/com/greatspace/sprites/pini2.png");
	        ProxyImage enemyThreeImage = new ProxyImage("/com/greatspace/sprites/enemy_1.gif");
	        ProxyImage enemyFourImage = new ProxyImage("/com/greatspace/sprites/enemy_2.gif");
	        if(score==0)
	        	level1(enemy, enemyOneImage, enemyTwoImage, enemyThreeImage, enemyFourImage );
	        else if(score==1)
	        	level2(enemy, enemyOneImage, enemyTwoImage, enemyThreeImage, enemyFourImage);
	        else if(score==2)
	        	level3(enemy, enemyOneImage, enemyTwoImage, enemyThreeImage);
	        else if(score==3)
	         	level4(enemy, enemyOneImage, enemyTwoImage, enemyThreeImage, enemyFourImage);
	        Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	sem.release();
    }
    
    private void level4(Enemy enemy, ProxyImage enemyOneImage, ProxyImage enemyTwoImage, ProxyImage enemyThreeImage, ProxyImage enemyFourImage) {
    	for (int i = 0; i < 20; i++) {
        	//sets the enemy in the game
            Enemy ini = (Enemy) enemy.clone();
            ini.setX(Enemy.GeneratePosX(4,i));
            ini.setY(Enemy.GeneratePosY(4,i));

            if (i % 2 == 0) {
            	ini.setShots_to_death(4);
            	ini.setName("Pini");
                ini.setImage(enemyTwoImage.loadImage().getImage());
            } else if (i % 6 ==0) {
            	ini.setShots_to_death(2);
            	ini.setName("orange_alian");
                ini.setImage(enemyFourImage.loadImage().getImage());
            } else if (i % 4 == 0)  {
            	ini.setShots_to_death(2);
            	ini.setName("Edo");
                ini.setImage(enemyOneImage.loadImage().getImage());
             
            }else  {
            	ini.setShots_to_death(1);
            	ini.setName("Bat");
                ini.setImage(enemyThreeImage.loadImage().getImage());
            }
            ini.setHeight(ini.getImage().getHeight(null));
            ini.setWidth(ini.getImage().getWidth(null));
            ini.setVisibility(true);
            enemies.add(ini);
       
		}
		
		
	}
    
    
    private void level3(Enemy enemy, ProxyImage enemyOneImage, ProxyImage enemyTwoImage, ProxyImage enemyThreeImage) {
		for (int i = 0; i < 18; i++) {
        	//sets the enemy in the game
            Enemy ini = (Enemy) enemy.clone();
            ini.setX(Enemy.GeneratePosX(3,i));
            ini.setY(Enemy.GeneratePosY(3,i));

            if (i % 4 == 0) {
            	ini.setShots_to_death(4);
            	ini.setName("Pini");
                ini.setImage(enemyTwoImage.loadImage().getImage());
            } else if (i % 2 == 0)  {
            	ini.setShots_to_death(2);
            	ini.setName("Edo");
                ini.setImage(enemyOneImage.loadImage().getImage());
             
            }else  {
            	ini.setShots_to_death(1);
            	ini.setName("Bat");
                ini.setImage(enemyThreeImage.loadImage().getImage());
            }
            ini.setHeight(ini.getImage().getHeight(null));
            ini.setWidth(ini.getImage().getWidth(null));
            ini.setVisibility(true);
            enemies.add(ini);
       
		}
		
	}
	private void level2(Enemy enemy, ProxyImage enemyOneImage, ProxyImage enemyTwoImage, ProxyImage enemyThreeImage , ProxyImage enemyFourImage) {
		for (int i = 0; i <12; i++) {
        	//sets the enemy in the game
            Enemy ini = (Enemy) enemy.clone();
            ini.setX(Enemy.GeneratePosX(2,i));
            ini.setY(Enemy.GeneratePosY(2,i));

            if (i % 4 == 0) {
            	ini.setShots_to_death(1);
            	ini.setName("orange_alian");
                ini.setImage(enemyFourImage.loadImage().getImage());
            } else if (i % 2 == 0)  {
            	ini.setShots_to_death(2);
            	ini.setName("Edo");
                ini.setImage(enemyOneImage.loadImage().getImage());
             
            }else  {
            	ini.setShots_to_death(1);
            	ini.setName("Bat");
                ini.setImage(enemyThreeImage.loadImage().getImage());
            }
            ini.setHeight(ini.getImage().getHeight(null));
            ini.setWidth(ini.getImage().getWidth(null));
            ini.setVisibility(true);
            enemies.add(ini);
       
		}
		
	}
	

	private void level1(Enemy enemy, ProxyImage enemyOneImage, ProxyImage enemyTwoImage, ProxyImage enemyThreeImage, ProxyImage enemyFourImage) {
		for (int i = 0; i < 6; i++) {
        	//sets the enemy in the game
            Enemy ini = (Enemy) enemy.clone();
            ini.setX(Enemy.GeneratePosX(1,i));
            ini.setY(Enemy.GeneratePosY(1,i));

            if (i % 2 == 0) {
            	ini.setShots_to_death(2);
            	ini.setName("orange_alian");
                ini.setImage(enemyFourImage.loadImage().getImage());
            
            }else  {
            	ini.setShots_to_death(1);
            	ini.setName("bat");
                ini.setImage(enemyThreeImage.loadImage().getImage());
            }
            ini.setHeight(ini.getImage().getHeight(null));
            ini.setWidth(ini.getImage().getWidth(null));
            ini.setVisibility(true);
            enemies.add(ini);
        }
	}
	public void startGame() {
    	
        
        if (playing == false) {
            playing = true;
            mainPlayer.setIsDead(false);
    
            	
            
            isWon = false;
            if (begin) {
                begin = false;
            }

            startingPosition();

            initEnemy();
        }
   
   // mainPlayer.getControle().keyPressed(mainPlayer, e);

}

private void startingPosition() {
	if(index==0) {
		mainPlayer.setX(100);
		mainPlayer.setY(100);
		player2.setX(100);
		player2.setY(200);
	}
	else {
		mainPlayer.setX(100);
		mainPlayer.setY(200);
		player2.setX(100);
		player2.setY(100);
	}

	}
    @Override
    public void paint(Graphics g)
    {
    
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, null);
        if (playing) 

            

                g2d.drawImage(mainPlayer.getImage(), mainPlayer.getX(), mainPlayer.getY(), this);
            
           
            	g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), this);
            
   

            List<Bullet> bullets1 = mainPlayer.getBullet();
           
            for (int i = 0; i < bullets1.size(); i++) {

                Bullet m = (Bullet) bullets1.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);

            }
            List<Bullet> bullets2 = player2.getBullet();
            
            for (int i = 0; i < bullets2.size(); i++) {

                Bullet m = (Bullet) bullets2.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);

            }

            for (int i = 0; i < enemies.size(); i++) {

                Enemy in = enemies.get(i);
                g2d.drawImage(in.getImage(), in.getX(), in.getY(), this);
            }
    



            g2d.setColor(Color.WHITE);
            g2d.drawString("level: "+ score+" Enemies: " + enemies.size(), 5, 15);//counter of the enemys in the top of the screen


            /*else if (begin) {

            ImageIcon main_menu = new ImageIcon(getClass().getResource("/com/greatspace/sprites/main_menu.png"));
            menuimg = main_menu.getImage();
            g2d.drawImage(menuimg, 0, 0, null);

        }*/ 

        g2d.dispose();//end the use in this graphics context and releases any system resources that it that is using
		
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (enemies.isEmpty()) {
            playing = false;
            Client.SendstringToServer("WON="+score);
            //score++;
            
            playing =false;
            startGame();        }

        List<Bullet> bullets1 = mainPlayer.getBullet();

        for (int i = 0; i < bullets1.size(); i++) {

            Bullet m = (Bullet) bullets1.get(i);

            if (m.isVisible()) {
                m.check_if_the_bullet_cross_the_maxWidth();
            } else {
                bullets1.remove(i);//exit from the list
            }

        }
           
        

        List<Bullet> bullets2 = player2.getBullet();

        for (int i = 0; i < bullets2.size(); i++) {

            Bullet m = (Bullet) bullets2.get(i);

            if (m.isVisible()) {
                m.check_if_the_bullet_cross_the_maxWidth();
            } else {
                bullets2.remove(i);//exit from the list
            }

        }
        for (int i = 0; i < enemies.size(); i++) {
        	
            Enemy all_enemys = enemies.get(i);
        	if(all_enemys.isdead()) {
        		enemies.remove(i);
        	}
        	else {
        		if(all_enemys.getX()%11==0 &&all_enemys.getX()==0) {
        			
        		
        		all_enemys.enemy_fire();
        	     for (int j = 0; j < all_enemys.getBullets().size(); j++) {

     	            Bullet m = (Bullet) all_enemys.getBullets().get(j);

     	            if (m.isVisible()) {
     	                m.check_if_the_bullet_cross_the_maxWidth_bollean(false);
     	                
     	            } else {
     	            	all_enemys.getBullets().remove(j);
     	            }

     	        }
        		}
        		if(all_enemys.getName() == "Bat") {
        			if(score == 2)
            			all_enemys.specialMoveTOlitleEnemys();
        			
            			
        		}
        		if(all_enemys.getName() == "orange_alian") {
        			if(score == 1)
            			all_enemys.specialMoveTOlitleEnemys();
        			
            			
        		}
        		if(all_enemys.getName() == "Edo") {
        			if( score ==2)
            			all_enemys.specialMoveTOlitleEnemys();
        			
            			
        		}
        		if(all_enemys.getName() == "ooo") {
        			if( score ==0)
            			all_enemys.moveBoss(i);
        		}
        			
            	if(all_enemys.getName() == "Pini")
            	{
        			all_enemys.specialMove();
        			
            	}
        		else {

        			all_enemys.move();
            } 

         

        }
        }
 
        
        mainPlayer.the_speed_of_the_player();
        player2.the_speed_of_the_player();
        
        
            if (mainPlayer.isDead()) {
                

                startingPosition();
                mainPlayer.setIsDead(false);
            }
        
        repaint();
    }
   

    private class KeyboardAdapter extends KeyAdapter {

    	   @Override
           public void keyPressed(KeyEvent e)// end of the use of this keys
           {
               mainPlayer.getControle().keyPressed(mainPlayer, e);

           }
        @Override
        public void keyReleased(KeyEvent e)// end of the use of this keys
        {
            mainPlayer.getControle().keyReleased(mainPlayer, e);

        }

    }
    
    public boolean isPassedMainMenu() {
		return passedMainMenu;
	}

	@Override
	public void run() {
		
	}


	public void PressmovePlayer(int key) {
		player2.getControle().keyPressed(player2,key);
	}
	public void ReleasemovePlayer(int key) {
		player2.getControle().keyReleased(player2,key);
	}

	public void resetPlayer() {
		if(index==0) {
		
			player2.setX(100);
			player2.setY(200);
		}
		else {
			player2.setX(100);
			player2.setY(100);
		}
	}
}
