import java.io.*;
import java.net.*;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(4444);
		
		while (true)
		{
			Socket s = null;
			
			try
			{
				s = ss.accept();
				
				System.out.println("[SERVER] " + "A new client is connected : " + s);

				BufferedReader read = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter write = new PrintWriter(s.getOutputStream(), true);
				
				System.out.println("[SERVER] " + "Assigning new thread for this client");

				Thread t = new ClientHandler(s, read, write);

				t.start();
				
			}
			catch (Exception e){
				s.close();
				e.printStackTrace();
			}
		}
	}
}

class ClientHandler extends Thread
{
	final BufferedReader read;
	final PrintWriter write;
	final Socket s;
	 

	public ClientHandler(Socket s, BufferedReader read, PrintWriter write)
	{
		this.s = s;
		this.read = read;
		this.write = write;
	}

	@Override
	public void run()
	{
		String received;
		while (true)
		{
			try {
				received = read.readLine();
				
				if(received.equals("Exit"))
				{
					System.out.println("[ClientHandler] " + "Client " + this.s + " sends exit...");
					System.out.println("[ClientHandler] " + "Closing this connection.");
					this.s.close();
					System.out.println("[ClientHandler] " + "Connection closed");
					break;
				}
				else {
					write.println("[ClientHandler] " + received + " is what you said reversed!");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try
		{
			this.read.close();
			this.write.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
