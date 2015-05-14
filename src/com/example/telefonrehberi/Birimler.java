package com.example.telefonrehberi;

import java.io.Serializable;

import android.database.sqlite.SQLiteDatabase;

public class Birimler implements Serializable {
	public String BirimAdi;
	public String BirimTel;
	public String BirimFax;
	
	
	public void dbyeKaydet(SQLiteDatabase db) {
		
		String komut = "insert into Birim (BirimAdi,BirimTel,BirimFax) values " +
				"('"+BirimAdi+"','"+BirimTel+"','"+BirimFax+"')";
		
		db.execSQL(komut);
	}

}
