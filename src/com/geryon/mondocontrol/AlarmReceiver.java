package com.geryon.mondocontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		//Bundle bundle = new Bundle(arg1);
		FileAccess FA = new FileAccess(arg0);
		int i = arg1.getIntExtra("sorszam", 0);
		//String s = arg1.getStringExtra("Info");
		long[] pattern = {300,300,500,500,700,700};
		SharedPreferences prefs = arg0.getSharedPreferences(
			      "com.geryon.mondocontrol", Context.MODE_WORLD_WRITEABLE);
		
			
		if (i == 0){
			Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
			
		} else {
			//String perc = prefs.getString(String.valueOf(i);
			Toast.makeText(arg0, prefs.getString(String.valueOf(i), prefs.getString(String.valueOf(i),"")), Toast.LENGTH_LONG).show();
			SharedPreferences.Editor editor = prefs.edit();
			editor.remove(String.valueOf(i)).commit();
		
		}
		
		
		Vibrator v;
		v=(Vibrator)arg0.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(pattern, -1);
		try {
	        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        Ringtone r = RingtoneManager.getRingtone(arg0, notification);
	        r.play();
	    } catch (Exception e) {}
	}

}
