package org.edu.vt.pingpongapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Leaderboard extends Activity{
	
	private ListView myScoreList_ = null;
	private ArrayList<String> scores_;
	private ArrayAdapter<String> adapter_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		
		myScoreList_ = (ListView) findViewById(R.id.scores);
		scores_ = new ArrayList<String>();
		adapter_ = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores_);
		myScoreList_.setAdapter(adapter_);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	
	public void updateScores(Vector<String> newScores) {
		scores_.clear();
		
		Iterator<String> itr = newScores.iterator();
		
		while (itr.hasNext()) {
			String curString = itr.next();
			if (curString != null && curString != "") {
				scores_.add(curString);
			}
		}
		
		adapter_.notifyDataSetChanged();
	}
}
