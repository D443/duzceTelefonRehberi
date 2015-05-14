package com.example.telefonrehberi;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PersonelAdaptor extends BaseAdapter {

	Activity ac;
	ArrayList<Personel> liste;
	
	public PersonelAdaptor(Activity activity, ArrayList<Personel> list) {
		ac = activity;
		liste = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return liste.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return liste.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		LayoutInflater cogaltici = (LayoutInflater)ac.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		View satir = cogaltici.inflate(R.layout.personel_getir_layout, null);
		Personel p = liste.get(position);
		
		TextView lblAdi = (TextView)satir.findViewById(R.id.lblAdiSoyadi);
		lblAdi.setText(p.Unvani+" "+p.Adi + " " + p.Soyadi);
		
		TextView lblGorevi = (TextView)satir.findViewById(R.id.lblGorevi);
		lblGorevi.setText(p.Gorevi);
		
		TextView lblTelefon = (TextView)satir.findViewById(R.id.lblTelefonNo);
		lblTelefon.setText(p.Dahili);
		
		
		return satir;
	}

}
