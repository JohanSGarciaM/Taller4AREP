import java.io.*;
import java.net.*;

public class Servidor {

	
	public static void main(String[] args)throws IOException{
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(35000);
		}catch(IOException e) {
			System.err.println("Could not listen on port: 35000");
			System.exit(1);
		}
		
		boolean running = true;
		while (running) {
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir.");
				clientSocket = serverSocket.accept();
			}catch(IOException e) {
				System.err.println("Accept failed");
				System.exit(1);
			}
			System.out.println("Sale del ciclo.");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String path = null;

            String name = "";

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    path = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
                System.out.println(path);
                in.close();
                clientSocket.close();
            }

            outputLine = "HTTP/1.1 200 OK \r\n";
            
            serverSocket.close();
		}
	}
}
