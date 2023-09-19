package edu.escuelaing.arep;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;



/**
 * Class that keeps the connection with the API and gives the response
 * @author Johan Sebastian Garcia
 */
public class HttpConnection {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String GET_URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=64843024";
    private static String movieName = "";
    private static String responseString = "";
	
	
	/**
     * Main
     * Take the API's data
     * @param args
     * @throws IOException if is not able to connect
     */
    public static void main(String[] args) throws IOException {
    	
    	
    	
    	if(!Objects.equals(movieName, "")) {
    		String NEW_URL = GET_URL+movieName+API_KEY;
    		URL obj = new URL(NEW_URL);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    		con.setRequestMethod("GET");
    		con.setRequestProperty("User-Agent", USER_AGENT);
    		
    		//request response code
    		int responseCode = con.getResponseCode();
    		System.out.println("GET Response Code :: " + responseCode);
    		
    		//Checks the value of the response code
    		if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                responseString = response.toString();
                System.out.println(responseString);
            } else {
                System.out.println("GET request not worked");
            }
    	}
    }
    
    
    /**
     * Method that returns the response of the connection with the API
     * @return "responseString" the variable with the API response as a String
     */
    public static String getResponse() {
    	return responseString;
    }
    
    /**
     * Method that returns the movie
     * @return "movieName" the variable with the name of the movie
     */
    public static String getMovie() {
    	return movieName;
    }
    
    /**
     * Method that set the movieName value
     * @param "movie" as a string with the name of the movie
     */
    public static void setMovie(String movie) {
    	movieName = movie;
    }
    
    
    
}
