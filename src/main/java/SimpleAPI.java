import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.*;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class SimpleAPI {

	public static void main(String[] args) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		server.createContext("/hello", new HelloHandler());

		server.createContext("/", new StaticFileHandler());

		server.setExecutor(null);
		server.start();

	}

	static class HelloHandler implements HttpHandler {
		public void handle(HttpExchange exchange) throws IOException{
			if ("GET".equals(exchange.getRequestMethod())){
				String response = "Hola desde la API en java sin frameworks";
				exchange.sendResponseHeaders(200, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}else {
				
				String response = "Método HTTP no admitido";
				exchange.sendResponseHeaders(405, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		}
	}
	
	static class StaticFileHandler implements HttpHandler{
		public void handle(HttpExchange exchange) throws IOException{
			
			
			String path = exchange.getRequestURI().getPath();
			File file = new File("src/main/java"+path);
			if(file.exists() && !file.isDirectory()) {
				byte[] fileBytes = Files.readAllBytes(file.toPath());
				exchange.sendResponseHeaders(200, fileBytes.length);
				OutputStream os = exchange.getResponseBody();
				os.write(fileBytes);
				os.close();
			//Si el archivo no se encuentra
			}else {
				String response = "Archivo no encontrado";
				exchange.sendResponseHeaders(404, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		}
	}

}






