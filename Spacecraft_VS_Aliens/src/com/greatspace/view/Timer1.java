package com.greatspace.view;
import java.util.concurrent.TimeUnit;

public class Timer1 extends Thread {

    Game panel;

    public Timer1(Game panel)
    {
        this.panel=panel;
    }

    public void run()
    {
        while(true)
        {
            try {
                sleep(1000);
                panel.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.Time+=1;

        }
    }
}
