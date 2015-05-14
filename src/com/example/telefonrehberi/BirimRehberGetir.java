package com.example.telefonrehberi;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class BirimRehberGetir extends Thread {

	MainActivityBirimler ekran;
	
	public BirimRehberGetir(MainActivityBirimler activity){
		ekran = activity;
	}
	
	@Override
	public void run() {
		try {
			String yazi = ekran.getResources().getString(R.string.mektupiki);
			
			HttpPost zarf = new HttpPost("http://bilalalbayrak.com/Service1.asmx");
			zarf.setHeader("Content-Type", "text/xml; charset=utf-8");
			zarf.setEntity(new StringEntity(yazi));
			
			DefaultHttpClient postaci = new DefaultHttpClient();
			HttpResponse cevap = postaci.execute(zarf);

			InputStream dokuman = cevap.getEntity().getContent();
			
			DocumentBuilder xmlYapici = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmlDokuman = xmlYapici.parse(dokuman);
			
			Element rootEleman = xmlDokuman.getDocumentElement();
			
			Element eleman = (Element)rootEleman.getElementsByTagName("NewDataSet").item(0);
			
			NodeList liste = eleman.getElementsByTagName("Table1");
			
			for (int i = 0; i < liste.getLength(); i++) {
				Element persEleman = (Element)liste.item(i);
				Birimler b = new Birimler();
				
				Node okunan = persEleman.getElementsByTagName("BirimAdi").item(0);
				
				b.BirimAdi = okunan != null ? okunan.getTextContent() : "--";
				
				okunan = persEleman.getElementsByTagName("BirimTel").item(0);
				b.BirimTel = okunan != null ? okunan.getTextContent() : "--";
				
				okunan = persEleman.getElementsByTagName("BirimFax").item(0);
				b.BirimFax = okunan != null ? okunan.getTextContent() : "Adsýz";
				
				b.dbyeKaydet(ekran.db);
			}
			
			ekran.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ekran.progress.dismiss();
					ekran.birimListele();
				}
			});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
