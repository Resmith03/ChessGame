import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private static final int PORT = 1321;
	private InetAddress serverAddress = Inet4Address.getByName("9.107.16.216");
	private static Socket socket;
	private static BufferedReader receive;
	private static PrintWriter send;
	
	public Client() throws Exception{
		socket = new Socket(serverAddress, PORT);
		receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		send = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public static void main(String args[]) throws Exception{
		Client c = new Client();
		String message;	
		try{
			//message = receive.readLine();
			while(true){
				System.out.print("Boo");
				//message = receive.readLine();
				send.println("Test send");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
//		finally{
//			socket.close();
//		}
	}
}
