import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client
{
	public static void main(String[] args) throws IOException
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			InetAddress ip = InetAddress.getByName("localhost");
			Socket s = new Socket(ip, 4444);
			
			BufferedReader read = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter write = new PrintWriter(s.getOutputStream(), true);
	
			// the following loop performs the exchange of
			// information between client and client handler
			while (true)
			{
				System.out.print("[CLIENT] Enter a Word (Type 'Exit' to close the client): ");
				String tosend = scanner.nextLine();
				
				StringBuffer sb = new StringBuffer(tosend);
				sb.reverse();
				
				write.println(sb);
				
				if(tosend.equals("Exit"))
				{
					write.println(tosend);
					System.out.println("[CLIENT] " + "Closing this connection : " + s);
					s.close();
					System.out.println("[CLIENT] " + "Connection closed");
					break;
				}
		
				String received = read.readLine();
				System.out.println(received);
			}
			
			scanner.close();
			read.close();
			write.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
