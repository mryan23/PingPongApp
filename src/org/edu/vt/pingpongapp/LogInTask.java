package org.edu.vt.pingpongapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class LogInTask extends AsyncTask<String, Void, String> {
	
	LogInActivity myActivity_;
	static Gson gson = new Gson();
	
	public LogInTask(LogInActivity newActivity) {
		myActivity_ = newActivity;
	}

	@Override
	protected String doInBackground(String... arg0) {

		String[] nameTokens = arg0[0].split(" ");
        
        String username = nameTokens[0];
        String password = nameTokens[1];
        
        String hashPassword = null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			 
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        
	        hashPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        //Set up URL
        String URL = "http://ec2-184-72-139-63.compute-1.amazonaws.com:8080/login?username=" + username + "&password=" + hashPassword;
        
        //Send HTTP GET request
        HttpResponse response = null;
        try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(URL);
                response = client.execute(request);
        }
        catch (Exception e){
                Log.i("ERROR", e.toString());
        }
        
        //Read in the http response to form a readable string
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
                InputStream in = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                }
        }
        catch (IOException e) {
                Log.i("ERROR", e.toString());
        }
        
        //Get response
        String httpResponseVal = sb.toString();
        Response resp = gson.fromJson(httpResponseVal, Response.class);
        
        //Return the result back to the GUI
        return resp.result;
	}
	
	protected void onPostExecute(String incomingString) {
        myActivity_.update(incomingString);
	}
	
	private class Response {
		public String username = null;
		public String password = null;
		public String result = null;
	}

}
