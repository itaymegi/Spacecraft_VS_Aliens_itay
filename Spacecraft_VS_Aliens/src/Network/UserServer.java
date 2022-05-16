package Network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class UserServer {
    private String name="";
    private Socket socket;
    private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;	


    public UserServer(Socket socket,String name) {
        this.socket = socket;
        this.name = name;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getName() {
        return name;
    }

    public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public Socket getSocket() {
        return socket;
    }

  
}