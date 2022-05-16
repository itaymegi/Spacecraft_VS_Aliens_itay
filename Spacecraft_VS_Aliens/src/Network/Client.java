package Network;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.greatspace.controller.Touch;
import com.greatspace.view.Game;
import com.greatspace.view.Window;


public class Client {
    private final int PORT ;//
    private final String IP;//
    private static Socket socket;
    private int i=0;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private String name;
    private boolean win=false;
    private Game game;
	private JFrame frame;
    public Client(String playerName,String IP,int PORT) {
        this.IP=IP;
        
        this.PORT=PORT;
        name = playerName;
        
        System.out.println("Trying to connect to server");
        startServerConn();
        System.out.println("Connect to server");
        try {
            
    		 
            SendstringToServer(playerName);
    		
             
            Receiviedata();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null) {
                    System.out.println("closing the socket.");
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

    }

    public static void SendstringToServer(String data) {
    	
    	
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private void Receiviedata()  throws IOException {
    	
    
        try {
         
        	 InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             setPlayerIndex(inputStreamReader, bufferedReader);
            while (true) {
            	String dataFromServer = "";
            	dataFromServer  = bufferedReader.readLine();
            	if(dataFromServer==null) {
            		break;
            		
            	}
            	//System.out.println(dataFromServer);
            	String[] arr = dataFromServer.split("=");
            	if(dataFromServer.equals("DEAD")) {
            		game.resetPlayer();
            	}
            	if(arr.length==3) {
            	if(arr[1].equals("NEXT")) {
            		int number = Integer.parseInt(arr[2]);
            		if(number!=-1)
            			game.setScore(number);
            	}
            	if(arr[1].equals("Press")&arr[2].equals("DOWN")) {
            		game.PressmovePlayer(Touch.PLAYER_ONE_MOVE_DOWN);
            		
            	}
            	if(arr[1].equals("Press")&arr[2].equals("UP")) {
            		game.PressmovePlayer(Touch.PLAYER_ONE_MOVE_UP);
            	}
            	if(arr[1].equals("Press")&arr[2].equals("RIGHT")) {
            		game.PressmovePlayer(Touch.PLAYER_ONE_MOVE_RIGHT);
            	}
            	if(arr[1].equals("Press")&arr[2].equals("LEFT")) {
            		game.PressmovePlayer(Touch.PLAYER_ONE_MOVE_LEFT);
            	}
            	if(arr[1].equals("Press")&arr[2].equals("FIRE")) {
            		game.PressmovePlayer(Touch.PLAYER_ONE_FIRE);
            	}
            	
            	
            	
            	if(arr[1].equals("Release")&arr[2].equals("DOWN")) {
            		game.ReleasemovePlayer(Touch.PLAYER_ONE_MOVE_DOWN);
            	}
            	if(arr[1].equals("Release")&arr[2].equals("UP")) {
            		game.ReleasemovePlayer(Touch.PLAYER_ONE_MOVE_UP);
            	}
            	if(arr[1].equals("Release")&arr[2].equals("RIGHT")) {
            		game.ReleasemovePlayer(Touch.PLAYER_ONE_MOVE_RIGHT);
            	}
            	if(arr[1].equals("Release")&arr[2].equals("LEFT")) {
            		game.ReleasemovePlayer(Touch.PLAYER_ONE_MOVE_LEFT);
            	}
            	
            	
            	}
            }
          
        } catch (IOException e) {
            System.out.println("There was an error receiving data.");
    
        }
     
    
}

	private void setPlayerIndex(InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
		while(true) {
		 	String dataFromServer = "";
		 	try {
				dataFromServer = bufferedReader.readLine();
				if(dataFromServer.contains("INDEX")) {
			 		
			 		int index = Integer.parseInt(dataFromServer.split("=")[1]);
			 		//game.setIndexForPlayer(index);
			 		Window win = new Window(index);
			 		game =win.game;
			 		
			 		break;
			 		
			 	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	}
	}
    
    public boolean IsSocketClosed() {
    	System.out.println(socket.isClosed());
    	return socket.isClosed();
    }
    

    private void startServerConn() {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            socket = new Socket(IP, PORT);
            
            try {
    			inputStream = socket.getInputStream();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        } catch (IOException e) {
            startServerConn();
        }
    }

	public boolean isWin() {
		return win;
	}
	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter username");

	    String userName = myObj.nextLine();  // Read user input
		new Client(userName, "127.0.0.1", 4242);
	}

}