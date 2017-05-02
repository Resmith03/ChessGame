import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int PORT = 1321;
	
	private static BufferedReader receive;
	private PrintWriter send;
	
	public static void main(String args[]) throws Exception{
		
		ServerSocket listener = new ServerSocket(PORT);
		Socket socket = listener.accept();
		
		System.out.println("Starting Chess Server...");
		try{
			while(true){
				receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(receive.readLine());
				
//				Game game = new Game();
//				Game.blackPlayer = new Player(listener.accept());
//				Game.whitePlayer = new Player(listener.accept());
			}
		}
		finally{
			listener.close();
		}
	}
}

//TODO Allow multiple people to connect to lobby
//TODO Allow people to challenge another player
//TODO Create Game in new thread with two players
