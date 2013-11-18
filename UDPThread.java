package com.example.pingpongclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class UDPThread extends AsyncTask<Void, Void, Void> {

	float lastX = 0;
	float lastY = 0;
	float curX = 0;
	float curY = 0;
	String gameID = "";
	String message = "";
	String player = "";
	DatagramSocket s;
	InetAddress i;
	
	public UDPThread(String id, String pnum)
	{
		gameID = id;
		player = pnum;
		try {
			s = new DatagramSocket(6789);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			i = InetAddress.getByName("ec2-184-72-139-63.compute-1.amazonaws.com");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setParams(float x, float y)
	{
		curX = x;
		curY = y;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		while(true)
		{
			if(this.isCancelled())
			{
				break;
			}
			if (curX != lastX && curY != lastY)
			{
				message = gameID + " " + player + " " + Float.toString(curX) + " " + Float.toString(curY);
		        DatagramPacket p = new DatagramPacket(message.getBytes(), message.length(), i, 6789);
		        try {
					s.send(p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        lastX = curX;
		        lastY = curY;
			}
		}
		return null;
	}
	
	
}
