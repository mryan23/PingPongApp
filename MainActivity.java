package com.example.pingpongclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {//implements SensorListener {
	SensorManager sm;
	float pos = 0;
	float vel = 0;
	long t = 0;
	long prevt = 0;
	float lastX = 0;
	float lastY = 0;
	String gameID = "3195";
	String message = "";
	DatagramSocket s;
	InetAddress i;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		try {
			s = new DatagramSocket(6789);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final View touchView = findViewById(R.id.view1);
		final TextView text = (TextView) findViewById(R.id.view1);
	    touchView.setOnTouchListener(new View.OnTouchListener() {

	    	@Override
	    	public boolean onTouch(View v, MotionEvent event) {
	    		System.out.println("Testing");
	            int action = event.getAction();
	            int midY = text.getHeight()/2;
	            int midX = text.getWidth()/2;
	            message = gameID + " 1 " +  Float.toString(event.getX() - midX) + " " + Float.toString(-event.getY() + midY);
	            try {
					i = InetAddress.getByName("24.219.213.60");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            DatagramPacket p = new DatagramPacket(message.getBytes(), message.length(), i, 6789);
	            try {
					s.send(p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            text.setText("Sent: " + message);
	            return true;
	    	}
	    });

	}
	
/*	 public void onSensorChanged(int sensor, float[] values) {
	        synchronized (this) {
	        	TextView text = (TextView) findViewById(R.id.view1);
	 
	            if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
	            	if (values[0] > 1 || values[0] < -1)
	            	{
	            		if (prevt == 0)
	            		{
	            			prevt = System.currentTimeMillis()/1000;
	            		}
	            		else
	            		{
	            			t = System.currentTimeMillis()/1000;
	            			vel = vel + values[0]*((t-prevt));
	            			pos += values[0]*(t-prevt);
	            			prevt = t;
	            		}
		            	String s = "Accel X: " + values[0] + "   Vel: " + vel + "   Pos: " + pos;
		            	text.setText(s);
	            	}
	            }            
	        }
	    }
	 
	    public void onAccuracyChanged(int sensor, int accuracy) {
	    	System.out.println("onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
	 
	    }
	 
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	        sm.registerListener(this, 
	                SensorManager.SENSOR_ORIENTATION |
	        		SensorManager.SENSOR_ACCELEROMETER,
	                SensorManager.SENSOR_DELAY_NORMAL);
	    }
	 
	    @Override
	    protected void onStop() {
	        sm.unregisterListener(this);
	        super.onStop();
	    }    */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
