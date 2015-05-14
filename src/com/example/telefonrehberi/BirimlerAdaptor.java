package com.example.telefonrehberi;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BirimlerAdaptor extends BaseAdapter {

	Activity ac;
	ArrayList<Birimler> liste;
	
	public BirimlerAdaptor(Activity activity, ArrayList<Birimler> list) {
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
		
		View satir = cogaltici.inflate(R.layout.birimler_getir_layout, null);
		Birimler b = liste.get(position);
		
		TextView lblBirim = (TextView)satir.findViewById(R.id.lblBirim);
		lblBirim.setText(b.BirimAdi);
		
		TextView lblTel=(TextView)satir.findViewById(R.id.lblTel);
		lblTel.setText(b.BirimTel);
		
		TextView lblFaks=(TextView)satir.findViewById(R.id.lblFaks);
		lblFaks.setText(b.BirimFax);
		
		return satir;
	}

}
