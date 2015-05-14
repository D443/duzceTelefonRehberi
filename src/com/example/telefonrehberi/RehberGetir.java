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

public class RehberGetir extends Thread {

	MainActivity ekran;
	
	public RehberGetir(MainActivity activity){
		ekran = activity;
	}
	
	@Override
	public void run() {
		try {
			String yazi = ekran.getResources().getString(R.string.mektup);
			
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
				Personel p = new Personel();
				
				Node okunan = persEleman.getElementsByTagName("Gorevi").item(0);
				
				p.Gorevi = okunan != null ? okunan.getTextContent() : "Gorevsiz";
				
				okunan = persEleman.getElementsByTagName("Unvani").item(0);
				p.Unvani = okunan != null ? okunan.getTextContent() : "Ünvansýz";
				
				okunan = persEleman.getElementsByTagName("Adi").item(0);
				p.Adi = okunan != null ? okunan.getTextContent() : "Adsýz";
				
				okunan = persEleman.getElementsByTagName("Soyadi").item(0);
				p.Soyadi = okunan != null ? okunan.getTextContent() : "Soyadsýz";
				
				okunan = persEleman.getElementsByTagName("Birimi").item(0);
				p.Birimi = okunan != null ? okunan.getTextContent() : "---";
				
				okunan = persEleman.getElementsByTagName("Bolumu").item(0);
				p.Bolumu = okunan != null ? okunan.getTextContent() : "";
				
				okunan = persEleman.getElementsByTagName("Dahili").item(0);
				p.Dahili = okunan != null ? okunan.getTextContent() : "---";
				
				p.dbyeKaydet(ekran.db);
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
