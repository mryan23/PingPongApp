package org.edu.vt.pingpongapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class LeaderboardTask extends AsyncTask<Void, Void, Vector<String>>{
	
	static Gson gson = new Gson();
	Leaderboard myActivity_;
	
	public LeaderboardTask(Leaderboard activity_) {
		myActivity_ = activity_;
	}

	@Override
	protected Vector<String> doInBackground(Void... arg0) {

		// Set up URL
		String URL = "http://ec2-184-72-139-63.compute-1.amazonaws.com:8080/leaderboard";
		
		// Send HTTP GET request
		HttpResponse response = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(URL);
			response = client.execute(request);
		} catch (Exception e) {
			Log.i("ERROR", e.toString());
		}

		// Read in the http response to form a readable string
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.i("ERROR", e.toString());
		}

		// Get response
		String httpResponseVal = sb.toString();
		Response resp = gson.fromJson(httpResponseVal, Response.class);

		// Return the result back to the GUI
		return resp.result;
	}
	
	protected void onPostExecute(Vector<String> incomingString) {
        myActivity_.updateScores(incomingString);
	}
	
	private class Response {
		Vector<String> result;
	}

}
