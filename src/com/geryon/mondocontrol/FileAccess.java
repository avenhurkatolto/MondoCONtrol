package com.geryon.mondocontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;

public class FileAccess{
	public Context context;
	public FileAccess(Context c){
		context = c;
	}
	
	public boolean checkFile(String FileName) {
		
		File file = new File (context.getFilesDir(), FileName);
		if (file.exists() == true) {
		return true;
		}
		else {
		return false;
		}
		
		
	} 
	
	public void createXML (String pile) throws IOException{
		String path = context.getFilesDir().toString();
				
		FileWriter f = new FileWriter(context.getFilesDir().toString() + "/holdfeny.xml");
		f.append(pile);
		f.flush();
		f.close();
	
	}
	
	
	
	public String openXML () throws IOException{
		String result = "";
		String TempString = "";
		BufferedReader buf = new BufferedReader(new FileReader(context.getFilesDir().toString()+"/data.xml"));
		
		 while ((TempString = buf.readLine()) != null) {
	         result += TempString; 
	          }
		
		buf.close();
		 return result;
		
	} 

	public void createINI (String path, String pile) throws IOException{
		String stringIni = "Tutorial=0\nShuffle=1\nRepeat=1Lastplayed=0";
				
		FileWriter f = new FileWriter(path+"/settings.ini");
		f.append(stringIni);
		f.flush();
		f.close();
	
	}
	
	
	
}


