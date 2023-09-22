package edu.escuelaing.arep.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import edu.escuelaing.arep.HttpServer;

/**
 * Clase main encargada de iniciar el servidor HTTP con una instancia de la clase HttpServer
 * @author Johan Sebastian Garcia
 */

public class MainService {

	/**
	 * Método main encargado de iniciar el servidor HTTP
	 * @param args arguments
	 * @throws IOException si no establece conexión con el servidor
	 */
	public static void main(String[] args) throws IOException{
		
		HttpServer server = HttpServer.getInstance();
		
		try {
			server.run(args);
		}catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();}
	}

}
