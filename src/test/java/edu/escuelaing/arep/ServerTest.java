package edu.escuelaing.arep;


import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import edu.escuelaing.arep.controller.stController;


public class ServerTest {

	
	@Test
    public void testHelloFormer() {
        String input = "Johan";
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<!DOCTYPE html>\r\n" +
                "<html>\r\n" +
                "    <head>\r\n" +
                "        <meta charset=\"UTF-8\">\r\n" +
                "        <title>Salutation</title>\r\n" +
                "    </head>\r\n" +
                "    <body>\r\n" +
                "         <h1> Your message is: Johan</h1>\r\n" +
                "    </body>\r\n" +
                "</html>";
        
        String response = HttpServer.helloFormer(input);

        assertEquals(expectedResponse, response);
    }
	
	
	
	/**
	 * @Test Check if the server can response js files
	 */
	@Test
	public void javaTest() {
		String expected = "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/javascript\r\n" +
                "\r\n" + "alert(\"hello world, its johan form js file\");";
		HttpServer server = HttpServer.getInstance();
		Map<String,Method> services = server.getServices();
		if(services.containsKey("/js")) {

			try {
				String outputLine = services.get("/js").invoke(null).toString();
				assertEquals(expected,outputLine);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
		}		
	}
	
	/**
	 * @Test chech if the server can response /hello file
	 */
	@Test
	public void helloControllerTest() {
		String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\r\n"
                + "<html>\r\n"
                + "    <head>\r\n"
                + "        <title>Form Example</title>\r\n"
                + "        <meta charset=\"UTF-8\">\r\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                + "    </head>\r\n"
                + "    <body>\r\n"
                + "        <div class=\"feedback-card\">\r\n"
                + "            <div class=\"feedback-header\">\r\n"
                + "                <h1>Pagina Principal</h1>\r\n"
                + "                <h3>Este es el servicio Hello basico</h3>\r\n"
                + "            </div>\r\n"
                + "    </body>\r\n"
                + "</html>";
		HttpServer server = HttpServer.getInstance();
		Map<String,Method> services = server.getServices();
		if(services.containsKey("/hello")) {
			String outputStream;
			try {
				outputStream = services.get("/hello").invoke(null).toString();
				assertEquals(expected,outputStream);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * @Test check if the server can response css file
	 */
	@Test
	public void cssTest() {
		String response = "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/javascript\r\n" +
                "\r\n" + stController.css();
		HttpServer server = HttpServer.getInstance();
		Map<String,Method> services = server.getServices();
		if(services.containsKey("/css")){
			try {
				String outputStream = services.get("/css").invoke(null).toString();
				assertEquals(response,outputStream);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
