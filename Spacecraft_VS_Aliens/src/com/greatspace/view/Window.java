
package com.greatspace.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Network.Client;


public class Window {
	public Game game;
    public Window(int index) {

        JFrame frame = new JFrame("Spacecraft VS Aliens"+index);
        //JPanel panel =  new JPanel();
        game = new Game(index);
        //ConnectionToServer con = new ConnectionToServer("g", "127.0.0.1",4242,f);
        frame.setContentPane(game);
        game.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //while (!f.isPassedMainMenu()) {}
        frame.setJMenuBar(game.criarMenu());
        
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        try {
            URL url = getClass().getResource("../sprites/gsicon.png");
            BufferedImage image = ImageIO.read(url);
            frame.setIconImage(image);
        } catch (IOException e) {
            System.out.println("ImageError: " + e);
        }
        frame.setVisible(true);
    }


}
