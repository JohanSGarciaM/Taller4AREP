package edu.escuelaing.arep.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.escuelaing.arep.controller.annotations.Component;
import edu.escuelaing.arep.controller.annotations.RequestMapping;

@Component
public class stController {

	
	@RequestMapping(value="/css")
	public static String css() {
		String response = "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/css\r\n" +
                "\r\n" + body("style.css");
		return response;
				
	}
	
	@RequestMapping(value="/js")
	public static String js() {
		String response = "HTTP/1.1 200 OK\r\n" +
	                "Content-type: text/javascript\r\n" +
	                "\r\n" + body("script.js");
	    return response;
	}
	
	
	
	private static String body(String extention){
        byte[] content = new byte[0];
        try {
            Path file = Paths.get("src/main/resources/"+extention);
            content = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(content);
    }
}
