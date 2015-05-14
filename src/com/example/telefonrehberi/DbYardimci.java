package com.example.telefonrehberi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.DeletedContacts;

public class DbYardimci extends SQLiteOpenHelper {

	public static int versiyon = 1;

	public static String dbAdi = "Rehber.db";

	public DbYardimci(Context context) {
		super(context, dbAdi, null, versiyon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table Personel (Adi VARCHAR,Soyadi VARCHAR,Gorevi VARCHAR,Unvani VARCHAR,Birimi VARCHAR,Bolumu VARCHAR,Dahili VARCHAR)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Personel");
	
		onCreate(db);
	}

}
