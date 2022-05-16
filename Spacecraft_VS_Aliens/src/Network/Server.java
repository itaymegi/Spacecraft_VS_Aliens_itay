package Network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;



public class Server {
    private static final int PORT = 4242;
    public static final int MAX_USERS = 2;
    private static ArrayList<UserServer>players = new ArrayList<UserServer>();
	private static int level=0; 


	
	
	
    public Server() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        	System.out.println(serverSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server is ON");
        int numberOfClientsConnected = 0;

        System.out.println("Waiting for " + (MAX_USERS - numberOfClientsConnected) + " more users");
        
        Socket socket;
       
        while(MAX_USERS>numberOfClientsConnected) {
        	try {
    			socket = serverSocket.accept();
    		    System.out.println("User " + socket.getInetAddress().getHostName() + " has connected");
    		    UserServer player;
    	        
                player = new UserServer(socket,"");// (numberOfClientsConnected));
                String name = player.getBufferedReader().readLine();
                player.setName(name);
                System.out.println(name);
                players.add(player);
                numberOfClientsConnected++;
	      		 socket = player.getSocket();
	      		 
	      		  new Thread(new Runnable() {
	      			  @Override
	      			  public void run() {
	      				  StartReceivingData(player);
	      			  }
	      		  }).start();
	      

             
    	}
    		 catch(Exception e) {
           	  e.printStackTrace();
             }
        }
        
        for (int i = 0; i < players.size(); i++) {
			SendDataToPlayer("INDEX="+i, players.get(i));
		}
		 
		//this.startGame();
    }
		  



  

    public static void CloseAllSockets() throws IOException {
       // System.out.println("Closing all sockets.");
        if(players!=null)
        for (UserServer player : players) {
            Socket socket = player.getSocket();
            System.out.println("Closing Socket " + socket.getInetAddress().getHostName());
            if (socket.isConnected())
                socket.close();
        }
    }




    
    private void startGame() {    
        SendDataToAllSockets("START");
    }
    
    
    private void StartReceivingData(UserServer player) {
    	
        	
            try {

                String dataFromClient = "";
                while (true) {
             		
            	
                    dataFromClient = player.getBufferedReader().readLine();
                    if(dataFromClient==null) {
                    	continue;
                    }                               
                    else {
                    	if(dataFromClient.contains("WON")) {
                    		String []arr = dataFromClient.split("=");
                    		if(level==Integer.parseInt(arr[1])) {
                    			level++;
                    			if(level==4) {
                    				level=-1;
                    			}
                    			SendDataToAllSockets("Server=NEXT="+level);
                    		}
                    	}
                    	else {
                    		 BroadcastMessage(player,dataFromClient);
                    	}
                   
                    }
                    }

                
        
                    
            } catch (IOException e) {
            	try {
    				CloseAllSockets();
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
                System.out.println("There was an error receiving data.");
            }
        
    }


public static void BroadcastMessage(UserServer sender, String data) {
	 for(UserServer player: players) {
		 if(player!=null) {
			 
		 
		 if(!player.getName().equals(sender.getName())) {
			 try {
		         player.getBufferedWriter().write(data);
				 player.getBufferedWriter().newLine();
				 player.getBufferedWriter().flush();
			 }
		    catch (IOException e) {
		    	try {
					CloseAllSockets();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            e.printStackTrace();
	        }
		 }
	 }
	 }
 }
 public static void SendDataToPlayer(String data, UserServer player) {
		 try {
		 player.getBufferedWriter().write(data);
	     player.getBufferedWriter().newLine();
	     player.getBufferedWriter().flush();
		 }
	  catch (IOException e) {
			try {
				CloseAllSockets();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	     e.printStackTrace();
	 }
 }
 public static void SendDataToAllSockets(String data) {
	 for(UserServer player: players) {
        try {
            player.getBufferedWriter().write(data);
            player.getBufferedWriter().newLine();
            player.getBufferedWriter().flush();
        } catch (IOException e) {
        	try {
				CloseAllSockets();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
	 }
        
    }
  
    }
