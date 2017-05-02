import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable{
	public String username;
	public String color;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	public Player(Socket socket){
		this.socket = socket;
		this.color = color;
		try{
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("Your color is "+color);
		}
		catch(IOException e){
			System.out.println("Error: "+e);
		}
	}
	
	public void run(){
		try{
			output.println("Run Test");
			while(true){
				String command = input.readLine();
				output.println(command);
			}
		} catch (IOException e){
			System.out.println(e);
		}

	}
}
