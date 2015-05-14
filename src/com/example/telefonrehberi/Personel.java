package com.example.telefonrehberi;

import java.io.Serializable;

import android.database.sqlite.SQLiteDatabase;

public class Personel implements Serializable {
	public String Gorevi;
	public String Unvani;
	public String Adi;
	public String Soyadi;
	public String Birimi;
	public String Bolumu;
	public String Dahili;
	
	public void dbyeKaydet(SQLiteDatabase db) {
		
		String komut = "insert into Personel (Gorevi,Unvani,Adi,Soyadi,Birimi,Bolumu,Dahili) values " +
				"('"+Gorevi+"','"+Unvani+"','"+Adi+"','"+Soyadi+"','"+Birimi+"','"+Bolumu+"','"+Dahili+"')";
		
		db.execSQL(komut);
	}

}
