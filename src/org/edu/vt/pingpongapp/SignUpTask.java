package org.edu.vt.pingpongapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class SignUpTask extends AsyncTask<String, Void, String> {
	
	LogInActivity myActivity_;
	
	public SignUpTask(LogInActivity newActivity) {
		myActivity_ = newActivity;
	}

	@Override
	protected String doInBackground(String... arg0) {

		String[] nameTokens = arg0[0].split(" ");
        
        String username = nameTokens[0];
        String password = nameTokens[1];
        
        //Set up URL
        String URL = "http://ec2-184-72-139-63.compute-1.amazonaws.com:8080/signup?username=" + username + "&password=" + password;
        
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
        
        String[] responseTokens = httpResponseVal.split("><");
        String result = null;
        
        //Find result and reason in response
        for (int i = 0; i < responseTokens.length; i++) {
                if (responseTokens[i].contains("result")) {
                        result = responseTokens[i].substring(7, responseTokens[i].length() - 8);
                }
        }
        
        //Return the result back to the GUI
        return result;
	}
	
	protected void onPostExecute(String incomingString) {
        myActivity_.update(incomingString);
	}

}
