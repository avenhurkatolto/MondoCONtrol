package com.geryon.mondocontrol;

import java.io.IOException;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MondoConMainActivity extends Activity  {
	Context context;
	RelativeLayout rel;
	TextView textview1, textview2;
	ImageButton imgBtn;
	Button alarmButton;
	String seged ="";
	Programs[] progs = new Programs[140]; 
	private int alarmH, alarmM, sorszam;
	private String PrNev;
	private int mode;
	private String Helyszin;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getApplicationContext();
		setContentView(R.layout.activity_main);
		//alarmButton = (Button) findViewById(R.id.Alarm);

		for (int i=2;i<48;i++){
			seged +=dateConvert(i);
		}
		seged = seged.substring(0, seged.length()-1);
		for (int i=0;i<140;i++){
			progs[i]=new Programs();

		}
		textview1 = (TextView) findViewById(R.id.BalIdo);
		textview1.setText(seged);
		try {
			beolvas(this);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

	@Override
	protected void onActivityResult(int requestCode,
			int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100){
			mode = data.getExtras().getInt("mode");  
			sorszam = data.getExtras().getInt("sorszam");
			SharedPreferences prefs = this.getSharedPreferences(
					"com.geryon.mondocontrol", Context.MODE_WORLD_WRITEABLE);
			if (mode == 1){
				alarmH = data.getExtras().getInt("alarmH");
				alarmM = data.getExtras().getInt("alarmM");




				//Toast.makeText(getBaseContext(), sorszam+alarmH+alarmM,  Toast.LENGTH_LONG).show();
				// Toast.makeText(getBaseContext(), String.valueOf(alarmH)+" "+String.valueOf(alarmM)+" "+String.valueOf(sorszam),  Toast.LENGTH_LONG).show();
				Calendar calNow = Calendar.getInstance();
				Calendar calSet = (Calendar) calNow.clone();
				calSet.set(Calendar.YEAR, 2013);
				calSet.set(Calendar.MONTH, Calendar.JULY);
				calSet.set(Calendar.DAY_OF_MONTH, 20);
				calSet.set(Calendar.HOUR_OF_DAY, alarmH);
				calSet.set(Calendar.MINUTE, alarmM);
				calSet.set(Calendar.SECOND, 0);
				calSet.set(Calendar.MILLISECOND, 0);

				if(calSet.compareTo(calNow) <= 0){
					//Today Set time passed, count to tomorrow
					calSet.add(Calendar.DATE, 1);
				}
				String temptime = calSet.getTime().toString();
				temptime.replace(":0", ":00");
				Toast.makeText(getBaseContext(), "Emlékeztetõ " + calSet.getTime().toString(), Toast.LENGTH_LONG).show();

				prefs.edit().putString(String.valueOf(sorszam), progs[sorszam].getNev()+" "+String.valueOf(alarmH)+":"+String.valueOf(alarmM)).commit();
				//RQS_1-re számlálót
				Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
				intent.putExtra("sorszam", sorszam);
				//intent.putExtra("Info", progs[sorszam].getNev()+"\n"+progs[sorszam].getHelyszin());
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), sorszam, intent, 0);
				AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
			} else if (mode == 2){

				Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), sorszam, intent, 0);
				AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(pendingIntent);
				SharedPreferences.Editor editor = prefs.edit();
				editor.remove(String.valueOf(sorszam));
				editor.commit();
				Toast.makeText(getBaseContext(), "Törölve",  Toast.LENGTH_LONG).show();

			}


		}

	}
	public void alarms(View view){
		//Context dialogContext = new ContextThemeWrapper(this, R.style.);
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog));
		String emlek = "";
		SharedPreferences prefs = this.getSharedPreferences(
				"com.geryon.mondocontrol", Context.MODE_WORLD_WRITEABLE);
		emlek = prefs.getAll().toString();
		emlek = stringFormatter(emlek);
		builder.setMessage("Emlékeztetõk:"+emlek)

		.setCancelable(true)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//
			}
		});
		AlertDialog alert = builder.create();
		alert.show();


	}



	public void CollExp(View view){
		int k = Integer.parseInt((String) view.getTag());
		//String s = "Col"+String.valueOf(k);
		switch (k){
		case 1:{ 
			rel = (RelativeLayout) findViewById(R.id.Col1);
			imgBtn = (ImageButton) findViewById(R.id.ExpColl1);
			break;	
		}
		case 2:{ 
			rel = (RelativeLayout) findViewById(R.id.Col2);
			imgBtn = (ImageButton) findViewById(R.id.ExpColl2);
			break;	
		}case 3:{ 
			rel = (RelativeLayout) findViewById(R.id.Col3);
			imgBtn = (ImageButton) findViewById(R.id.ExpColl3);
			break;	
		}case 4:{ 
			rel = (RelativeLayout) findViewById(R.id.Col4);
			imgBtn = (ImageButton) findViewById(R.id.ExpColl4);

			break;	
		}
		}
		if (rel.getVisibility() == rel.VISIBLE){
			rel.setVisibility(rel.GONE);
			imgBtn.setImageResource(R.drawable.expand);
		} else {rel.setVisibility(rel.VISIBLE);
		imgBtn.setImageResource(R.drawable.collapse);
		}

		//Toast.makeText(getBaseContext(), String.valueOf(view.getTag()),  Toast.LENGTH_LONG).show();
	}






	public void clickedProgram(View view){
		int i = Integer.parseInt((String) view.getTag());
		//Toast.makeText(getBaseContext(),view.getTag()+progs[i].getNev() +"\n"+ progs[i].getHelyszin()+"\n"+progs[i].getkezdOra()+":"+percIns(progs[i].getkezdPerc())+"\n"+progs[i].getvegOra()+":"+percIns(progs[i].getvegPerc())+"\n"+progs[i].getDesc() , Toast.LENGTH_LONG).show();	
		Intent intent = new Intent(getApplicationContext(), SubActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("Nev", progs[i].getNev());
		bundle.putString("Helyszin", progs[i].getHelyszin());
		/*String kezdperc = String.valueOf(progs[i].getkezdPerc());
		if (kezdperc == "0") {
			kezdperc = "00";
		}

		String vegperc = String.valueOf(progs[i].getvegPerc());
		if (vegperc == "0") {
			vegperc = "00";
		}*/
		bundle.putString("Idopont", String.valueOf(progs[i].getkezdOra()+":"+percIns((progs[i].getkezdPerc()))+"-"+String.valueOf(progs[i].getvegOra()))+":"+percIns(progs[i].getvegPerc()));
		bundle.putString("Leiras", progs[i].getDesc());
		bundle.putInt("sorszam", i);
		bundle.putInt("kezdOra", progs[i].getkezdOra());
		bundle.putInt("kezdPerc", progs[i].getkezdPerc());
		SharedPreferences prefs = this.getSharedPreferences(
				"com.geryon.mondocontrol", Context.MODE_WORLD_WRITEABLE);
		if (prefs.contains(String.valueOf(i))){
			mode = 2;


		} else 

		{mode =1;}

		bundle.putInt("mode", mode);		



		intent.putExtras(bundle);
		startActivityForResult(intent, 100);
	}

	public void beolvas(Activity activity) throws XmlPullParserException, IOException{
		StringBuilder temp= new StringBuilder();
		String ido="";
		String helyszin = "";
		int j = 1;
		int ora, perc;
		Resources res = activity.getResources();
		XmlResourceParser xpp = res.getXml(R.xml.holdfeny);
		xpp.next();
		int eventType = xpp.getEventType();
		FileAccess FA = new FileAccess(context);
		//FA.createXML(getResources().getXml(R.xml.holdfeny).toString());




		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if	(xpp.getName().equalsIgnoreCase("ProgramNev")){
					progs[j].setNev(xpp.nextText());
				} else if (xpp.getName().equalsIgnoreCase("Kezdet")){
					ido = xpp.nextText();
					progs[j].setkezdOra(oraConvert(ido));
					progs[j].setkezdPerc(percConvert(ido));   				
				} else if (xpp.getName().equalsIgnoreCase("Veg")){
					ido =xpp.nextText();
					progs[j].setvegOra(oraConvert(ido));
					progs[j].setvegPerc(percConvert(ido));

				} else if (xpp.getName().equalsIgnoreCase("Leiras")){
					progs[j].setDesc(xpp.nextText());
					progs[j].setHelyszin(helyszin);
					j++;
				} else if (xpp.getName().equalsIgnoreCase("Helyszin")){
					helyszin = xpp.nextText();
				}
			}
			/*else if(eventType == XmlPullParser.END_TAG)
    		{
    			if (xpp.getName().equalsIgnoreCase("ProgramNev")){
    				progs[j].setHelyszin(helyszin);
    				j++;
    			}
    		}*/
			eventType = xpp.next();

		}

		FA.createXML(temp.toString());

	}

	public String dateConvert(int be){
		int seged1 = (be / 4)+9;
		int seged2 = (be %4)*15;
		String ido = "";
		if (seged2 == 0){
			ido=Integer.toString(seged1)+"H\n";	

		}else {
			ido=Integer.toString(seged2)+"\n";
		}
		return ido;

	}
	public int oraConvert(String s){
		int h = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		if (sb.indexOf(":") == 1){
			h = 9;
			//Integer.parseInt(sb.substring(0,1));

		} else if (sb.indexOf(":") == 2){
			h = Integer.parseInt(sb.substring(0,2));



		}
		return h;


	}

	public String percIns (int be){
		String ki=String.valueOf(be);
		if (be==0) {
			ki = "0"+ki;
		} 
		return ki;
	}

	public int percConvert(String s){
		int m =0;
		StringBuilder sb = new StringBuilder(s);
		if (sb.indexOf(":") == 1){
			m = Integer.parseInt(sb.substring(2,4));

		} else if (sb.indexOf(":") == 2){
			m = Integer.parseInt(sb.substring(3,5));

		}
		return m;


	}
	public String stringFormatter(String input){
		int in1, in2;
		StringBuilder output= new StringBuilder(input);
		if (input == "{}") {
			output = new StringBuilder("Nincs emlékeztető beállítva.");
		}else{ 
			output = new StringBuilder(output.toString().replace("{", "\n"));
			in1 = 1;
			in2 = output.indexOf("=");
			output = output.delete(in1, in2+1);
			output = new StringBuilder(output.toString().replace(",", "\n\n,"));
			while (output.indexOf("=")!= -1){
				in1 = output.indexOf(",");
				in2 = output.indexOf("=");
				output= output.delete(in1, in2+1);


			}
			output = output.deleteCharAt(output.length()-1);
			output = new StringBuilder(output.toString().replace(":0", ":00"));
		}
		return output.toString();
	}
}
