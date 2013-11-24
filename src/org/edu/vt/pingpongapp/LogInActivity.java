package org.edu.vt.pingpongapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {
	
	private EditText username_;
	private EditText password_;
	private Button loginButton_;
	private Button signupButton_;
	
	private String user_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		username_ = (EditText) findViewById(R.id.usernameText);
		password_ = (EditText) findViewById(R.id.passwordText);
		loginButton_ = (Button) findViewById(R.id.loginButton);
		signupButton_ = (Button) findViewById(R.id.signupButton);
		
		loginButton_.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				user_ = username_.getText().toString();
				String password = password_.getText().toString();
				
				if (user_ == "" || password == "") {
					Toast.makeText(getApplicationContext(), "Invalid Length Username/Password", Toast.LENGTH_LONG).show();
				}
				else {
					String params = user_ + " " + password;
                	final LogInTask logIn = new LogInTask(LogInActivity.this);
                	logIn.execute(params);
				}
			}
			
		});
		
		signupButton_.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				user_ = username_.getText().toString();
				String password = password_.getText().toString();
				
				if (user_ == "" || password == "") {
					Toast.makeText(getApplicationContext(), "Invalid Length Username/Password", Toast.LENGTH_LONG).show();
				}
				else {
					String params = user_ + " " + password; 
                	final SignUpTask signUp = new SignUpTask(LogInActivity.this);
                	signUp.execute(params);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	 //See if login/sign-up was successful
    public void update(String result) {   
    	if (result.contains("Success")) {
    		Intent start = new Intent(getApplicationContext(), StartActivity.class);
    		start.putExtra("username", user_);
    		startActivity(start);
    		finish();
    	}
    	else {
    		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    	}
    }

}
