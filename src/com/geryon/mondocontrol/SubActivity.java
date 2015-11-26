package com.geryon.mondocontrol;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class SubActivity extends Activity{
	private int alarmH, alarmM, kezdOra, kezdPerc;
	private TextView textViewTime;
	//private TimePicker timePicker;
	private Button button;
	public int sorszam;
	private String PrNev;
	private int mode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subpage);
		Bundle bundle = this.getIntent().getExtras();
		mode = bundle.getInt("mode");
		sorszam = bundle.getInt("sorszam");
		kezdOra = bundle.getInt("kezdOra");
		kezdPerc = bundle.getInt("kezdPerc");
		PrNev = bundle.getString("Nev");
		
		
		
		textViewTime = (TextView) findViewById(R.id.subtextView);
		textViewTime.setText(bundle.getString("Nev")+"\n\n"+bundle.getString("Idopont")+"\n\n"+bundle.getString("Helyszin")+"\n\n"+bundle.getString("Leiras"));
		//timePicker = (TimePicker) findViewById(R.id.timePicker);
		if (mode == 2) {
		button = (Button) findViewById(R.id.ButtonAlarme);
		button.setText("Emlékeztetõ törlése");
				
		}
}
	/*
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			final TimePicker timePicker = new TimePicker(this);
		    timePicker.setIs24HourView(true);
		    timePicker.setCurrentHour(kezdOra);
		    timePicker.setCurrentMinute(kezdPerc);

		    new AlertDialog.Builder(this)
		            .setTitle("Test")
		            .setPositiveButton(android.R.string.ok, new OnClickListener() {

		                @Override
		                public void onClick(DialogInterface dialog, int which) {
		                    Log.d("Picker", timePicker.getCurrentHour() + ":"
		                            + timePicker.getCurrentMinute());
		                }
		            })
		            .setNegativeButton(android.R.string.cancel,
		                    new OnClickListener() {

		                        @Override
		                        public void onClick(DialogInterface dialog,
		                                int which) {
		                            Log.d("Picker", "Cancelled!");
		                        }
		                    }).setView(timePicker).show();
		}
		return null;
	}*/
	
	
	public void checkMode(View view){
		if (mode == 1) {
			setTime(view);
		} else if (mode == 2){
			delALarm(view);
			
		}
		
		
		
	}
	
	
	public void delALarm(View view){
		
		Intent in = new Intent(getApplicationContext(),
				MondoConMainActivity.class);
		in.putExtra("sorszam", sorszam);
		in.putExtra("mode", mode);
		setResult(100, in);
		finish();
	
	}
	
	
	




	@SuppressWarnings("deprecation")
	public void setTime(View view){
				 
		final TimePicker timePicker = new TimePicker(this);
	   timePicker.setIs24HourView(true);
	    timePicker.setCurrentHour(kezdOra);
	 timePicker.setCurrentMinute(kezdPerc);

	    new AlertDialog.Builder(this)
	            .setTitle("Test")
	            .setPositiveButton(android.R.string.ok, new OnClickListener() {

	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                   onTimeSet(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
	                }
	            })
	            .setNegativeButton(android.R.string.cancel,
	                    new OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog,
	                                int which) {
	                            Log.d("Picker", "Cancelled!");
	                        }
	                    }).setView(timePicker).show();}
		
			
	
	
	
	//private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
		
			
			
		
		public void onTimeSet(int selectedHour, int selectedMinute) {
			alarmH = selectedHour;//selected = amit átadunk vissza
			alarmM = selectedMinute;
			
			// set current time into textview
			//textViewTime.setText(new StringBuilder().append(padding_str(selectedHour)).append(":").append(padding_str(selectedMinute)));
 
			// set current time into timepicker 
			//timePicker.setCurrentHour(hour);
			//timePicker.setCurrentMinute(minute);
 
			//zárjuk be, küldjük el
			Intent in = new Intent(getApplicationContext(),
					MondoConMainActivity.class);
			if (mode == 1) { 
			in.putExtra("alarmH", alarmH);
			in.putExtra("alarmM", alarmM);
			in.putExtra("sorszam", sorszam);
			in.putExtra("PrNev", PrNev);
			}
			in.putExtra("mode", mode);
			setResult(100, in);
			finish();
		}
	//};}
 
	/*private static String padding_str(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}*/
}

