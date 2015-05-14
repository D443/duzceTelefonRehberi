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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements OnItemSelectedListener {

	ListView lstPersonel;
	ProgressDialog progress;

	EditText txtAdi;
	EditText txtSoyadi;
	EditText txtDahili;

	Spinner spnBirim;
	Spinner spnBolum;

	SQLiteDatabase db;
	SQLiteDatabase ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DbYardimci(this).getReadableDatabase();

		txtAdi = (EditText) findViewById(R.id.txtAdi);
		txtSoyadi = (EditText) findViewById(R.id.txtSoyad);
		txtDahili = (EditText) findViewById(R.id.txtDahili);

		spnBirim = (Spinner) findViewById(R.id.spnBirim);
		spnBirim.setOnItemSelectedListener(this);
		lstPersonel = (ListView)findViewById(R.id.lstPersonal);
		spnBolum = (Spinner) findViewById(R.id.spnBolum);

		SharedPreferences pref = getSharedPreferences("Acilis", MODE_PRIVATE);
		boolean ilkMi = pref.getBoolean("ilkAcilisMi", true);

		if (ilkMi) {
			Editor edit = pref.edit();
			edit.putBoolean("ilkAcilisMi", false);
			edit.commit();

			progress = ProgressDialog.show(this, "Lütfen bekleyiniz.",
					"Liste internetten alýnýyor.");

			RehberGetir rg = new RehberGetir(this);
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
	public void birimListele() {
		Cursor rdrBirim = db.rawQuery("select distinct Birimi from Personel",
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

	public void bolumListele(String birimi) {

		if (birimi.equals("Seçiniz...")) {
			ArrayAdapter<String> adpBolum = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item);
			adpBolum.add("Seçiniz...");

			adpBolum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnBolum.setAdapter(adpBolum);

			return;
		}

		Cursor rdrBolum = db.rawQuery(
				"select distinct Bolumu from Personel where Birimi = '"
						+ birimi + "'", null);
			
		ArrayList<String> bolumler = new ArrayList<String>();
		while (rdrBolum.moveToNext()) {
			
				bolumler.add(rdrBolum.getString(0));
			
			
		}

		rdrBolum.close();

		bolumler.add(0, "Seçiniz...");

		if (bolumler.size() > 0) {
			ArrayAdapter<String> adpBolum = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, bolumler);
			adpBolum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnBolum.setAdapter(adpBolum);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void btnYenile(View view) {
		if (isConn()) {
			deleteDatabase(DbYardimci.dbAdi);
			
			db = new DbYardimci(this).getReadableDatabase();
			
			progress = ProgressDialog.show(this, "Lütfen bekleyiniz.",
					"Liste internetten alýnýyor.");

			RehberGetir rg = new RehberGetir(this);
			rg.start();
		}
		else{
			Toast.makeText(getApplicationContext(), "Ýnterneti kontrol ediniz", Toast.LENGTH_LONG).show();
		}

	}

	public void btnArama(View view) {
		String sorgu = "select * from Personel";
		StringBuilder sb = new StringBuilder();

		if (!txtAdi.getText().toString().equals("")) {
			if (!sb.toString().equals("")) {
				sb.append(" and ");
			}
			sb.append("Adi like '%");
			sb.append(txtAdi.getText().toString());
			sb.append("%'");
		}

		if (!txtSoyadi.getText().toString().equals("")) {
			if (!sb.toString().equals("")) {
				sb.append(" and ");
			}
			sb.append("Soyadi like '%");
			sb.append(txtSoyadi.getText().toString());
			sb.append("%'");
		}

		if (!txtDahili.getText().toString().equals("")) {
			if (!sb.toString().equals("")) {
				sb.append(" and ");
			}
			sb.append("Dahili like '%");
			sb.append(txtDahili.getText().toString());
			sb.append("%'");
		}

		if (spnBirim.getSelectedItemPosition() > 0) {
			if (!sb.toString().equals("")) {
				sb.append(" and ");
			}
			sb.append("Birimi = '");
			sb.append(spnBirim.getSelectedItem().toString());
			sb.append("'");
			if (spnBolum.getSelectedItemPosition() > 0) {
				if (!sb.toString().equals("")) {
					sb.append(" and ");
				}
				sb.append("Bolumu = '");
				sb.append(spnBolum.getSelectedItem().toString());
				sb.append("'");
			}
		}

		if (!sb.toString().equals("")) {
			sorgu += " where " + sb.toString();
		}

		Cursor cr = db.rawQuery(sorgu, null);

		ArrayList<Personel> liste = new ArrayList<Personel>();

		while (cr.moveToNext()) {
			
			Personel p = new Personel();
			p.Adi = cr.getString(0);
			p.Soyadi = cr.getString(1);
			p.Gorevi = cr.getString(2);
			p.Unvani = cr.getString(3);
			p.Birimi = cr.getString(4);
			p.Bolumu = cr.getString(5);
			p.Dahili = cr.getString(6);
			liste.add(p);
		}

		PersonelAdaptor adap = new PersonelAdaptor(this, liste);
		lstPersonel.setAdapter(adap);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		bolumListele(spnBirim.getSelectedItem().toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	
}
