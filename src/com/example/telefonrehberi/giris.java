package com.example.telefonrehberi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class giris extends Activity{
	
	ImageButton dahili;
	ImageButton birimler;
@Override

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.ana);
	
	birimler=(ImageButton)findViewById(R.id.buttonBirimler);
	birimler.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(giris.this,MainActivityBirimler.class);
			startActivity(i);
		}
	});
	dahili=(ImageButton)findViewById(R.id.buttonhome);
	dahili.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(giris.this,MainActivity.class);
			startActivity(i);
		}
	});
}
@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
