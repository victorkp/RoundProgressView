package com.example.roundedimagetest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.roundedimagetest.RoundedProgressView.ProgressListener;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final RoundedProgressView p1 = (RoundedProgressView) findViewById(R.id.progress1);
		final RoundedProgressView p2 = (RoundedProgressView) findViewById(R.id.progress2);
		final RoundedProgressView p3 = (RoundedProgressView) findViewById(R.id.progress3);
		
		
		p1.setListener(new ProgressListener() {
			@Override
			public void onUpdate(double progress) {
				
			}
			
			@Override
			public void onLooped() {
				
			}
			
			@Override
			public void onCompleted() {
				p2.startAnimatingProgress(1500, false);
			}
		});
		
		p1.startAnimatingProgress(4000, false);
		p2.setReversed(true);
		p3.startAnimatingProgress(3000, true);
	}

}
