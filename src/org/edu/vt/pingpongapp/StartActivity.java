package org.edu.vt.pingpongapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class StartActivity extends Activity implements OnItemSelectedListener {
	
	private Spinner playerSpinner_;
	private int player_ = 1;
	private Button startButton_;
	private EditText gameId_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		playerSpinner_ = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.players, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               
        // Apply the adapter to the spinner
        playerSpinner_.setAdapter(adapter);
        playerSpinner_.setOnItemSelectedListener(this);
        
        startButton_ = (Button) findViewById(R.id.startButton);
        gameId_ = (EditText) findViewById(R.id.gameIDText);
        
        startButton_.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//get game Id
				String id = gameId_.getText().toString();
				if (id.length() != 4) {
					Toast.makeText(getApplicationContext(), "Invalid Game ID", Toast.LENGTH_LONG).show();
				}
				else {
					Intent startGame = new Intent(getApplicationContext(), InGameActivity.class);
					startGame.putExtra("gameID", id);
					startGame.putExtra("playerNum", player_);
					startActivity(startGame);	
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		/*String answerString = (String) arg0.getItemAtPosition(arg2);
		if (answerString == "1 Player") {
			player_ = 1;
		}
		else if (answerString == "2 Player") {
			player_ = 2;
		}*/
		player_=arg2+1;
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		player_ = 1;
	}
	
}
