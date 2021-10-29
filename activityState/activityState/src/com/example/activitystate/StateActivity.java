package com.example.activitystate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
public class StateActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
	}
	
	protected void onPause() {
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
	}
	@Override
	protected void onStop() {
		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
}