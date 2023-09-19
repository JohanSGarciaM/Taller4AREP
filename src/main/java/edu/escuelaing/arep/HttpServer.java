package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.escuelaing.arep.controller.annotations.Component;
import edu.escuelaing.arep.controller.annotations.RequestMapping;


/**
 * Clase que implementa un servidor HTTP encargado de manejar solicitudes GET y POST.
 * @author Johan Sebastian Garcia
 */
public class HttpServer {
	
	
	public static ServerSocket serverSocket;
	static HttpServer instance = new HttpServer();
	private final static String root = "edu/escuelaing/arep/controller";
	private static Map<String, Method> services = new HashMap<String, Method>();
	public static PrintWriter out;
	
	
	
	/**
     * Obtiene la única instancia de HttpServer.
     * @return Instancia única de HttpServer.
     */
    public static HttpServer getInstance(){
        return instance;
    }
    
    /**
     * Starts the Http server and control the connection with the annotations of the classes
     * @param args
     * @throws IOException if is not able to connect.
     */
    public void run(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

        List<Class<?>> classes = getClasses();
        for (Class<?> clasS:classes){
            if(clasS.isAnnotationPresent(Component.class)){
                Class<?> c = Class.forName(clasS.getName());
                Method[] methods = c.getMethods();
                for (Method method: methods){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        String key = method.getAnnotation(RequestMapping.class).value();
                        services.put(key,method);
                    }
                }
            }
        }
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Listo para recibir...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
    }
    
    /**
     * Handle a request
     * @param clientSocket socket pointed to the client
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void handleRequest(Socket clientSocket) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String uriString = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (inputLine.startsWith("GET")) {
                    uriString = inputLine.split(" ")[1];
                    break;
                } else if (inputLine.startsWith("POST")) {
                    uriString = inputLine.split(" ")[1];
                    break;
                }
            }
            String outputLine = "";
            if(!uriString.equals("/favicon.ico") && !uriString.equals("/")){
                String[] uriParts = uriString.split("\\?");
                String serviceToUse = uriParts[0];
                System.out.println(serviceToUse);
                System.out.println(services.containsKey(serviceToUse));
                if(services.containsKey(serviceToUse)){
                    outputLine = services.get(serviceToUse).invoke(null).toString();
                }
            } else {
                outputLine = indexResponse();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
     * method that control the format of the classes
     * @return classes: a list of the classes with the correct format
     */
    
    private static List<Class<?>> getClasses(){
        List<Class<?>> classes = new ArrayList<>();
        String classPath = System.getProperty("java.class.path");
        String[] classPaths =  classPath.split(System.getProperty("path.separator"));
        ArrayList<String> classPaths1 = new ArrayList<>(Arrays.asList(classPaths));
        try{
            for (String cp: classPaths1){
                File file = new File(cp + "/" + root);
                if(file.exists() && file.isDirectory()){
                    for (File cf: Objects.requireNonNull(file.listFiles())){
                        if(cf.isFile() && cf.getName().endsWith(".class")){
                            String rootTemp = root.replace("/",".");
                            String className = rootTemp+"."+cf.getName().replace(".class","");
                            Class<?> clasS =  Class.forName(className);
                            classes.add(clasS);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classes;
    }
    
    /**
     * Makes a HTML basic response
     * @return outputLine HTML form
     */
    public static String indexResponse() {
        String outputLine = "HTTP/1.1 200 OK\r\n"
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
                + "                <h3>Si deseas acceder a funciones o recursos, debes hacerlo desde la URL ;)</h3>\r\n"
                + "            </div>\r\n"
                + "    </body>\r\n"
                + "</html>";
    
        return outputLine;
    }
    
}
