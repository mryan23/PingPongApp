package org.edu.vt.pingpongapp;

import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class InGameActivity extends Activity {
	
	SensorManager touchManager_;
    String gameID_ = "";
    String message_ = "";
    String user_ = "";
    int playerNum = 1;
    DatagramSocket socket_;
    InetAddress iAddress_;
    UDPThread udp_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_game);

		gameID_ = getIntent().getStringExtra("gameID");
		user_ = getIntent().getStringExtra("username");
		playerNum = getIntent().getIntExtra("playerNum", 1);
		
		touchManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		udp_ = new UDPThread(gameID_, ""+playerNum);
		udp_.execute();
		
        final View touchView = findViewById(R.id.touchText);
        touchView.setKeepScreenOn(true);
        final TextView text = (TextView) findViewById(R.id.touchText);
        touchView.setOnTouchListener(new View.OnTouchListener() {

        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
                 System.out.println("Testing");
                 int action = event.getAction();
                 int midY = text.getHeight()/2;
                 int midX = text.getWidth()/2;
                 int xdivide = text.getWidth()/70;
                 int ydivide = text.getHeight()/60;
                 message_ = gameID_ + " 2 " + Float.toString(event.getX() - midX) + " " + Float.toString(-event.getY() + midY);
                 if(playerNum == 1)
                 {
                 	udp_.setParams((event.getX() - midX)/xdivide, (-event.getY() + midY)/ydivide);
                 }
                 else if (playerNum == 2)
                 {
                 	udp_.setParams(-(event.getX() - midX)/xdivide, (-event.getY() + midY)/ydivide);
                 }
                 //text.setText("Sent: " + message_);
                 return true;
        	}
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	 @Override
     public void onStop()
     {
             udp_.cancel(true);
             super.onStop();
     }
}
