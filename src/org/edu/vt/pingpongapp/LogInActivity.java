package org.edu.vt.pingpongapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends Activity {
	
	private EditText username_;
	private EditText password_;
	private Button gamePlay_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		username_ = (EditText) findViewById(R.id.usernameText);
		password_ = (EditText) findViewById(R.id.passwordText);
		gamePlay_ = (Button) findViewById(R.id.playButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

}
