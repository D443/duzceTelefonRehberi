package com.example.telefonrehberi;

import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivityBirimler extends Activity implements OnItemSelectedListener {

	ListView lstBirimler;
	ProgressDialog progress;
	
	Spinner spnBirim;
	

	SQLiteDatabase db;
	SQLiteDatabase ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birimler_layout);

		db = new DBYardimciBirim(this).getReadableDatabase();
		lstBirimler = (ListView)findViewById(R.id.lstBirimler);
        spnBirim=(Spinner)findViewById(R.id.spnBirimiki);
	
		SharedPreferences pref = getSharedPreferences("Acilis", MODE_PRIVATE);
		boolean ilkMi = pref.getBoolean("ilkAcilisMi", true);

		if (ilkMi) {
			Editor edit = pref.edit();
			edit.putBoolean("ilkAcilisMi", false);
			edit.commit();

			progress = ProgressDialog.show(this, "Lütfen bekleyiniz.",
					"Liste internetten alýnýyor.");

			BirimRehberGetir rg = new BirimRehberGetir(this);
			rg.start();
		} else {
			birimListele();
		}

	}
	public boolean isConn(){
		 ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 if(connectivity.getActiveNetworkInfo()!=null){
		 if(connectivity.getActiveNetworkInfo().isConnected ())
		 return true;
		 }
		 
		 return false;
		 }
	public void btnYenile(View view) {
		if (isConn()) {
			deleteDatabase(DBYardimciBirim.dbAdi);
			
			db = new DBYardimciBirim(this).getReadableDatabase();
			
			progress = ProgressDialog.show(this, "Lütfen bekleyiniz.",
					"Liste internetten alýnýyor.");

			BirimRehberGetir rg = new BirimRehberGetir(this);
			rg.start();
		}
		else{
			Toast.makeText(getApplicationContext(), "Ýnterneti kontrol ediniz", Toast.LENGTH_LONG).show();
		}

	}
   
	
	
	
	
 
	public void birimListele() {
		
		Cursor rdrBirim = db.rawQuery("select distinct BirimAdi from Birim",
				null);

		ArrayList<String> birimler = new ArrayList<String>();
		while (rdrBirim.moveToNext()) {
			birimler.add(rdrBirim.getString(0));
		}
		rdrBirim.close();

		birimler.add(0, "Seçiniz...");

		if (birimler.size() > 0) {
			ArrayAdapter<String> adpBirim = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, birimler);
			adpBirim.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnBirim.setAdapter(adpBirim);
		}
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	public void btnArama(View view) {
		String sorgu = "select * from Birim";
		StringBuilder sb = new StringBuilder();
		if (spnBirim.getSelectedItemPosition() > 0) {
			if (!sb.toString().equals("")) {
				sb.append(" and ");
			}
			sb.append("BirimAdi = '");
			sb.append(spnBirim.getSelectedItem().toString());
			sb.append("'");
			
		}
		if (!sb.toString().equals("")) {
			sorgu += " where " + sb.toString();
		}
		

		Cursor cr = db.rawQuery(sorgu, null);

		ArrayList<Birimler> liste = new ArrayList<Birimler>();

		while (cr.moveToNext()) {
			
			Birimler b = new Birimler();
			b.BirimAdi = cr.getString(0);
			b.BirimTel= cr.getString(1);
			b.BirimFax = cr.getString(2);
			
			liste.add(b);
		}

		BirimlerAdaptor adap = new BirimlerAdaptor(this, liste);
		lstBirimler.setAdapter(adap);
	}

	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	
}

