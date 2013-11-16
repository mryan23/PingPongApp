package org.edu.vt.pingpongapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class InGameActivity extends Activity {
	
	private TextView player1Name_;
	private TextView player2Name_;
	private TextView player1Score_;
	private TextView player2Score_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_game);
		
		player1Name_ = (TextView) findViewById(R.id.player1Text);
		player2Name_ = (TextView) findViewById(R.id.player2Text);
		player1Score_ = (TextView) findViewById(R.id.player1Score);
		player2Score_ = (TextView) findViewById(R.id.player2Score);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
}
